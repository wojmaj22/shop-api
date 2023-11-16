package pl.majchrzw.shopapi.bdd.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.majchrzw.shopapi.bdd.tooling.SpringGlue;
import pl.majchrzw.shopapi.dao.ProductRepository;
import pl.majchrzw.shopapi.model.Product;
import pl.majchrzw.shopapi.service.ProductService;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProductControllerSteps extends SpringGlue {
	
	long productId;
	Product productToGet;
	Product productToPost;
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ProductService service;
	
	@Autowired
	@MockBean
	ProductRepository repository;
	
	private ResultActions GetResult;
	private ResultActions PostResult;
	
	@Given("user has a valid product id")
	public void user_has_a_valid_product_id() {
		productId = 1L;
		productToGet = new Product(productId, "data", 45.55, 65);
		productToGet.setId(productId);
		Mockito.when(repository.findById(productId)).thenReturn(Optional.of(productToGet));
	}
	@When("user makes GET request")
	public void user_makes_get_request() throws Exception {
		GetResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/products/"+productId).accept(MediaType.APPLICATION_JSON_VALUE));
		
	}
	@Then("system returns product data and 200 status")
	public void system_returns_product_data() throws Exception {
		String jsonContentExpected = objectMapper.writeValueAsString(productToGet);
		GetResult.andExpect(status().is2xxSuccessful()).andExpect(content().json(jsonContentExpected));
	}
	
	@Given("user has a valid product data")
	public void userHasAValidProductData() {
		productToPost = new Product(2L, "Test", 34.56, 88);
		Mockito.when(repository.save(productToPost)).thenReturn(productToPost);
	}
	
	@When("user makes POST request")
	public void userMakesPOSTRequest() throws Exception {
		String content = objectMapper.writeValueAsString(productToPost);
		PostResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/products").contentType(MediaType.APPLICATION_JSON).content(content));
	}
	
	@Then("system returns 201 status")
	public void systemReturnsStatus() throws Exception{
		PostResult.andExpect(status().is2xxSuccessful()).andExpect(content().string(""));
	}
}
