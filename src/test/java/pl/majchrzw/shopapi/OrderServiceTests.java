package pl.majchrzw.shopapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.majchrzw.shopapi.dao.OrderRepository;
import pl.majchrzw.shopapi.dto.OrderDTO;
import pl.majchrzw.shopapi.model.*;
import pl.majchrzw.shopapi.service.OrderService;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest
public class OrderServiceTests {
	
	@Autowired
	private OrderService orderService;
	
	@MockBean
	private OrderRepository orderRepository;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void shouldReturnOrder(){
		// given
		long orderId = 5;
		Order order = new Order( orderId, "user", Date.from(Instant.now()), OrderStatus.NEW, new ArrayList<>());
		Mockito.when(orderRepository.findById(orderId))
				.thenReturn(Optional.of(order));
		// when
		Order testOrder = orderService.getOrderById(orderId);
		// then
		Assertions.assertEquals(testOrder, order);
	}
	
	@Test
	public void shouldDeleteOrder(){
		// given
		long orderId = 10;
		Order order = new Order( orderId, "user", Date.from(Instant.now()), OrderStatus.NEW, new ArrayList<>());
		Mockito.doNothing()
				.when(orderRepository)
				.deleteById(orderId);
		// when
		orderService.deleteOrder(orderId);
		// then
	}
	
	@ParameterizedTest
	@MethodSource("getOrderDTOs")
	public void shouldConvertToOrderFromOrderDTO(OrderDTO orderDTO){
		Order order = orderService.convertFromDTO(orderDTO);
		assertThat(order)
				.isNotNull()
				.hasFieldOrProperty("id");
	}
	
	private static Stream<OrderDTO> getOrderDTOs() throws IOException {
			return objectMapper.readValue(new File("src/main/resources/values.json"), new TypeReference<List<OrderDTO>>() {}).stream();
	}
	
	@Test
	public void shouldUpdateOrderStatus(){
		// given
		long orderId = 10;
		Order order = new Order( orderId, "user", Date.from(Instant.now()), OrderStatus.NEW, new ArrayList<>());
		// when
		orderService.updateOrderStatusToShipped(order);
		// then
		assertThat(order)
				.hasFieldOrPropertyWithValue("orderStatus", OrderStatus.SHIPPED);
	}
	
	@Test
	public void shouldCheckOrderForShipment(){
		// given
		Product product = new Product(1L, "Produkt", 12.99, 50);
		Order order = new Order(1L, "user", Date.from(Instant.now()), OrderStatus.NEW, new ArrayList<>());
		OrderDetail orderDetail = new OrderDetail( 1L, order, product, 5);
		order.getOrderDetails().add(orderDetail);
		// when
		boolean orderReady = orderService.checkoutOrderForShipping(order);
		// then
		Assertions.assertTrue(orderReady);
		Assertions.assertEquals(order.getOrderStatus(), OrderStatus.SHIPPED);
		Assertions.assertEquals(product.getStockQuantity(), 45);
	}
	
	@Test
	public void shouldNotCheckOrderForShipmentBecauseStockIsTooLow(){
		// given
		Product product = new Product(1L, "Produkt", 12.99, 10);
		Order order = new Order(1L, "user", Date.from(Instant.now()), OrderStatus.NEW, new ArrayList<>());
		OrderDetail orderDetail = new OrderDetail( 1L, order, product, 20);
		order.getOrderDetails().add(orderDetail);
		// when
		boolean orderReady = orderService.checkoutOrderForShipping(order);
		// then
		Assertions.assertFalse(orderReady);
		Assertions.assertEquals(order.getOrderStatus(), OrderStatus.NEW);
	}
	
	@Test
	public void shouldRestQuantityWhenOrderIsCanceled(){
		// given
		Product product = new Product(1L, "Produkt", 12.99, 50);
		Order order = new Order(1L, "user", Date.from(Instant.now()), OrderStatus.NEW, new ArrayList<>());
		OrderDetail orderDetail = new OrderDetail( 1L, order, product, 5);
		order.getOrderDetails().add(orderDetail);
		// when
		orderService.checkoutOrderForShipping(order);
		orderService.cancelOrderAndResetQuantity(order);
		// then
		Assertions.assertEquals(order.getOrderStatus(), OrderStatus.NEW);
		Assertions.assertEquals(product.getStockQuantity(), 50);
	}
}
