package pl.majchrzw.shopapi.model;

import lombok.Data;

@Data
public class AddToCartRequestBody {
	private int quantity;
	
	private long id;
}
