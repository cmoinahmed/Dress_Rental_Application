package com.ty.moin.sfashionworld.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ty.moin.sfashionworld.dto.Dress;
import com.ty.moin.sfashionworld.repository.DressRepository;

@Repository
public class DressDao {

	@Autowired
	private DressRepository dressRepository;

	public Dress saveDress(Dress dress) {
		return dressRepository.save(dress);
	}

	public Dress getDressById(int id) {
		Optional<Dress> optional = dressRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public List<Dress> getAllDresses() {
		return dressRepository.findAll();
	}

	public void deleteDress(Dress dress) {
		dressRepository.delete(dress);
	}

	public List<Dress> getDressesByPriceRange(double lowPrice, double highPrice) {
		return dressRepository.findByRentPrice(lowPrice, highPrice);
	}

	public List<Dress> getDressesBySize(String size) {
		return dressRepository.findBySize(size);
	}

	public List<Dress> getDressesByBrandName(String brandName) {
		return dressRepository.findByBrandName(brandName);
	}

	public List<Dress> getDressByName(String dressName) {
		return dressRepository.findByName(dressName);
	}

}
