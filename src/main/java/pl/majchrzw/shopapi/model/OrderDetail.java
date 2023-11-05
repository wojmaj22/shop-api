package pl.majchrzw.shopapi.model;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "orderdetail")
@Getter
@Builder
@NoArgsConstructor
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "price")
	private double price;
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public void setQuantity(int quantity) {
		if( product == null){
			throw new IllegalStateException("Cannot set quantity of null product");
		}
		this.quantity = quantity;
		this.price = Math.round(quantity*product.getPrice()*100)/100.0;
	}
	
	public OrderDetail(Long id, Order order, Product product, int quantity, double price) {
		this.id = id;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.price = Math.round(quantity*product.getPrice()*100)/100.0;
	}
	
	public OrderDetail(Long id, Order order, Product product, int quantity) {
		this.id = id;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.price = Math.round(quantity*product.getPrice()*100)/100.0;
	}
}
