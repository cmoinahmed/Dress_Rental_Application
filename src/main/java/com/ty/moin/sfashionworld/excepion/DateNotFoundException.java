package com.ty.moin.sfashionworld.excepion;

public class DateNotFoundException extends RuntimeException{

	private String message="Date Not Found";

	public DateNotFoundException(String message) {
		this.message = message;
	}
	
	public DateNotFoundException() {
		
	}
	
	@Override
	public String toString() {
		return message;
	}
}
