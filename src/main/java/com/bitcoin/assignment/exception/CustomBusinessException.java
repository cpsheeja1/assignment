package com.bitcoin.assignment.exception;

public class CustomBusinessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomBusinessException(String message) {
		super(message);
	}

}
