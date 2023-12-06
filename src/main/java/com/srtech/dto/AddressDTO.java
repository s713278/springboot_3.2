package com.srtech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddressDTO {

	private String addressLine;
	
	private String state;
	
	private Integer zipCode;
	
}
