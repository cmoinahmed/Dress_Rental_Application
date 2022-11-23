package com.ty.moin.sfashionworld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ty.moin.sfashionworld.dto.JwtRequest;
import com.ty.moin.sfashionworld.dto.JwtResponse;
import com.ty.moin.sfashionworld.dto.ResponseStructure;
import com.ty.moin.sfashionworld.util.JwtTokenUtil;

@Service
public class AuthenticateService {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService detailsService;

	public ResponseEntity<ResponseStructure<JwtResponse>> authenticate(@RequestBody JwtRequest jwtRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Username or Password", e);
		}
		final UserDetails details = detailsService.loadUserByUsername(jwtRequest.getUserName());

		final String jwt = jwtTokenUtil.generateToken(details);

		ResponseStructure<JwtResponse> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("SUCCESS");
		responseStructure.setData(new JwtResponse(jwt));

		return ResponseEntity.ok(responseStructure);
	}
}
