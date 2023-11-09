package pl.majchrzw.shopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.majchrzw.shopapi.model.OrderStatus;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	private Long id;
	private String user;
	private Date orderDate;
	private OrderStatus orderStatus;
	private List<Long> orderDetailsList;
	
}
