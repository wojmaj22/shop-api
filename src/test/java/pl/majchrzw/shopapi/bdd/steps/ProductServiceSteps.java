package pl.majchrzw.shopapi.bdd.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import pl.majchrzw.shopapi.dao.ProductRepository;
import pl.majchrzw.shopapi.model.Product;
import pl.majchrzw.shopapi.service.ProductService;

import java.util.Optional;

@SpringBootTest
public class ProductServiceSteps {
	
	@InjectMocks
	@Autowired
	private ProductService service;
	@MockBean
	@Autowired
	private ProductRepository repository;
	
	private long productId;
	private Product product;
	private Product retrievedProduct;
	
	@Given("Product id")
	public void product_id(){
		product = new Product(1L, "product", 12.66, 55);
		productId = 1L;
		Mockito.when(repository.findById(productId)).thenReturn(Optional.of(product));
	}
	
	@When("I retrieve data")
	public void i_retrieve_data(){
		retrievedProduct = service.getProductById(productId);
		System.out.println(retrievedProduct.toString());
	}

	@Then("I receive data")
	public  void i_receive_data(){
		Assertions.assertEquals(product,retrievedProduct);
	}
}

