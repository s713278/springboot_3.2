package com.srtech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.srtech.dto.UserResponse;

@ControllerAdvice//Interceptor
public class AppExceptionHandler {
	
	
	@ExceptionHandler(InvalidNameException.class)
	public ResponseEntity<UserResponse> addUser(InvalidNameException exception ) {
		System.out.println("Inside InvalidNameException()");
		UserResponse response=new UserResponse();
		response.setSuccess(false);
		response.setMessage(exception.getMessage());
		ResponseEntity<UserResponse>  responseEntity=new ResponseEntity<UserResponse>(response,HttpStatus.BAD_REQUEST);
		return responseEntity;
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<UserResponse> addUser(Exception exception ) {
		System.out.println("Inside InvalidNameException()");
		UserResponse response=new UserResponse();
		response.setSuccess(false);
		response.setMessage(exception.getMessage());
		ResponseEntity<UserResponse>  responseEntity=new ResponseEntity<UserResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}
}
