package com.ty.moin.sfashionworld.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ty.moin.sfashionworld.dto.Merchant;
import com.ty.moin.sfashionworld.repository.MerchantRepository;

@Repository
public class MerchantDao {

	@Autowired
	private MerchantRepository merchantRepository;

	public Merchant saveMerchant(Merchant merchant) {
		return merchantRepository.save(merchant);
	}

	public Merchant getMerchantByPhone(long phone) {
		Optional<Merchant> optional = merchantRepository.findByPhone(phone);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public Merchant getMerchantByEmail(String email) {
		Optional<Merchant> optional = merchantRepository.findByEmail(email);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public Merchant getMerchantById(int id) {
		Optional<Merchant> optional = merchantRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public void deleteMerchantById(Merchant merchant) {
		merchantRepository.delete(merchant);
	}

	public List<Merchant> getAllMerchant() {
		return merchantRepository.findAll();
	}

	public Merchant getMerchantByOtp(int otp) {
		Optional<Merchant> optional = merchantRepository.findByOtp(otp);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}
}
