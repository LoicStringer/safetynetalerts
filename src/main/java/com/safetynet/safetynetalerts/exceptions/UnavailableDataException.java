package com.safetynet.safetynetalerts.exceptions;

public class UnavailableDataException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnavailableDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnavailableDataException(String message) {
		super(message);
	}
	
	

}
