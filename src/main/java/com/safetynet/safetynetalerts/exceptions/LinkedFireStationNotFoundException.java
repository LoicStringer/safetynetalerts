package com.safetynet.safetynetalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LinkedFireStationNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public LinkedFireStationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public LinkedFireStationNotFoundException(String message) {
		super(message);
	}

	
}
