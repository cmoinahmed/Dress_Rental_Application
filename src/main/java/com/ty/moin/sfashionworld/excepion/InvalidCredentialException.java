package com.ty.moin.sfashionworld.excepion;

public class InvalidCredentialException extends RuntimeException {

	private String message="Invalid Password";

	public InvalidCredentialException(String message) {
		this.message = message;
	}
	
	public InvalidCredentialException() {
		
	}
	
	@Override
	public String toString() {
		return message;
	}
}
