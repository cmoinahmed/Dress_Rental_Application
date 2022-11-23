package com.ty.moin.sfashionworld.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ty.moin.sfashionworld.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	Optional<Customer> findByPhone(long phone);
	
	Optional<Customer> findByEmail(String email);
	
	Optional<Customer> findByOtp(int otp);
	
	@Query(nativeQuery =true, value = ("SELECT *,0 as clazz FROM Customer c,user u  WHERE u.id=c.id and c.id In (select customer_id from Rent "
			+ "where id In (select rent_id from Rent_dress where dress_id In "
			+ "(select id from Dress where merchant_id=?)))"))
	List<Customer> findAll(int merchantId);


}
