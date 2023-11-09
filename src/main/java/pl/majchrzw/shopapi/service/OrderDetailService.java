package pl.majchrzw.shopapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.majchrzw.shopapi.dao.OrderDetailRepository;
import pl.majchrzw.shopapi.dao.ProductRepository;
import pl.majchrzw.shopapi.model.AddToCartRequestBody;
import pl.majchrzw.shopapi.model.OrderDetail;
import pl.majchrzw.shopapi.model.Product;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
	
	private final OrderDetailRepository orderDetailRepository;
	private final ProductService productService;
	
	public void createOrderDetail(OrderDetail orderDetail){
		orderDetailRepository.save(orderDetail);
	}
	
	public void updateOrderDetail(Long id, OrderDetail orderDetail){
		if( orderDetailRepository.existsById(id)){
			orderDetailRepository.save(orderDetail);
		} else {
			throw new EntityNotFoundException("No orderDetail with id: " + id + ", has been found.");
		}
	}
	
	public void deleteOrderDetail(Long id){
		orderDetailRepository.deleteById(id);
	}
	
	public OrderDetail createOrderDetailAndAddToCart(AddToCartRequestBody request){
		Product productToAdd = productService.getProductById(request.getId());
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setQuantity(request.getQuantity());
		orderDetail.setProduct(productToAdd);
		
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
		orderDetail.setQuantity(quantity);
		orderDetailRepository.save(orderDetail);
	}
}
