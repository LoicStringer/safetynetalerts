package com.safetynet.safetynetalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.MULTIPLE_CHOICES)
public class DuplicatedPersonException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatedPersonException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedPersonException(String message) {
		super(message);
	}

	
	
}
