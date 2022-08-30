package br.com.mercadinho.handle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.mercadinho.model.error.ErrorMessage;
import br.com.mercadinho.model.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
		
		ErrorMessage error = new ErrorMessage("Not Found", HttpStatus.NOT_FOUND.value(), 	exception.getMessage());
		return new ResponseEntity<>(error	, HttpStatus.NOT_FOUND);
	}	
}
