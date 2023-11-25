package pl.majchrzw.shopapi.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOrderRequestBody {

	@NotNull( message = "Field cannot be null")
	private String user;

	@NotNull( message = "Field cannot be null")
	private Date orderDate;

	@NotNull( message = "Field cannot be null")
	private OrderStatus orderStatus;
}
