package com.srtech.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.srtech.entity.UserDetails;

@Repository
public interface UserRespository extends JpaRepository<UserDetails, Integer> {

}
