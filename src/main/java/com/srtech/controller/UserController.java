package com.srtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.srtech.dto.UserDetailsDto;
import com.srtech.dto.UserResponse;
import com.srtech.dto.UserRespository;
import com.srtech.entity.UserDetails;
import com.srtech.exception.InvalidNameException;

@RestController
@RequestMapping("/api")
public class UserController {

	private static Map<Integer, String> usersDataBase = null;
	
	@Autowired
	private UserRespository userRespository;

	static {
		System.out.println("Loading the defalt data...");
		usersDataBase = new HashMap<>();
		usersDataBase.put(1, "Dean");
		usersDataBase.put(2, "Robert");
		usersDataBase.put(3, "Swam");
		usersDataBase.put(4, "John");
		usersDataBase.put(5, "Dorothy");
		usersDataBase.put(6, "Toms");
	}

	public UserController() {

	}

	// http://localhost:8080/api/user?id=1
	@GetMapping("/user")
	public String getUserDetails(@RequestParam Integer id) {
		return usersDataBase.get(id);
	}

	// http://localhost:8080/api/user1?userId=1
	@GetMapping("/user1")
	public String getUserDetails1(@RequestParam("userId") Integer id) {
		return usersDataBase.get(id);
	}

	// http://localhost:8080/api/user2/1
	@GetMapping("/user2/{userId}")
	public String getUserDetails2(@PathVariable("userId") Integer id) {
		return usersDataBase.get(id);
	}

	@GetMapping("/user/all")
	public List<String> getUsers() {
		// Transforming the map to list of strings
		List<String> userList = usersDataBase.entrySet().stream().map(entry -> entry.getKey() + "_" + entry.getValue())
				.collect(Collectors.toList());
		return userList;
	}

	@GetMapping("/user/all/{startsWith}")
	public List<String> getUsersStartsWith(@PathVariable String startsWith) {
		// Transforming the map data to list of strings
		List<String> userList = usersDataBase.entrySet().stream().map(entry -> entry.getValue()) // Transforming
																									// key-pair value to
																									// only value
				.filter(name -> name.startsWith(startsWith)) // Filtering the value by givein startwWith String
				.collect(Collectors.toList());
		return userList;
	}

	@PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public ResponseEntity<UserResponse> addUser(@RequestBody UserDetailsDto userDetails) {

		if (userDetails.getName().contains("%") || userDetails.getName().contains("_")) {
			throw new InvalidNameException("Found name is "+userDetails.getName() +" It has invalid characters. Please use the correct name and retry!!");
		}
		
		//Creting user entiry object
		UserDetails userEntity=new UserDetails();
		userEntity.setEmail(userDetails.getEmail());
		userEntity.setName(userDetails.getName());
		
		//Save in database
		userEntity= userRespository.save(userEntity);
		
		//Create Response Object
		UserResponse userResponse = new UserResponse();
		userResponse.setSuccess(true);
		userResponse.setMessage("User added to DB successfully with ID "+userEntity.getId());
		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}

	// 1. Write a method to fitler the users by Gender
	// 2. Write a method to fitler the users by Gender & Year of birth

	// In case you find the data: Status codes should be -- 200
	// In case you dont find the data: Status codes should be -- 404

}
