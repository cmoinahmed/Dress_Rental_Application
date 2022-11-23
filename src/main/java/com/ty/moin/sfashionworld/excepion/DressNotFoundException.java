package com.ty.moin.sfashionworld.excepion;

public class DressNotFoundException extends RuntimeException{

	private String message="Dress Not Found";

	public DressNotFoundException(String message) {
		this.message = message;
	}
	
	public DressNotFoundException() {
		
	}
	
	@Override
	public String toString() {
		return message;
	}
}
