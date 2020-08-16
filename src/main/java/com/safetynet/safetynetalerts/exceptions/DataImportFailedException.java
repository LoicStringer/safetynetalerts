package com.safetynet.safetynetalerts.exceptions;

public class DataImportFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataImportFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataImportFailedException(String message) {
		super(message);
	}

	
	
	
}
