package com.ty.moin.sfashionworld.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.moin.sfashionworld.dto.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

	Optional<Admin> findByEmail(String email);

	Optional<Admin> findByPhone(long phone);

	Optional<Admin> findByUserName(String username);
}
