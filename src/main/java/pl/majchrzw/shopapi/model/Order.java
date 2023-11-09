package pl.majchrzw.shopapi.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;
	
	@Column(name = "user_name")
	private String user;
	
	@Column(name = "order_date")
	private Date orderDate;
	
	@Column(name = "status")
	private OrderStatus orderStatus;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<OrderDetail> orderDetails;
	
	
}

