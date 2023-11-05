package pl.majchrzw.shopapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.validation.annotation.Validated;
import pl.majchrzw.shopapi.model.Product;

@RepositoryRestResource(path = "products", collectionResourceRel = "products")
@Validated
public interface ProductRepository extends JpaRepository<Product, Long> {
}