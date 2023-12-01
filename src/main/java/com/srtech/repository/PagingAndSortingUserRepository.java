package com.srtech.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.srtech.entity.UserEntity;

@Repository
public interface PagingAndSortingUserRepository extends PagingAndSortingRepository<UserEntity, Integer> {

}
