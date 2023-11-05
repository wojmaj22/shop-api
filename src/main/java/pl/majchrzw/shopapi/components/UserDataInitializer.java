package pl.majchrzw.shopapi.components;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.majchrzw.shopapi.dao.OrderDetailRepository;
import pl.majchrzw.shopapi.dao.OrderRepository;
import pl.majchrzw.shopapi.dao.ProductRepository;
import pl.majchrzw.shopapi.model.Order;
import pl.majchrzw.shopapi.model.OrderDetail;
import pl.majchrzw.shopapi.model.Product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class UserDataInitializer {
	private final OrderDetailRepository orderDetailRepository;
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	
	private Faker faker = new Faker(new Random(100L));
	
	public void initializeData(){
		int productsAmount = 30;
		int ordersAmount = 10;
		int orderDetailsAmount = 20;
		Random random = new Random();
		ArrayList<Product> products = new ArrayList<>();
		ArrayList<OrderDetail> orderDetails = new ArrayList<>();
		ArrayList<Order> orders = new ArrayList<>();
		for( int i = 0; i < productsAmount; i++) {
			Product product = Product.builder()
					.name(faker.commerce().productName())
					.price(Math.round(random.nextDouble()*10000.0)/100.0)
					.build();
			products.add(product);
		}
		for( int i = 0; i < ordersAmount; i++){
			Order order = Order.builder()
					.user(faker.name().username())
					.orderDate(Date.from(Instant.now()))
					.build();
			orders.add(order);
		}
		for( int i = 0; i < orderDetailsAmount; i++){
			OrderDetail orderDetail = OrderDetail.builder()
					.product(products.get(Math.abs(random.nextInt()%productsAmount)))
					.order(orders.get(Math.abs(random.nextInt()%ordersAmount)))
					.build();
			orderDetail.setQuantity(Math.abs(random.nextInt()%20));
			orderDetails.add(orderDetail);
		}
		orderRepository.saveAll(orders);
		productRepository.saveAll(products);
		orderDetailRepository.saveAll(orderDetails);
	}
}
