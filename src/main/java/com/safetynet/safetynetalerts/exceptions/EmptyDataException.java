package com.safetynet.safetynetalerts.exceptions;

public class EmptyDataException extends Exception{

	private static final long serialVersionUID = 1L;

	public EmptyDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyDataException(String message) {
		super(message);
	}
	
	

}
