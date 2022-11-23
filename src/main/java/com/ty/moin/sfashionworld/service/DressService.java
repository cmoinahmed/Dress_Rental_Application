package com.ty.moin.sfashionworld.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.moin.sfashionworld.dao.DressDao;
import com.ty.moin.sfashionworld.dto.Dress;
import com.ty.moin.sfashionworld.dto.ResponseStructure;
import com.ty.moin.sfashionworld.excepion.DressNotFoundException;
import com.ty.moin.sfashionworld.excepion.IdNotFoundException;
import com.ty.moin.sfashionworld.excepion.InvalidCredentialException;

@Service
public class DressService {

	@Autowired
	private DressDao dressDao;

	public ResponseEntity<ResponseStructure<Dress>> saveDress(Dress dress) {
		if (dress.getMerchant() != null) {
			ResponseStructure<Dress> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(dressDao.saveDress(dress));

			return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
		}else {
			throw new InvalidCredentialException("Required Merchant Data");
		}
	}

	public ResponseEntity<ResponseStructure<Dress>> updateDress(Dress dress) {
		ResponseStructure<Dress> responseStructure = new ResponseStructure<>();

		Dress dress2 = dressDao.getDressById(dress.getId());
		if (dress2 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(dressDao.saveDress(dress));

			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Dress id : " + dress.getId());
		}
	}

	public ResponseEntity<ResponseStructure<Dress>> getDressById(int id) {
		ResponseStructure<Dress> responseStructure = new ResponseStructure<>();
		Dress dress = dressDao.getDressById(id);
		if (dress != null) {
			responseStructure.setStatus(HttpStatus.FOUND.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(dress);

			return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new IdNotFoundException("Dress Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Dress>>> getAllDresses() {
		ResponseStructure<List<Dress>> responseStructure = new ResponseStructure<>();
		List<Dress> dresses = dressDao.getAllDresses();

		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("SUCCESS");
		responseStructure.setData(dresses);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Dress>> deleteDressById(int id) {
		ResponseStructure<Dress> responseStructure = new ResponseStructure<>();
		Dress dress = dressDao.getDressById(id);
		if (dress != null) {
			dressDao.deleteDress(dress);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Deleted Successfully");
			responseStructure.setData(dress);

			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Dress Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Dress>>> getDressesByRange(double lowPrice, double highPrice) {
		ResponseStructure<List<Dress>> responseStructure = new ResponseStructure<>();
		List<Dress> dresses = dressDao.getDressesByPriceRange(lowPrice, highPrice);

		if (dresses.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(dresses);

			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DressNotFoundException("No Dresses Found For This Price Range :" + lowPrice + "," + highPrice);
		}
	}

	public ResponseEntity<ResponseStructure<List<Dress>>> getDressesByBrandName(String brandName) {
		ResponseStructure<List<Dress>> responseStructure = new ResponseStructure<>();
		List<Dress> dresses = dressDao.getDressesByBrandName(brandName);
		if (dresses.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(dresses);

			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DressNotFoundException("No Dresses Found for the Brand : " + brandName);
		}
	}

	public ResponseEntity<ResponseStructure<List<Dress>>> getDressesBySize(String size) {
		ResponseStructure<List<Dress>> responseStructure = new ResponseStructure<>();
		List<Dress> dresses = dressDao.getDressesBySize(size);
		if (dresses.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(dresses);

			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DressNotFoundException("No Dresses Found for the size : " + size);
		}
	}

	public ResponseEntity<ResponseStructure<List<Dress>>> getDressesByName(String name) {
		ResponseStructure<List<Dress>> responseStructure = new ResponseStructure<>();
		List<Dress> dresses = dressDao.getDressByName(name);
		if (dresses.size() > 0) {
			responseStructure.setStatus(HttpStatus.FOUND.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(dresses);
			return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new DressNotFoundException("No Dress Found for the DressName : " + name);
		}
	}

}
