package com.srtech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDTO {
	
	private String name;
	
	private String email;
	
	//@JsonAlias("year_of_birth")
	@JsonProperty("year_of_birth")
	private String yearOfBirth;
	
}
