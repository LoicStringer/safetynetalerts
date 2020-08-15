package com.safetynet.safetynetalerts.exceptions;

public class DuplicatedItemException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatedItemException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedItemException(String message) {
		super(message);
	}

	
	
}
