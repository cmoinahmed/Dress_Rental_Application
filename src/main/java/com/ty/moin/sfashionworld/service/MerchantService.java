package com.ty.moin.sfashionworld.service;

import static com.ty.moin.sfashionworld.util.Aes.decrypt;
import static com.ty.moin.sfashionworld.util.Aes.encrypt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ty.moin.sfashionworld.dao.MerchantDao;
import com.ty.moin.sfashionworld.dto.Merchant;
import com.ty.moin.sfashionworld.dto.ResponseStructure;
import com.ty.moin.sfashionworld.excepion.DataNotFoundException;
import com.ty.moin.sfashionworld.excepion.EmailIdNotFoundException;
import com.ty.moin.sfashionworld.excepion.IdNotFoundException;
import com.ty.moin.sfashionworld.excepion.InvalidCredentialException;
import com.ty.moin.sfashionworld.excepion.PhoneNumberNotFoundException;
import com.ty.moin.sfashionworld.util.EmailSender;

@Service
public class MerchantService {

	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private PasswordEncoder encoder;

	public ResponseEntity<ResponseStructure<Merchant>> saveMerchant(Merchant merchant) {

		merchant.setPassword(encoder.encode(merchant.getPassword()));
		ResponseStructure<Merchant> responseStructure = new ResponseStructure<Merchant>();

		emailSender.sendSimpleEmail(merchant.getEmail(),
				"Greetings , " + merchant.getUserName() + "\n Your Profile Created In Moin's Fashion World",

				"Profile Created");

		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("SUCCESS");
		responseStructure.setData(merchantDao.saveMerchant(merchant));
		return new ResponseEntity<ResponseStructure<Merchant>>(responseStructure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Merchant>> getMerchantById(int id) {
		ResponseStructure<Merchant> responseStructure = new ResponseStructure<Merchant>();
		Merchant merchant = merchantDao.getMerchantById(id);
		if (merchant != null) {
			merchant.setPassword(merchant.getPassword());
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(merchant);
			return new ResponseEntity<ResponseStructure<Merchant>>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Merchant>> findMerchantByPhone(long phone) {
		ResponseStructure<Merchant> responseStructure = new ResponseStructure<Merchant>();
		Merchant merchant = merchantDao.getMerchantByPhone(phone);
		if (merchant != null) {
			merchant.setPassword(merchant.getPassword());
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(merchant);
			return new ResponseEntity<ResponseStructure<Merchant>>(responseStructure, HttpStatus.OK);
		} else {
			throw new PhoneNumberNotFoundException("Phone Number " + phone + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Merchant>> findMerchantByEmail(String email) {
		ResponseStructure<Merchant> responseStructure = new ResponseStructure<Merchant>();
		Merchant merchant = merchantDao.getMerchantByEmail(email);
		if (merchant != null) {
			merchant.setPassword(merchant.getPassword());
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(merchant);
			return new ResponseEntity<ResponseStructure<Merchant>>(responseStructure, HttpStatus.OK);
		} else {
			throw new EmailIdNotFoundException("Email " + email + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Merchant>> deleteMerchantById(int id) {
		ResponseStructure<Merchant> responseStructure = new ResponseStructure<>();
		Merchant merchant = merchantDao.getMerchantById(id);
		if (merchant != null) {
			merchantDao.deleteMerchantById(merchant);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Deleted Successfully");
			responseStructure.setData(merchant);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Merchant>>> getAllMerchant() {
		ResponseStructure<List<Merchant>> responseStructure = new ResponseStructure<>();
		List<Merchant> list = merchantDao.getAllMerchant();
		if (list.size() > 0) {
			for (Merchant merchant : list) {
				merchant.setPassword(merchant.getPassword());
			}
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(list);

			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> validateMerchantByEmail(String email, String password) {

		Merchant merchant = merchantDao.getMerchantByEmail(email);

		if (merchant != null) {
			if (password.equals(decrypt(merchant.getPassword()))) {
				double otp = Math.random();
				merchant.setOtp((int) (otp * 10000));
				merchantDao.saveMerchant(merchant);
				emailSender.sendSimpleEmail(email, "your otp is " + merchant.getOtp(), "Please enter the otp");
				ResponseStructure<String> responseStructure = new ResponseStructure<String>();
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("SUCCESS");
				responseStructure.setData("OTP sent to your Registered Email id");
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);

			} else {
				throw new InvalidCredentialException("Invalid Password");
			}
		} else {
			throw new EmailIdNotFoundException("Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Merchant>> validateOtp(int otp) {

		Merchant merchant = merchantDao.getMerchantByOtp(otp);
		if (otp == merchant.getOtp()) {
			ResponseStructure<Merchant> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("SUCCESS");
			responseStructure.setData(merchant);
			return new ResponseEntity<ResponseStructure<Merchant>>(responseStructure, HttpStatus.OK);
		} else {
			throw new InvalidCredentialException("Invalid Credential");
		}
	}

	public ResponseEntity<ResponseStructure<Merchant>> validateMerchantByPhone(long phone, String password) {

		Merchant merchant = merchantDao.getMerchantByPhone(phone);

		if (merchant != null) {
			if (encoder.matches(password, merchant.getPassword())) {
				ResponseStructure<Merchant> responseStructure = new ResponseStructure<>();
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("SUCCESS");
				responseStructure.setData(merchant);
				return new ResponseEntity<ResponseStructure<Merchant>>(responseStructure, HttpStatus.OK);
			} else {
				throw new InvalidCredentialException("Invalid Credential");
			}
		} else {
			throw new PhoneNumberNotFoundException("Phone : " + phone + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Merchant>> updateMerchant(Merchant merchant) {
		ResponseStructure<Merchant> responseStructure = new ResponseStructure<>();
		Merchant merchant1 = merchantDao.getMerchantById(merchant.getId());
		if (merchant1 != null) {
			merchant.setPassword(encrypt(merchant.getPassword()));
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Updated Successfully");
			responseStructure.setData(merchantDao.saveMerchant(merchant));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Id " + merchant.getId() + " Not Found");
		}
	}
}
