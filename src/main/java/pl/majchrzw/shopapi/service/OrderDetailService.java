package pl.majchrzw.shopapi.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.majchrzw.shopapi.dao.OrderDetailRepository;
import pl.majchrzw.shopapi.dao.ProductRepository;
import pl.majchrzw.shopapi.model.AddToCartRequestBody;
import pl.majchrzw.shopapi.model.Order;
import pl.majchrzw.shopapi.model.OrderDetail;
import pl.majchrzw.shopapi.model.Product;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
	
	private final OrderDetailRepository orderDetailRepository;
	private final ProductService productService;
	
	private final EntityManager entityManager;
	
	public void createOrderDetail(OrderDetail orderDetail){
		orderDetail.getProduct().setStockQuantity(orderDetail.getProduct().getStockQuantity() - orderDetail.getQuantity());
		orderDetailRepository.save(orderDetail);
		
	}
	
	public void updateOrderDetail(Long id, OrderDetail orderDetail){
		if( orderDetailRepository.existsById(id)){
			orderDetailRepository.save(orderDetail);
		} else {
			throw new EntityNotFoundException("No orderDetail with id: " + id + ", has been found.");
		}
	}
	
	@Transactional
	public void deleteOrderDetail(Long id){
		//orderDetailRepository.deleteById(id);
		Query query = entityManager.createQuery("delete from OrderDetail o where o.id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}
	
	public OrderDetail createOrderDetailAndAddToCart(AddToCartRequestBody request, Order order){
		Product productToAdd = productService.getProductById(request.getId());
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setProduct(productToAdd);
		orderDetail.setQuantity(request.getQuantity());
		orderDetail.setOrder(order);
		
		return orderDetailRepository.save(orderDetail);
	}
	
	public OrderDetail findById(Long id){
		Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(id);
		if( orderDetailOptional.isPresent()){
			return orderDetailOptional.get();
		} else {
			throw new EntityNotFoundException("No order detail with id: " + id + ", has been found.");
		}
	}
	
	public List<OrderDetail> findAllById(Iterable<Long> ids){
		return orderDetailRepository.findAllById(ids);
	}
	
	public void updateOrderDetailsQuantity(Long id, Integer quantity){
		OrderDetail orderDetail = findById(id);
		Integer quantityToChange = quantity - orderDetail.getQuantity();
		// jak quantityToChange jest > 0 to znaczy że zwiększamy ilość w zamówienia a zmniejszamy w produkcie, jak < 0 to znaczy że zminejszamy w zamówieniu a zwiekszamy w produkcie.
		// w produkcie zmieniamy chyba o przeciwnośc tej liczby
		
		orderDetail.getProduct().setStockQuantity(orderDetail.getProduct().getStockQuantity()-quantityToChange);
		
		orderDetail.setQuantity(quantity);
		orderDetailRepository.save(orderDetail);
	}
}
