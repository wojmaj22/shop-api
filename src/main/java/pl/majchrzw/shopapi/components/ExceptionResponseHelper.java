package pl.majchrzw.shopapi.components;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.majchrzw.shopapi.model.DefaultErrorResponse;

import java.time.Instant;
import java.util.Set;

@ControllerAdvice
public class ExceptionResponseHelper {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<DefaultErrorResponse> ConstraintViolationExceptionHandler(ConstraintViolationException exception){
		DefaultErrorResponse errorResponse = new DefaultErrorResponse();
		StringBuilder sb = new StringBuilder();
		Set<ConstraintViolation<?>> errors = exception.getConstraintViolations();
		for ( ConstraintViolation<?> violation: errors) {
			sb.append(violation.getMessage());
			sb.append(".");
		}
		errorResponse.setTimestamp(Instant.now());
		errorResponse.setMessage(sb.toString());
		errorResponse.setError("Validation error");
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<DefaultErrorResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException exception){
		DefaultErrorResponse errorResponse = new DefaultErrorResponse();
		errorResponse.setTimestamp(Instant.now());
		errorResponse.setError("Object not found");
		errorResponse.setMessage(exception.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
