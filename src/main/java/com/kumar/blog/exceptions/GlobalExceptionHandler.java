package com.kumar.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kumar.blog.payloads.ApiResponse;

// controller handle exception and proper message send to client

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private String defaultMessage;

	// provider what type of class handle
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new  ApiResponse(message, false);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

	// create exception handle for field request time bean validation 2.0
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String, String>> (resp, HttpStatus.BAD_REQUEST);
	}
	// password has wrong exception
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handlerApiException(ApiException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new  ApiResponse(message, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
}
