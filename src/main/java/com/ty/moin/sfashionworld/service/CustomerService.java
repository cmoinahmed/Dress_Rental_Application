package com.ty.moin.sfashionworld.service;

import static com.ty.moin.sfashionworld.util.Aes.decrypt;
import static com.ty.moin.sfashionworld.util.Aes.encrypt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.moin.sfashionworld.dao.CustomerDao;
import com.ty.moin.sfashionworld.dto.Customer;
import com.ty.moin.sfashionworld.dto.ResponseStructure;
import com.ty.moin.sfashionworld.dto.User;
import com.ty.moin.sfashionworld.excepion.EmailIdNotFoundException;
import com.ty.moin.sfashionworld.excepion.IdNotFoundException;
import com.ty.moin.sfashionworld.excepion.InvalidCredentialException;
import com.ty.moin.sfashionworld.excepion.PhoneNumberNotFoundException;
import com.ty.moin.sfashionworld.util.EmailSender;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private EmailSender emailSender;

	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(Customer customer) {
		customer.setPassword(encrypt(customer.getPassword()));

		emailSender.sendSimpleEmail(customer.getEmail(), "Greetings \n Your Profile in Moin's Fashion World Created",
				"Profile Created");

		ResponseStructure<Customer> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("Success");
		structure.setData(customerDao.saveCustomer(customer));

		return new ResponseEntity<>(structure, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<Customer>> findCustomerById(int id) {
		ResponseStructure<Customer> structure = new ResponseStructure<>();
		Customer customer = customerDao.findCustomerById(id);
		if (customer != null) {
			customer.setPassword(customer.getPassword());
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("Success");
			structure.setData(customer);
			return new ResponseEntity<>(structure, HttpStatus.FOUND);
		} else {
			throw new IdNotFoundException("Id Not Found" + id);

		}
	}

	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomers(int merchantId) {
		ResponseStructure<List<Customer>> structure = new ResponseStructure<>();
		List<Customer> users = customerDao.getAllCustomers(merchantId);
		for (User user : users) {
			user.setPassword(user.getPassword());
		}

		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("Success");
		structure.setData(users);

		return new ResponseEntity<>(structure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Customer>> deleteCustomerById(int id) {
		ResponseStructure<Customer> structure = new ResponseStructure<Customer>();
		Customer customer = customerDao.findCustomerById(id);
		if (customer != null) {
			customerDao.deleteCustomer(customer);
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Deleted Successfully");
			structure.setData(customer);
			return new ResponseEntity<>(structure, HttpStatus.OK);

		} else {
			throw new IdNotFoundException("Customer Id" + id + "Not Found");
		}

	}

	public ResponseEntity<ResponseStructure<Customer>> findCustomerByEmail(String email) {
		ResponseStructure<Customer> structure = new ResponseStructure<>();
		Customer customer = customerDao.findCustomerByEmail(email);
		if (customer != null) {
			customer.setPassword(customer.getPassword());
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("FOUND");
			structure.setData(customer);
			return new ResponseEntity<>(structure, HttpStatus.FOUND);

		} else {
			throw new EmailIdNotFoundException("Invalid email :" + email);
		}

	}

	public ResponseEntity<ResponseStructure<Customer>> findCustomerByPhone(long phone) {
		ResponseStructure<Customer> structure = new ResponseStructure<>();
		Customer customer = customerDao.findCustomerByPhone(phone);
		if (customer != null) {
			customer.setPassword(customer.getPassword());
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setMessage("FOUND");
			structure.setData(customer);
			return new ResponseEntity<>(structure, HttpStatus.FOUND);

		} else {
			throw new PhoneNumberNotFoundException("Invalid Phone Number :" + phone);

		}

	}

	public ResponseEntity<ResponseStructure<Object>> validateCustomerByEmail(String email, String password) {

		ResponseStructure<Object> responseStructure = new ResponseStructure<>();

		Customer customer = customerDao.findCustomerByEmail(email);

		if (customer != null) {
			String password1 = decrypt(customer.getPassword());

			if (password1.equals(password)) {

				int otp = (int) (Math.random() * (9999 - 1000) + 1000);
				customer.setOtp(otp);
				customerDao.saveCustomer(customer);
				emailSender.sendSimpleEmail(customer.getEmail(), "https://localhost:8082/customers",
						"please click the link and enter otp" + otp);

				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setData("Otp Sent Successfully");
				responseStructure.setMessage("Success");
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new InvalidCredentialException("Invalid Credentials");
			}

		} else {
			throw new EmailIdNotFoundException("Invalid Email");
		}

	}

	public ResponseEntity<ResponseStructure<Customer>> validateOtp(int otp) {

		Customer customer = customerDao.findCustomerByOtp(otp);
		if (customer != null) {
			ResponseStructure<Customer> responseStructure = new ResponseStructure<Customer>();
			customer.setPassword(customer.getPassword());
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setData(customer);
			responseStructure.setMessage("Success");
			return new ResponseEntity<ResponseStructure<Customer>>(responseStructure, HttpStatus.OK);

		} else {
			throw new InvalidCredentialException("Invalid OTP");
		}

	}

	public ResponseEntity<ResponseStructure<Customer>> validateCustomerByPhone(long phone, String password) {

		ResponseStructure<Customer> responseStructure = new ResponseStructure<Customer>();

		Customer customer = customerDao.findCustomerByPhone(phone);

		if (customer != null) {

			String password1 = decrypt(customer.getPassword());

			if (password1.equals(password)) {

				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setData(customer);
				responseStructure.setMessage("Success");
				return new ResponseEntity<ResponseStructure<Customer>>(responseStructure, HttpStatus.OK);
			} else {
				throw new InvalidCredentialException("Invalid Credentials");
			}

		} else {
			throw new PhoneNumberNotFoundException("Invalid Phone Number");
		}

	}

	public ResponseEntity<ResponseStructure<Customer>> updateCustomer(Customer customer) {
		ResponseStructure<Customer> responseStructure = new ResponseStructure<>();
		Customer customer1 = customerDao.findCustomerById(customer.getId());
		if (customer1 != null) {
			customer.setPassword(encrypt(customer.getPassword()));
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("success");
			responseStructure.setData(customerDao.saveCustomer(customer));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Id Not Found");
		}
	}

}