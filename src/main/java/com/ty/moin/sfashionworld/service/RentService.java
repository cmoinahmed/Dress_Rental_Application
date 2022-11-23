package com.ty.moin.sfashionworld.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.moin.sfashionworld.dao.CustomerDao;
import com.ty.moin.sfashionworld.dao.DressDao;
import com.ty.moin.sfashionworld.dao.RentDao;
import com.ty.moin.sfashionworld.dto.Customer;
import com.ty.moin.sfashionworld.dto.Dress;
import com.ty.moin.sfashionworld.dto.Rent;
import com.ty.moin.sfashionworld.dto.RentDress;
import com.ty.moin.sfashionworld.dto.ResponseStructure;
import com.ty.moin.sfashionworld.excepion.DateNotFoundException;
import com.ty.moin.sfashionworld.excepion.IdNotFoundException;
import com.ty.moin.sfashionworld.util.CalculateDays;
import com.ty.moin.sfashionworld.util.EmailSender;

@Service
public class RentService {
	@Autowired
	private RentDao rentDao;

	@Autowired
	private DressDao dressDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private EmailSender emailSender;

	public ResponseEntity<ResponseStructure<Rent>> saveRent(int custId, Rent rent) {

		String d1 = rent.getRentDate().toString();
		String d2 = rent.getReturnDate().toString();
		long days = CalculateDays.findDifference(d1, d2);

		List<Dress> dresses = rent.getDresses();

		double totalAmt = 0;
		if (days <= 4) {

			for (Dress d : dresses) {
				totalAmt = totalAmt + (d.getRentPrice() * d.getQuantity() * 4);
			}
		} else {

			for (Dress d : dresses) {
				totalAmt = totalAmt + (d.getRentPrice() * d.getQuantity() * days);
			}
		}
		rent.setTotalRentPrice(totalAmt);
		rent.setCustomer(customerDao.findCustomerById(custId));

		ResponseStructure<Rent> responseStructure = new ResponseStructure<Rent>();

		List<RentDress> rentDresses = new ArrayList<>();
		for (Dress dress : dresses) {
			RentDress rentDress1 = new RentDress();
			rentDress1.setQuantity(dress.getQuantity());
			rentDress1.setDressId(dress.getId());
			rentDress1.setRent(rent);
			rentDresses.add(rentDress1);

		}
		rent.setRentDresses(rentDresses);
		Rent rent2 = rentDao.saveRent(rent);
		rent2.setDresses(dresses);
		emailSend(rent2);

		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("CREATED");
		rent2.getDresses().stream().forEach(dress -> System.out.println(dress.getQuantity()));
		responseStructure.setData(rent2);

		return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
	}

	public void emailSend(Rent rent) {

		List<Dress> dresses = rent.getDresses();

		String details = "";
		for (Dress dress : dresses) {
			details += "\nBrand - " + dress.getBrandName() + "\n";
			details += "Name - " + dress.getName() + "\n";
			details += "Rent price - " + dress.getRentPrice() + "\n";
			details += "Deposit price - " + dress.getDepositPrice() + "\n";
			details += "Mrp Price - " + dress.getMrpPrice() + "\n";
			details += "Dress Size - " + dress.getSize() + "\n";
			details += "Quantity - " + dress.getQuantity();
		}

		for (Dress dress : dresses) {
			Dress dress2 = dressDao.getDressById(dress.getId());
			dress2.setQuantity(dress2.getQuantity() - dress.getQuantity());
			dressDao.saveDress(dress2);
		}

		emailSender.sendSimpleEmail(rent.getCustomer().getEmail(),

				"Your Id is " + rent.getCustomer().getId() + "\n" + " Your Dress Is Booked \n" + "  From - "
						+ rent.getRentDate() + "\n" + " To - " + rent.getReturnDate() + "\n"
						+ " And The Total Rent Price is " + rent.getTotalRentPrice() + "\n" + details,
				"Welcome To Moin's Fashion World - " + rent.getCustomer().getUserName());

	}

	public ResponseEntity<ResponseStructure<List<Rent>>> getAllRents() {
		ResponseStructure<List<Rent>> responseStructure = new ResponseStructure<List<Rent>>();
		responseStructure.setStatus(HttpStatus.FOUND.value());
		responseStructure.setMessage("SUCCESS");
		responseStructure.setData(rentDao.getAllRents());
		return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
	}

	public ResponseEntity<ResponseStructure<Rent>> getRentById(int id) {
		ResponseStructure<Rent> responseStructure = new ResponseStructure<Rent>();
		Rent rent = rentDao.getRentById(id);
		if (rent != null) {
			responseStructure.setStatus(HttpStatus.FOUND.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(rent);
			return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new IdNotFoundException("Id not found:" + id);
		}
	}

	public ResponseEntity<ResponseStructure<Rent>> deleteRentById(int id) {
		ResponseStructure<Rent> responseStructure = new ResponseStructure<Rent>();
		Rent rent1 = rentDao.getRentById(id);
		if (rent1 != null) {
			rentDao.deleteRent(rent1);

			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(rent1);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		}
		throw new IdNotFoundException("Id not found:" + id);
	}

	public ResponseEntity<ResponseStructure<List<Rent>>> findByRentDate(LocalDate rentDate) {
		ResponseStructure<List<Rent>> responseStructure = new ResponseStructure<List<Rent>>();
		List<Rent> rents = rentDao.findByRentDate(rentDate);
		if (rents.size() > 0) {
			responseStructure.setStatus(HttpStatus.FOUND.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(rents);
			return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new DateNotFoundException("Date not found:" + rentDate);
		}
	}

	public ResponseEntity<ResponseStructure<List<Rent>>> findRentByCustomer(Customer customer) {
		ResponseStructure<List<Rent>> responseStructure = new ResponseStructure<List<Rent>>();
		List<Rent> rents = rentDao.findRentByCustomer(customer);
		if (rents.size() > 0) {
			responseStructure.setStatus(HttpStatus.FOUND.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(rents);
			return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new IdNotFoundException("Id not found:" + customer.getId());
		}
	}
}
