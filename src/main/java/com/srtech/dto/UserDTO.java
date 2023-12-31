package com.srtech.dto;

import java.util.List;

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
	

	List<AddressDTO> address;

	public UserDTO() {
		
	}
	
}
