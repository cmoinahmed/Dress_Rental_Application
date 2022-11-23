package com.ty.moin.sfashionworld.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ty.moin.sfashionworld.dto.Customer;
import com.ty.moin.sfashionworld.dto.Rent;
import com.ty.moin.sfashionworld.repository.RentRepository;

@Repository
public class RentDao {

	@Autowired
	private RentRepository rentRepository;

	public Rent saveRent(Rent rent) {
		return rentRepository.save(rent);
	}

	public List<Rent> getAllRents() {
		return rentRepository.findAll();
	}

	public Rent getRentById(int id) {
		Optional<Rent> optional = rentRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public void deleteRent(Rent rent) {
		rentRepository.delete(rent);
	}

	public List<Rent> findRentByCustomer(Customer customer) {
		return rentRepository.findByCustomer(customer);
	}

	public List<Rent> findByRentDate(LocalDate rentDate) {
		return rentRepository.findByRentDate(rentDate);
	}
}
