package com.ty.moin.sfashionworld.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ty.moin.sfashionworld.dto.Customer;
import com.ty.moin.sfashionworld.repository.CustomerRepository;

@Repository
public class CustomerDao {

	@Autowired
	private CustomerRepository customerRepository;

	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer findCustomerById(int id) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public List<Customer> getAllCustomers(int merchantId) {
		return customerRepository.findAll(merchantId);
	}

	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);

	}

	public Customer findCustomerByEmail(String email) {
		Optional<Customer> optional = customerRepository.findByEmail(email);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public Customer findCustomerByPhone(long phone) {
		Optional<Customer> optional = customerRepository.findByPhone(phone);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public Customer findCustomerByOtp(int otp) {
		Optional<Customer> optional = customerRepository.findByOtp(otp);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

}
