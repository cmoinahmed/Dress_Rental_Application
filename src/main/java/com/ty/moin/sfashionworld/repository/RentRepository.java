package com.ty.moin.sfashionworld.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.moin.sfashionworld.dto.Customer;
import com.ty.moin.sfashionworld.dto.Rent;

public interface RentRepository extends JpaRepository<Rent, Integer> {

	List<Rent> findByCustomer(Customer customer);

	List<Rent> findByRentDate(LocalDate rentDate);
}
