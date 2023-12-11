package pl.majchrzw.shopapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.majchrzw.shopapi.model.*;
import pl.majchrzw.shopapi.service.OrderDetailService;
import pl.majchrzw.shopapi.service.OrderService;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	@GetMapping("/byuser")
	public List<Order> getCurrentOrderByUsernameAndStatus(@RequestParam String username, @RequestParam("status") OrderStatus orderStatus){
		return List.of(orderService.getOrderByUsernameAndStatus(username, orderStatus));
	}
	
	@GetMapping()
	public Page<Order> getOrdersPaged(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam("sort") Optional<String> sortParam){
		String sort = sortParam.orElse("id,asc");
		String[] _sort = sort.split(",");
		return orderService.getOrders(PageRequest.of(page.orElse(0), size.orElse(20), Sort.by(Sort.Direction.DESC,_sort[0])));
	}
	
	@GetMapping("/{id}")
	public Order getOrderById(@PathVariable Long id){
		return orderService.getOrderById(id);
	}
	
	@GetMapping("/{id}/orderdetails")
	public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable Long id){
		return orderService.getOrderDetailsOfOrderById(id);
	}
	
	@PostMapping
	public ResponseEntity<String> postOrder(@RequestBody @Valid PostOrderRequestBody requestBody){
		Order order = orderService.saveNewOrder(requestBody);
		return new ResponseEntity<>("{ \"id\":\"" + order.getId() + "\"}",HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}/products")
	public ResponseEntity<String> postProductToOrder(@PathVariable Long id, @RequestBody AddToCartRequestBody body){
		// id - to jest id orderu
		orderService.addProductToOrder(orderService.getOrderById(id), body.getId(),body.getQuantity());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}/products/{productId}")
	public ResponseEntity<String> deleteProductFromOrder(@PathVariable Long id, @PathVariable Long productId){
		orderService.deleteOrderDetailFromOrder(id, productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> putOrder(@RequestBody @Valid Order order){
		orderService.saveOrder(order);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrder(@PathVariable Long id){
		orderService.deleteOrder(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	
}
