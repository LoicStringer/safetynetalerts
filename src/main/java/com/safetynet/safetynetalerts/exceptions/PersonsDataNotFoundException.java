package com.safetynet.safetynetalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonsDataNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonsDataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonsDataNotFoundException(String message) {
		super(message);
	}
	
	

}
