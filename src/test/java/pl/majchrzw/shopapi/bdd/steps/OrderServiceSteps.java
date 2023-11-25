package pl.majchrzw.shopapi.bdd.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.majchrzw.shopapi.dao.OrderRepository;
import pl.majchrzw.shopapi.dao.ProductRepository;
import pl.majchrzw.shopapi.model.Order;
import pl.majchrzw.shopapi.model.Product;
import pl.majchrzw.shopapi.service.OrderService;
import pl.majchrzw.shopapi.service.ProductService;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

public class OrderServiceSteps {
    @InjectMocks
    @Autowired
    private OrderService orderService;
    @MockBean
    @Autowired
    private OrderRepository orderRepository;

    private long orderId;
    private Order order;
    private Order retrievedOrder;

    @Given("Order id")
    public void orderId(){
        order = new Order("username", Date.from(Instant.now()));
        orderId = 1L;
        order.setId(orderId);
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    }

    @When("I retrieve order data")
    public void i_retrieve_data(){
        retrievedOrder = orderService.getOrderById(orderId);
        System.out.println(retrievedOrder.toString());
    }

    @Then("I receive order data")
    public  void i_receive_data(){
        Assertions.assertEquals(order,retrievedOrder);
    }
}
