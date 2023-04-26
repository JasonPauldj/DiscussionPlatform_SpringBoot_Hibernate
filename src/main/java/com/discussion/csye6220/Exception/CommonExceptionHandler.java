package com.discussion.csye6220.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class CommonExceptionHandler {
	
	@ExceptionHandler({MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class}) 
	public ResponseEntity<?> handleBadRequestErrors() 
	{
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
}
