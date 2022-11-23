package com.ty.moin.sfashionworld.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ty.moin.sfashionworld.dao.AdminDao;
import com.ty.moin.sfashionworld.dto.Admin;
import com.ty.moin.sfashionworld.dto.ResponseStructure;
import com.ty.moin.sfashionworld.excepion.EmailIdNotFoundException;
import com.ty.moin.sfashionworld.excepion.IdNotFoundException;
import com.ty.moin.sfashionworld.excepion.InvalidCredentialException;
import com.ty.moin.sfashionworld.excepion.PhoneNumberNotFoundException;
import com.ty.moin.sfashionworld.util.EmailSender;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private EmailSender emailSender;

	public ResponseEntity<ResponseStructure<Admin>> saveService(Admin admin) {

		ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();
		admin.setPassword(encoder.encode(admin.getPassword()));
		Admin admin2 = adminDao.saveAdmin(admin);
		emailsend(admin2);
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("SUCCESSFULLY CREATED");
		responseStructure.setData(admin2);
		return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.CREATED);
	}

	public void emailsend(Admin admin) {
		emailSender.sendSimpleEmail(admin.getEmail(), "Your Profile created And Your Admin Id is " + admin.getId(),
				"Profile Created Successfully - " + admin.getUserName());
	}

	public ResponseEntity<ResponseStructure<List<Admin>>> getAllAdmins() {
		ResponseStructure<List<Admin>> responseStructure = new ResponseStructure<List<Admin>>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("SUCCESS");
		responseStructure.setData(adminDao.getAllAdmin());
		return new ResponseEntity<ResponseStructure<List<Admin>>>(responseStructure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(Admin admin) {
		Admin admin1 = adminDao.findAdminById(admin.getId());
		ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();

		if (admin1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(adminDao.saveAdmin(admin));
			return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.OK);

		} else {
			throw new IdNotFoundException("ID " + admin.getId() + ", NOT FOUND");
		}

	}

	public ResponseEntity<ResponseStructure<Admin>> findAdminById(int id) {

		Admin admin = adminDao.findAdminById(id);
		ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();
		if (admin != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(admin);
			return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.OK);

		} else {
			throw new IdNotFoundException("ID " + id + ", NOT FOUND");
		}

	}

	public ResponseEntity<ResponseStructure<Admin>> deleteAdminById(int id) {

		ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();
		if (adminDao.deleteAdminById(id)) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.OK);

		} else {
			throw new IdNotFoundException("ID " + id + ", NOT FOUND");
		}
	}

	public ResponseEntity<ResponseStructure<Admin>> findAdminByEmail(String email) {

		Admin admin = adminDao.findAdminByEmail(email);
		ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();
		if (admin != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(admin);
			return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.OK);

		} else {
			throw new EmailIdNotFoundException("EMAIL : " + email + ", NOT FOUND");
		}

	}

	public ResponseEntity<ResponseStructure<Admin>> findAdminByPhone(long phone) {

		Admin admin = adminDao.findAdminByPhone(phone);
		ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();
		if (admin != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(admin);
			return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.OK);

		} else {
			throw new PhoneNumberNotFoundException("PHONE NO: " + phone + " FOUND ");
		}

	}

	public ResponseEntity<ResponseStructure<Admin>> adminValidateByEmail(String email, String password) {

		ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();

		Admin admin = adminDao.findAdminByEmail(email);

		if (admin != null) {

			if (admin.getPassword().equals(password)) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("SUCCESS");
				responseStructure.setData(admin);
				return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.OK);
			} else {
				throw new InvalidCredentialException("INVALID PASSWORD");
			}
		} else {
			throw new EmailIdNotFoundException("Mail Not Found " + email);
		}
	}

	public ResponseEntity<ResponseStructure<Admin>> adminValidateByPhone(long phone, String password) {
		ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();
		Admin admin = adminDao.findAdminByPhone(phone);
		if (admin != null) {
			if (password.equals(admin.getPassword())) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("SUCCESS");
				responseStructure.setData(admin);
				return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.OK);
			} else {
				throw new InvalidCredentialException("INVALID PASSWORD");

			}
		} else {
			throw new PhoneNumberNotFoundException("PHONE NO: " + phone + " FOUND ");
		}
	}
}
