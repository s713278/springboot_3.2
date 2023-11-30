package com.srtech.dto;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.srtech.entity.UserEntity;

@Repository
public interface UserRepository2 extends PagingAndSortingRepository<UserEntity, Integer> {

}
