package com.srtech.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.srtech.dto.AddressDTO;
import com.srtech.dto.UserDTO;
import com.srtech.dto.UserResponse;
import com.srtech.dto.UserRespository;
import com.srtech.entity.AddressEntity;
import com.srtech.entity.UserEntity;
import com.srtech.exception.InvalidNameException;
import com.srtech.repository.PagingAndSortingUserRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

	private static Map<Integer, String> usersDataBase = null;
	
	@Autowired
	private UserRespository userRespository;
	
	@Autowired 
	private PagingAndSortingUserRepository pagingAndSortingUserRepository;

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
	public UserResponse getUserDetails(@RequestParam Integer id) {
		
		//DB Lookup
		Optional<UserEntity> optionalEntiry= userRespository.findById(id);
		if(!optionalEntiry.isPresent()) {
			throw new RuntimeException("No user foudn for the given id "+id);
		}
		
		//Its UserResponse wrapper
		UserResponse response = new UserResponse();
		
		//GEt User entity object
		UserEntity userEntity= optionalEntiry.get();
		log.debug("User Entity : {}",userEntity);
		
		//Its DTO for creating ther response payload
		UserDTO userDTO=new UserDTO();
		userDTO.setEmail(userEntity.getName());
		userDTO.setName(userEntity.getName());
		userDTO.setYearOfBirth(userEntity.getYearOfBirth());
		
		response.setSuccess(true);
		response.setMessage("User retrival is success!!");
		response.setData(userDTO);
		
		return response;
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
	public ResponseEntity<UserResponse> addUser(@RequestBody UserDTO userDTO) {

		if (userDTO.getName().contains("%") || userDTO.getName().contains("_")) {
			throw new InvalidNameException("Found name is "+userDTO.getName() +" It has invalid characters. Please use the correct name and retry!!");
		}
		if (userDTO.getEmail().isBlank()) {
			throw new InvalidNameException("Email is required.");
		}
		
		//Creating user entiry object
		UserEntity userEntity=new UserEntity(); //Detached State
		
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setName(userDTO.getName());
		userEntity.setYearOfBirth(userDTO.getYearOfBirth());
		List<AddressDTO> addressDTOs= userDTO.getAddress();
		
		List<AddressEntity> addressEntities = new ArrayList<>();
		AddressEntity addressEntity=null;
		//Converting to AddressEntity from AddressDTO
		if(addressDTOs!=null && addressDTOs.size()>0) {
			for(int i=0;i<addressDTOs.size();i++) {
				addressEntity=new AddressEntity();
				addressEntity.setAddressLine(addressDTOs.get(i).getAddressLine());
				addressEntity.setState(addressDTOs.get(i).getState());
				addressEntity.setZipCode(addressDTOs.get(i).getZipCode());
			}
			addressEntities.add(addressEntity);
		}
		//Set to User entity
		userEntity.setAddresses(addressEntities);
		
		//Save in database
		UserEntity userEntity2= userRespository.save(userEntity); //Attached State
		
		//Create Response Object
		UserResponse userResponse = new UserResponse();
		userResponse.setSuccess(true);
		userResponse.setMessage("User is created in DB successfully with ID: "+userEntity2.getId());
		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}

	// 1. Write a method to fitler the users by Gender
	// 2. Write a method to fitler the users by Gender & Year of birth

	// In case you find the data: Status codes should be -- 200
	// In case you dont find the data: Status codes should be -- 404
	
	
	@GetMapping("/user/list/{birthYear}")
	public List<UserDTO> getUsersByBirthYears(@PathVariable String birthYear) {
		// Transforming the map to list of strings
		log.debug("Retriving users search results for matched birth year :{}",birthYear);
		List<UserEntity> userEntities= userRespository.findByYearOfBirth(birthYear);
		
		List<UserDTO> responseList = new ArrayList<>();
		UserDTO userDTO = null;
		for(UserEntity userEntity:userEntities) {
			userDTO=new UserDTO(
					userEntity.getName(),
					userEntity.getEmail(),
					userEntity.getYearOfBirth()
					);
			responseList.add(userDTO);
		}
		
		
		//TODO: Dean ,Return 404 status code  if no search results found for a given birth year.
		return responseList;
		/*userEntities.stream()
				.map(userEntity -> new UserDTO(
							userEntity.getName(),
							userEntity.getEmail(),
							userEntity.getYearOfBirth()
							)
				).collect(Collectors.toList());
				*/
	}
	
	//Get the user_entity info page wise
	@GetMapping("/user/pagination/{pageNo}")
	public Page getUsersByPageNo(@PathVariable Integer pageNo) {
		
		//Creating the Pageble Request with pageNo & no of records per page.
		Pageable pageRequest= PageRequest.of(pageNo, 10);

		Page page=  pagingAndSortingUserRepository.findAll(pageRequest);
		
		log.debug("page no of elements :{}",page.getNumberOfElements());
		log.debug("Total No Of Pages :{}",page.getTotalPages());
		log.debug("Page Content :{}",page.getContent());
		
		List pageResult= page.getContent();
		
		return  page;
	}
	
	
	//TODO: Write a new end point to fetch the details from database for a give nemail id.
	//If email doent have @ or . ,Stop process the reuest and throw exception
	//Service should ignore case sensitive
	//If any email has multiple records ,Return all
	
	
	
}
