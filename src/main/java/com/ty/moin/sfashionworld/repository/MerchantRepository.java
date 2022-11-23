package com.ty.moin.sfashionworld.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.moin.sfashionworld.dto.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

	Optional<Merchant> findByEmail(String email);
	Optional<Merchant> findByPhone(long phone);
	Optional<Merchant> findByOtp(int otp);
}
