package com.ty.moin.sfashionworld.excepion;

public class EmailIdNotFoundException extends RuntimeException {

	private String message="Invalid Email Id";

	public EmailIdNotFoundException(String message) {
		this.message = message;
	}
	
	public EmailIdNotFoundException() {
		
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
