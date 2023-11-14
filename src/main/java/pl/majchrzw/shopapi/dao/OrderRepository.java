package pl.majchrzw.shopapi.dao;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.majchrzw.shopapi.model.Order;
import pl.majchrzw.shopapi.model.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	public List<Order> findAllByUser(String username);
	
	public List<Order> findAllByUserAndOrderStatus(String username, OrderStatus orderStatus);
	
	public boolean existsByUserAndOrderStatus(String username, OrderStatus orderStatus);
}