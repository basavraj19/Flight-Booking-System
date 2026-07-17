package com.flightbooking.authservice.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1463744012242072781L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
