package com.srtech.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srtech.entity.UserEntity;

@Repository
public interface UserRespository extends JpaRepository<UserEntity, Integer> {

	//Write a service to get all the matched users for given birth year.
	
}
