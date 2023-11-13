package pl.majchrzw.shopapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.majchrzw.shopapi.model.*;
import pl.majchrzw.shopapi.service.OrderDetailService;
import pl.majchrzw.shopapi.service.OrderService;

import java.util.List;

@RestController()
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	private final OrderDetailService orderDetailService;
	
	@GetMapping()
	public Order getCurrentOrderByUsernameAndStatus(@RequestParam String username, @RequestParam OrderStatus orderStatus){
		return orderService.getOrderByUsernameAndStatus(username, orderStatus);
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
	public ResponseEntity<String> postOrder(@RequestBody PostOrderRequestBody requestBody){
		orderService.saveNewOrder(requestBody);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}/products")
	public ResponseEntity<String> postProductToOrder(@PathVariable Long id, @RequestBody AddToCartRequestBody body){
		// id - to jest id orderu
		orderService.addProductToOrder(orderService.getOrderById(id), body.getId(),body.getQuantity());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> putOrder(@RequestBody Order order){
		orderService.saveOrder(order);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrder(@PathVariable Long id){
		orderService.deleteOrder(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	
}
