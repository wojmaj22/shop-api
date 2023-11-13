package pl.majchrzw.shopapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.majchrzw.shopapi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}