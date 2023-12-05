package com.srtech.dto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srtech.entity.UserEntity;

@Repository
public interface UserRespository extends JpaRepository<UserEntity, Integer> {

	//Write a service to get all the matched users for given birth year.
	//QueryMethods
	
	//select * from UserEntity where YearOfBirth=yearOfBirth;
	List<UserEntity> findByYearOfBirth(String yearOfBirth);
	
}
