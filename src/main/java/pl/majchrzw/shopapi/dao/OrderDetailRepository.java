package pl.majchrzw.shopapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.majchrzw.shopapi.model.OrderDetail;
import pl.majchrzw.shopapi.model.Product;

import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	
	public Optional<OrderDetail> findOrderDetailByProduct(Product product);
	
}