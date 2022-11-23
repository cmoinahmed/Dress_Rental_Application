package com.ty.moin.sfashionworld.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.moin.sfashionworld.dto.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUserName(String username);
	
	

}
