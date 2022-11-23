package com.ty.moin.sfashionworld.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ty.moin.sfashionworld.dto.Admin;
import com.ty.moin.sfashionworld.repository.AdminRepository;

@Repository
public class AdminDao {
	
	@Autowired
	private AdminRepository adminRepository;
	
	public Admin saveAdmin(Admin admin) {
		return adminRepository.save(admin);
	}

	public Admin findAdminById(int id) {
		Optional<Admin> optional = adminRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public boolean deleteAdminById(int id) {
		Optional<Admin> optional = adminRepository.findById(id);
		if (optional.isPresent()) {
			adminRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public List<Admin> getAllAdmin() {
		return adminRepository.findAll();
	}

	public Admin findAdminByEmail(String email) {
		Optional<Admin> optional = adminRepository.findByEmail(email);
		if (optional.isPresent()) {
			
			return optional.get();
		} else {
			return null;
		}
	}

	public Admin findAdminByPhone(long phone) {
		Optional<Admin> optional = adminRepository.findByPhone(phone);
		if (optional.isPresent()) {
			
			return optional.get();
		} else {
			return null;
		}
		
	}

}
