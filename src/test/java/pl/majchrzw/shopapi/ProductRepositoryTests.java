package pl.majchrzw.shopapi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.majchrzw.shopapi.dao.ProductRepository;
import pl.majchrzw.shopapi.model.Product;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@SpringBootTest
public class ProductRepositoryTests {

	private final ProductRepository productRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryTests.class);
	
	@Autowired
	public ProductRepositoryTests(ProductRepository repository){
		productRepository = repository;
	}
	
	@AfterEach
	public void cleanUp(){
		productRepository.deleteAll();
	}
	
	@Test
	public void shouldAddProductToRepository(){
		logger.info("Testing database for create");
		// given
		Product product = new Product(2L, "Produkt", 12.99, 50, new ArrayList<>());
		// when
		productRepository.save(product);
		// then
		Assertions.assertEquals(product, productRepository.findById(2L).get());
		logger.info("Create test successful");
	}
	
	@Test
	public void shouldThrowExceptionWhenReadingNonExistingStudent(){
		// given
		long id = 100L;
		// when, then
		Assertions.assertThrows(NoSuchElementException.class, () -> productRepository.findById(id).get());
	}
	
	@Test
	public void shouldGetProductWhenAddedToRepository(){
		// given
		Product product = new Product(1L, "Produkt", 12.99, 50, new ArrayList<>());
		// when
		productRepository.save(product);
		// then
		Assertions.assertEquals(product, productRepository.findById(1L).get());
	}
	
	@Test
	public void shouldUpdateProductWhenUpdatedInRepository(){
		// given
		Product product = new Product(1L, "Produkt", 12.99, 50, new ArrayList<>());
		// when
		productRepository.save(product);
		product.setName("AnotherName");
		Product productFromDB = productRepository.save(product);
		// then
		Assertions.assertEquals(product, productFromDB);
	}
	
	@Test
	public void shouldDeleteProductFromRepository(){
		// given
		Product product = new Product(1L, "Produkt", 12.99, 50, new ArrayList<>());
		// when
		productRepository.save(product);
		productRepository.deleteById(1L);
		// then
		Assertions.assertFalse(productRepository.existsById(1L));
	}
}
