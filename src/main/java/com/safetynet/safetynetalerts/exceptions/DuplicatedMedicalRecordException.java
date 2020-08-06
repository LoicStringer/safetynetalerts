package com.safetynet.safetynetalerts.exceptions;

public class DuplicatedMedicalRecordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatedMedicalRecordException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedMedicalRecordException(String message) {
		super(message);
	}

	
}
