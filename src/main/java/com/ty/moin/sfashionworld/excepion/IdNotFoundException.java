package com.ty.moin.sfashionworld.excepion;

public class IdNotFoundException extends RuntimeException {

	private String message = "Id Not Found";

	public IdNotFoundException(String message) {
		this.message = message;
	}

	public IdNotFoundException() {
	}

	@Override
	public String getMessage() {
		return message;
	}
}
