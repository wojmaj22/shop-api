package pl.majchrzw.shopapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.majchrzw.shopapi.dao.ProductRepository;
import pl.majchrzw.shopapi.model.Product;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public Page<Product> getAllProductsPaginated(Pageable pageable){
		// TODO - this might not work, if so then fix this
		return productRepository.findAll(pageable);
	}
	
	public Product getProductById(Long id){
		Optional<Product> optionalProduct = productRepository.findById(id);
		if( optionalProduct.isPresent()){
			return optionalProduct.get();
		} else {
			throw new EntityNotFoundException("No product with id: " + id + ", has been found.");
		}
	}
	
	public void createProduct(Product product){
		productRepository.save(product);
	}
	
	public void deleteProduct(Long id){
		if(productRepository.existsById(id)){
			productRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("No product with id: " + id + ", has been found.");
		}
	}
	
	public void updateProduct(Long id, Product product){
		if(productRepository.existsById(id)){
			productRepository.save(product);
		} else {
			throw new EntityNotFoundException("No product with id: " + id + ", has been found.");
		}
	}
	
	public void patchProduct(){
		// TODO - implement this
	}
}
