package com.flightbooking.authservice.exception;

public class InvalidInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3459453812912669195L;

	public InvalidInputException(String message) {
		super(message);
	}
}
