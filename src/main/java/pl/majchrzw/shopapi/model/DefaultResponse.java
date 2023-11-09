package pl.majchrzw.shopapi.model;

import lombok.Data;

@Data
public class DefaultResponse<T> {
	
	private String message;
	private T body;
}
