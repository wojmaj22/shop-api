package pl.majchrzw.shopapi.bdd.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.majchrzw.shopapi.bdd.tooling.SpringGlue;
import pl.majchrzw.shopapi.dao.OrderDetailRepository;
import pl.majchrzw.shopapi.dao.OrderRepository;
import pl.majchrzw.shopapi.dao.ProductRepository;
import pl.majchrzw.shopapi.model.*;
import pl.majchrzw.shopapi.service.OrderService;
import pl.majchrzw.shopapi.service.ProductService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerSteps extends SpringGlue {
    public OrderControllerSteps() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Konfigurujemy format daty - np. "yyyy-MM-dd HH:mm:ss"
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
    long orderId;
    long productId;
    long invalidOrderId;
    private List<OrderDetail> orderDetails;
    Product product;
    Order order;
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    OrderService service;
    @Autowired
    @MockBean
    OrderRepository orderRepository;
    @Autowired
    @MockBean
    OrderDetailRepository orderDetailRepository;

    @Autowired
    @MockBean
    ProductRepository productRepository;
    private ResultActions Result;


    //Scenario: User can retrieve order by ID
    @Given("user has a valid order ID")
    public void userHasAValidOrderId() {
        orderId = 1L;
        order = new Order("username", Date.from(Instant.now()));
        order.setId(orderId);
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    }

    @When("user makes GET request for order by ID")
    public void userMakesGETRequestForOrderById() throws Exception {
        Result = mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/" + orderId).accept(MediaType.APPLICATION_JSON_VALUE));
    }

    @Then("system returns order data")
    public void systemReturnsOrderData() throws Exception {
        String jsonContentExpected = objectMapper.writeValueAsString(order);
        Result.andExpect(content().json(jsonContentExpected));
    }

    //  Scenario: User can retrieve order details by order ID
    @Given("user has a valid order ID for order details")
    public void userHasAValidOrderIdForOrderDetails() {
        orderId = 1L;
        order = new Order("username", Date.from(Instant.now()));
        order.setId(orderId);

        orderDetails = new ArrayList<>();
        product = new Product(1L, "Product Name", 29.99, 100);
        OrderDetail orderDetail = new OrderDetail(1L, order, product, 2, 99.99);
        orderDetail.setId(1L);
        orderDetails.add(orderDetail);

        order.setOrderDetails(orderDetails);

        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    }

    @When("user makes GET request for order details by order ID")
    public void userMakesGETRequestForOrderDetailsByOrderId() throws Exception {
        Result = mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/" + orderId + "/orderdetails").accept(MediaType.APPLICATION_JSON_VALUE));
    }

    @Then("system returns list of order details data")
    public void systemReturnsListOfOrderDetailsData() throws Exception {
        String jsonContentExpected = objectMapper.writeValueAsString(orderDetails);
        Result.andExpect(content().json(jsonContentExpected));
    }

    // Scenario: User wants to create a new order
    @Given("user has a valid order data")
    public void userHasAValidOrderData() {
        order = new Order("username", Date.from(Instant.now()));
        order.setId(1L);

        orderDetails = new ArrayList<>();
        product = new Product(1L, "Product Name", 29.99, 100);
        OrderDetail orderDetail = new OrderDetail(1L, order, product, 2, 99.99);
        orderDetail.setId(1L);
        orderDetails.add(orderDetail);
        order.setOrderDetails(orderDetails);

        Mockito.when(orderRepository.existsById(order.getId())).thenReturn(true);
        Mockito.when(orderRepository.save(order)).thenReturn(order);


//        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    }

    @When("user makes POST request to create a new order")
    public void userMakesPOSTRequestToCreateNewOrder() throws Exception {
        String content = objectMapper.writeValueAsString(order);
        Result = mockMvc.perform(MockMvcRequestBuilders.post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(content));
    }

    // Scenario: User wants to delete an order
    @When("user makes DELETE request to delete an order")
    public void userMakesDELETERequestToDeleteOrder() throws Exception {
        Mockito.when(orderRepository.existsById(orderId)).thenReturn(true);
        Result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/orders/" + orderId));
    }

    // Scenario: User wants to update an order
    @When("user makes PUT request to update an order")
    public void userMakesPUTRequestToUpdateAnOrder() throws Exception {
        String content = objectMapper.writeValueAsString(order);

        Result = mockMvc.perform(MockMvcRequestBuilders.put("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    // Scenario: User wants to retrieve a non-existing order
    @Given("user has an invalid order ID")
    public void userHasAnInvalidOrderIdForRetrieving() {
        invalidOrderId = 999L;
        Mockito.when(orderRepository.findById(invalidOrderId)).thenReturn(Optional.empty());
    }

    @When("user makes bad GET request for order by ID")
    public void userMakesBadGETRequestForOrderById() throws Exception {
        Result = mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/" + invalidOrderId));
    }

    // Scenario: User wants to delete a non-existing order
    @When("user makes bad DELETE request for order by ID")
    public void userMakesBadDELETERequestForOrderById() throws Exception {
        Result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/orders/" + invalidOrderId));
    }

    // Scenario: User wants to save invalid order with API
    @Given("user has an invalid order data")
    public void userHasAnInvalidOrderData() {
        order = new Order();
        Mockito.when(orderRepository.save(order)).thenReturn(order);
    }

    //success status
    @Then("system returns order success status")
    public void systemReturnsStatus() throws Exception {
//        Result.andDo(print());
        Result.andExpect(status().is2xxSuccessful());
    }

    //failure status
    @Then("system returns order failure status")
    public void systemReturnsFailureStatus() throws Exception {
        Result.andExpect(status().is4xxClientError());
//        Result.andDo(print());
    }
}
