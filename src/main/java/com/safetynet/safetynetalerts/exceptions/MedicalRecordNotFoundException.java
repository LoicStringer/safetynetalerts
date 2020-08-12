package com.safetynet.safetynetalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicalRecordNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MedicalRecordNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MedicalRecordNotFoundException(String message) {
		super(message);
	}

	
	
}
