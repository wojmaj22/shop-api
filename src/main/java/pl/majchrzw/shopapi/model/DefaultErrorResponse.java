package pl.majchrzw.shopapi.model;

import lombok.Data;

import java.time.Instant;

@Data
public class DefaultErrorResponse {
	private String message;
	private Instant timestamp;
	
	private String error;
}
