package com.srtech.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

//Object Relational Mapper
@Data
@Entity
@Table(name="USER_DETAILS") //Optional
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="NAME")
	private String name;
	
	@Column
	private String email;
	
	@Column(name="YEAR_OF_BIRTH")
	private String yearOfBirth;

	@OneToMany
	@JoinColumn(name = "user_id")
	private List<AddressEntity> addresses;
}
