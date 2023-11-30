package com.srtech.dto;

import java.util.Map;

import lombok.Data;

@Data
public class UserResponse {

	private boolean success;
	private String message;
	private Object data;
	
}
