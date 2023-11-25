package pl.majchrzw.shopapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	public Page<Product> getProductsPaginated(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam("sort") Optional<String> sortParam){
		String sort = sortParam.orElse("id,asc");
		String[] _sort = sort.split(",");
		if (_sort[1].equals("desc")){
			return productService.getAllProductsPaginated(PageRequest.of(page.orElse(0),size.orElse(20), Sort.by(Sort.Direction.DESC,_sort[0])));
		} else {
			return productService.getAllProductsPaginated(PageRequest.of(page.orElse(0),size.orElse(20), Sort.by(Sort.Direction.ASC, _sort[0])));
		}
	}
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable Long id){
		return productService.getProductById(id);
	}
	
	@PostMapping
	public ResponseEntity<String> postProduct(@RequestBody @Valid Product product){
		productService.createProduct(product);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<String> putProduct(@RequestBody @Valid Product product){
		productService.createProduct(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<String> patchProduct(@RequestBody @Valid Product product){
		productService.patchProduct();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
