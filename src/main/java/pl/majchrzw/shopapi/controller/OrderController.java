package pl.majchrzw.shopapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.majchrzw.shopapi.model.AddToCartRequestBody;
import pl.majchrzw.shopapi.model.Order;
import pl.majchrzw.shopapi.model.OrderDetail;
import pl.majchrzw.shopapi.service.OrderDetailService;
import pl.majchrzw.shopapi.service.OrderService;

import java.util.List;

@RestController()
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	private final OrderDetailService orderDetailService;
	
	@GetMapping("/{username}")
	public List<Order> getOrderByUsername(@PathVariable String username){
		return orderService.getOrdersByUsername(username);
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
	public ResponseEntity<String> postOrder(@RequestBody Order order){
		orderService.saveOrder(order);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<String> postProductToOrder(@PathVariable Long id, @RequestBody AddToCartRequestBody body){
		// TODO - w body ma byc produkt i ma być nowy OrderDetail lub edytowany jeżeli jeden już istnieje
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
	
	/* TODO - notatki do zrobienia
	do shop-api

Stworzyć usera jakoś?

- tworzyć koszyk ze statusem new
- dodajesz produkt do koszyka - nowe order detail i quantity
- jeżel aktualizujesz ilość to trzeba sprawdzić czy coś już jest w koszyku
- przy checkoucie sprawdzasz czy wystarczy produktu
- dajesz status na inny
- zmieniasz stan
- jak anulowane to dodajesz ilość do stanu produktu i zmieniasz status zamówienia na canceled

dodać endpoint do checkoutu oraz do cancellowania
endpoint do dodawania produktu do koszyka
	 */
	
	
}
