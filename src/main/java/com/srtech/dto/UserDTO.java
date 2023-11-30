package com.srtech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDTO {
	
	private String name;
	
	private String email;
	
	//@JsonAlias("year_of_birth")
	@JsonProperty("year_of_birth")
	private String yearOfBirth;
	
	public UserDTO() {
		
	}

	/*
	 * public UserDTO(String name, String email, String yearOfBirth) { super();
	 * this.name = name; this.email = email; this.yearOfBirth = yearOfBirth; }
	 */
	
	
}
