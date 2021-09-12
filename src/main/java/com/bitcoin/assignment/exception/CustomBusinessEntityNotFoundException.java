package com.bitcoin.assignment.exception;

public class CustomBusinessEntityNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public CustomBusinessEntityNotFoundException(String message) {
        super(message);
    }

}
