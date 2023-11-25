package pl.majchrzw.shopapi.bdd.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.majchrzw.shopapi.bdd.tooling.SpringGlue;
import pl.majchrzw.shopapi.dao.ProductRepository;
import pl.majchrzw.shopapi.model.Product;
import pl.majchrzw.shopapi.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProductControllerSteps extends SpringGlue {
	
	long productId;
	long badProductId;
	int page;
	int size;
	Product productToGet;
	ArrayList<Product> productList;
	Page<Product> productPage;
	Product productToPost;
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ProductService service;
	
	@Autowired
	@MockBean
	ProductRepository repository;
	private ResultActions Result;
	
	@Given("user has a valid product id")
	public void user_has_a_valid_product_id() {
		productId = 1L;
		productToGet = new Product(productId, "data", 45.55, 65);
		productToGet.setId(productId);
		Mockito.when(repository.findById(productId)).thenReturn(Optional.of(productToGet));
	}
	@When("user makes GET request")
	public void user_makes_get_request() throws Exception {
		Result = mockMvc.perform(MockMvcRequestBuilders.get("/api/products/"+productId).accept(MediaType.APPLICATION_JSON_VALUE));
	}
	@Then("system returns product data")
	public void system_returns_product_data() throws Exception {
		String jsonContentExpected = objectMapper.writeValueAsString(productToGet);
		Result.andExpect(content().json(jsonContentExpected));
	}
	
	@Given("user has a valid product data")
	public void userHasAValidProductData() {
		productToPost = new Product(2L, "Test", 34.56, 88);
		Mockito.when(repository.save(productToPost)).thenReturn(productToPost);
	}
	
	@When("user makes POST request")
	public void userMakesPOSTRequest() throws Exception {
		String content = objectMapper.writeValueAsString(productToPost);
		Result = mockMvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(content));
	}

	@When("user makes PUT request")
	public void user_makes_put_request() throws Exception {
		String content = objectMapper.writeValueAsString(productToPost);
		Result = mockMvc.perform(MockMvcRequestBuilders.put("/api/products").contentType(MediaType.APPLICATION_JSON).content(content));
	}

	@Then("system returns success status")
	public void systemReturnsStatus() throws Exception {
		Result.andExpect(status().is2xxSuccessful());
	}

	@When("user makes PATCH request")
	public void userMakesPATCHRequest() throws Exception {
		String content = objectMapper.writeValueAsString(productToPost);
		Result = mockMvc.perform(MockMvcRequestBuilders.patch("/api/products").contentType(MediaType.APPLICATION_JSON).content(content));
	}

	@When("user makes DELETE request")
	public void userMakesDELETERequest() throws Exception {
		Mockito.when(repository.existsById(productId)).thenReturn(true);
		Result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/" + productId));
	}


	@Then("system returns failure status")
	public void systemReturnsFailureStatus() throws Exception {
		Result.andExpect(status().is4xxClientError());
	}

	@When("user makes bad GET request")
	public void userMakesBadGETRequest() throws Exception {
		Result = mockMvc.perform(MockMvcRequestBuilders.get("/api/products/"+badProductId).accept(MediaType.APPLICATION_JSON_VALUE));
	}

	@Given("user has an invalid product id")
	public void userHasAnInvalidProductId() {
		productId = 1L;
		badProductId = 2L;
		productToGet = new Product(productId, "data", 45.55, 65);
		Mockito.when(repository.findById(productId)).thenReturn(Optional.of(productToGet));
	}

	@When("user makes bad DELETE request")
	public void userMakesBadDELETERequest() throws Exception {
		Result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/" + badProductId));
	}

	@Given("user has an invalid product data")
	public void userHasAnInvalidProductData() {
		productToPost = new Product();
		Mockito.when(repository.save(productToPost)).thenReturn(productToPost);
	}

	@Given("user has a page and size")
	public void userHasAPageAndSize() {
		page = 0;
		size = 10;
		productToGet = new Product(productId, "data", 45.55, 65);
		productList = new ArrayList<>();
		productList.add(productToGet);
		productList.add(productToGet);
		productPage = new PageImpl<Product>(productList, PageRequest.of(page, size), productList.size());
		Mockito.when(repository.findAll(PageRequest.of(page, size))).thenReturn(productPage);
	}

	@When("user makes GET paginated request")
	public void userMakesGETPaginatedRequest() throws Exception {
		Result = mockMvc.perform(MockMvcRequestBuilders.get("/api/products?"+"page="+page+"&size="+size).accept(MediaType.APPLICATION_JSON_VALUE));
	}

	@Then("system returns list of products data")
	public void systemReturnsListOfProductsData() throws Exception {
		String jsonContentExpected = objectMapper.writeValueAsString(productPage);
		Result.andExpect(content().json(jsonContentExpected));
	}
}
