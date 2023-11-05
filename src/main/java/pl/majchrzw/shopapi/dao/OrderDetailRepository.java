package pl.majchrzw.shopapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.majchrzw.shopapi.model.OrderDetail;

@RepositoryRestResource(path = "order_details", collectionResourceRel = "order_details")
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}