package pl.majchrzw.shopapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.majchrzw.shopapi.model.Product;
import pl.majchrzw.shopapi.service.ProductService;

import java.util.Optional;

@RestController()
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	@GetMapping
	public Page<Product> getProductsPaginated(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size){
		return productService.getAllProductsPaginated(PageRequest.of(page.orElse(0),size.orElse(20)));
	}
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable Long id){
		return productService.getProductById(id);
	}
	
	@PostMapping
	public ResponseEntity<String> postProduct(@RequestBody Product product){
		productService.createProduct(product);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<String> putProduct(@RequestBody Product product){
		productService.createProduct(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<String> patchProduct(@RequestBody Product product){
		productService.patchProduct();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
