package com.example.demo.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
//	we have created handler methods for specific exception here we are creating in whole project
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handlerNullPointerException(NullPointerException ex){
		logger.info("it is null pointer exception from gloabl handler");
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
