package com.safetynet.safetynetalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedLinkedFireStationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatedLinkedFireStationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedLinkedFireStationException(String message) {
		super(message);
	}

	
}
