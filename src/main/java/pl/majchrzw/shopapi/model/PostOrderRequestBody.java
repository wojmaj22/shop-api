package pl.majchrzw.shopapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOrderRequestBody {
	
	private String user;
	
	private Date orderDate;
	
	private OrderStatus orderStatus;
}
