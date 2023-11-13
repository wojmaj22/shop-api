package pl.majchrzw.shopapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.majchrzw.shopapi.dao.OrderDetailRepository;
import pl.majchrzw.shopapi.dao.OrderRepository;
import pl.majchrzw.shopapi.dto.OrderDTO;
import pl.majchrzw.shopapi.model.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final OrderDetailService orderDetailService;
	
	private final ProductService productService;
	
	public Page<Order> getOrders(Pageable pageable){
		// TODO - might not work as intended
		return orderRepository.findAll(pageable);
	}
	
	public List<Order> getOrdersByUsername(String username){
		//List<Order> orders = orderRepository.findAllByUser(username);
		return orderRepository.findAllByUser(username);
	}
	
	public Order getOrderByUsernameAndStatus(String username, OrderStatus status){
		List<Order> orders = orderRepository.findAllByUserAndOrderStatus(username, status);
		if( orders.size() == 0){
			Order order = new Order(username, Date.from(Instant.now()));
			orderRepository.save(order);
			return order;
		} else if ( orders.size() == 1){
			return orders.get(0);
		} else {
			throw new IllegalStateException("Cannot have more than one order with NEW status for user");
		}
	}
	
	public long checkIfNewOrderExists( List<Order> orders){
		return orders.stream()
				.filter(order -> order.getOrderStatus() == OrderStatus.NEW)
				.count();
	}
	
	public Order getOrderById(Long id){
		Optional<Order> orderOptional = orderRepository.findById(id);
		if( orderOptional.isPresent()){
			return orderOptional.get();
		} else {
			throw new EntityNotFoundException("No order with id:" + id + ", has been found.");
		}
	}
	
	public List<OrderDetail> getOrderDetailsOfOrderById(Long id){
		Optional<Order> orderOptional = orderRepository.findById(id);
		if( orderOptional.isPresent()) {
			return orderOptional.get().getOrderDetails();
		} else {
			throw new EntityNotFoundException("No order with id:" + id + ", has been found.");
		}
	}
	
	public Long checkIfProductAlreadyExistsInOrder(Order order, Long productId){
		Optional<Long> optionalOrderDetailId = order.getOrderDetails()
				.stream()
				.filter( orderDetail -> Objects.equals(orderDetail.getProduct().getId(), productId))
				.map(OrderDetail::getId)
				.findFirst();
		return optionalOrderDetailId.orElse(-1L);
	}
	
	public void addProductToOrder(Order order, Long productId, Integer quantity){
		Long orderDetailId = checkIfProductAlreadyExistsInOrder(order,productId);
		if ( orderDetailId == -1){
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProduct(productService.getProductById(productId));
			orderDetail.setQuantity(quantity);
			orderDetail.setOrder(order);
			
			orderDetailService.createOrderDetail(orderDetail);
		} else {
			orderDetailService.updateOrderDetailsQuantity(orderDetailId, quantity);
		}
	}
	
	public void addOrderDetailToOrder(OrderDetail orderDetail, Long id){
		Order order = getOrderById(id);
		order.getOrderDetails().add(orderDetail);
		orderRepository.save(order);
	}
	
	public Order convertFromDTO( OrderDTO dto){
		List<OrderDetail> orderDetails = orderDetailService.findAllById(dto.getOrderDetailsList());
		return Order.builder()
				.id(dto.getId())
				.user(dto.getUser())
				.orderDate(dto.getOrderDate())
				.orderStatus(dto.getOrderStatus())
				.orderDetails(orderDetails)
				.build();
	}
	
	public boolean checkoutOrderForShipping(Order order){
		if (!checkProductsForAvailability(order)){
			return false;
		}
		checkIfClientExists();
		updateOrderStatusToShipped(order);
		return true;
	}
	
	public boolean checkProductsForAvailability(Order order){
		for ( OrderDetail orderDetail: order.getOrderDetails()) {
			Product product = orderDetail.getProduct();
			if (product.getStockQuantity() < orderDetail.getQuantity()){
				return false;
			} else {
				product.setStockQuantity(product.getStockQuantity() - orderDetail.getQuantity());
			}
		}
		return true;
	}
	
	public void cancelOrderAndResetQuantity(Order order){
		if( order.getOrderStatus() == OrderStatus.NEW){
			throw new IllegalStateException("Cannot Reset quantity on new order");
		}
		for ( OrderDetail orderDetail: order.getOrderDetails()){
			Product product = orderDetail.getProduct();
			product.setStockQuantity(product.getStockQuantity() + orderDetail.getQuantity());
		}
		order.setOrderStatus(OrderStatus.NEW);
	}
	
	public void updateOrderStatusToShipped(Order oder){
		oder.setOrderStatus(OrderStatus.SHIPPED);
	}
	
	public boolean checkIfClientExists(){
		return true;
	}
	
	public void saveNewOrder(PostOrderRequestBody requestBody){
		Order order = new Order();
		order.setUser(requestBody.getUser());
		order.setOrderDate(requestBody.getOrderDate());
		if ( requestBody.getOrderStatus() != null) {
			order.setOrderStatus(requestBody.getOrderStatus());
		} else {
			order.setOrderStatus(OrderStatus.NEW);
		}
		orderRepository.save(order);
	}
	
	public void saveOrder(Order order){
		orderRepository.save(order);
	}
	
	public void updateOrder(Order order){
		orderRepository.save(order);
	}
	
	public void deleteOrder(Long id){
		orderRepository.deleteById(id);
	}
}
