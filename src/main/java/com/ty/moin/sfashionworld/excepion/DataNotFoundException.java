package com.ty.moin.sfashionworld.excepion;

public class DataNotFoundException extends RuntimeException {

	private String message = "Data Not Present";

	public DataNotFoundException(String message) {
		this.message = message;
	}

	public DataNotFoundException() {

	}

	@Override
	public String toString() {
		return message;
	}
}
