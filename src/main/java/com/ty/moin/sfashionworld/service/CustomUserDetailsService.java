package com.ty.moin.sfashionworld.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ty.moin.sfashionworld.dto.User;
import com.ty.moin.sfashionworld.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = repository.findByUserName(username);
		CustomUserDetails customUserDetails = new CustomUserDetails();

		if (optional.isPresent()) {
			User user = optional.get();
			System.out.println(user.getRole());
			customUserDetails.setUser(user);
		} else {
			throw new UsernameNotFoundException("Not Found - " + username);
		}

		return customUserDetails;
	}

}
