package com.bitcoin.assignment.exception;

public class CustomBusinessValidationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomBusinessValidationException(String message) {
		super(message);
	}

}
