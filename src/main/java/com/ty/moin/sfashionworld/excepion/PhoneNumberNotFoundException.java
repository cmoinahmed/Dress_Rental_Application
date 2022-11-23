package com.ty.moin.sfashionworld.excepion;

public class PhoneNumberNotFoundException extends RuntimeException {

	private String message = "Phone Number Does Not Exist";

	public PhoneNumberNotFoundException(String message) {
		this.message = message;
	}

	public PhoneNumberNotFoundException() {
	}

	@Override
	public String getMessage() {
		return message;
	}
}
