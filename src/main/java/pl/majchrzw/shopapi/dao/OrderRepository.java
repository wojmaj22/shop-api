package pl.majchrzw.shopapi.dao;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.majchrzw.shopapi.model.Order;
import pl.majchrzw.shopapi.model.OrderStatus;

import java.util.List;

//@RepositoryRestResource(path = "orders", collectionResourceRel = "orders")
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	public List<Order> findAllByUser(String username);
	
	public List<Order> findAllByUserAndOrderStatus(String username, OrderStatus orderStatus);
}