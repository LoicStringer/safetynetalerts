package com.safetynet.safetynetalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.MULTIPLE_CHOICES)
public class DuplicatedMedicalRecordException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicatedMedicalRecordException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedMedicalRecordException(String message) {
		super(message);
	}

	
}
