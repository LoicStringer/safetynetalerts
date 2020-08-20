package com.safetynet.safetynetalerts.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedLinkedFireStationException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedMedicalRecordException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedPersonException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationNotFoundException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.responseentity.ExceptionResponse;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(DataImportFailedException.class)
	public ResponseEntity<ExceptionResponse> handleDataImportFailedException(DataImportFailedException ex){
		
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		
		log.error(exceptionLogMessageBuilder(exceptionResponse));
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	@ExceptionHandler(DuplicatedPersonException.class)
	public ResponseEntity<ExceptionResponse> handleDuplicatedPersonException(DuplicatedPersonException ex){
		
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		
		log.error(exceptionLogMessageBuilder(exceptionResponse));
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	@ExceptionHandler(DuplicatedMedicalRecordException.class)
	public ResponseEntity<ExceptionResponse> handleDuplicatedMedicalRecordException(DuplicatedMedicalRecordException ex){
		
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		
		log.error(exceptionLogMessageBuilder(exceptionResponse));
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	@ExceptionHandler(DuplicatedLinkedFireStationException.class)
	public ResponseEntity<ExceptionResponse> handleDuplicatedLinkedFireStationException(DuplicatedLinkedFireStationException ex){
		
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		
		log.error(exceptionLogMessageBuilder(exceptionResponse));
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	@ExceptionHandler(PersonsDataNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handlePersonsDataNotFoundException(PersonsDataNotFoundException ex){
		
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		
		log.error(exceptionLogMessageBuilder(exceptionResponse));
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	@ExceptionHandler(MedicalRecordsDataNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleMedicalRecordsDataNotFoundException(MedicalRecordsDataNotFoundException ex){
		
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		
		log.error(exceptionLogMessageBuilder(exceptionResponse));
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	@ExceptionHandler(LinkedFireStationsDataNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleLinkedFireStationsDataNotFoundException(LinkedFireStationsDataNotFoundException ex){
		
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		
		log.error(exceptionLogMessageBuilder(exceptionResponse));
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	@ExceptionHandler(PersonNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handlePersonNotFoundException(PersonNotFoundException ex){
		
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		
		log.error(exceptionLogMessageBuilder(exceptionResponse));
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	@ExceptionHandler(MedicalRecordNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleMedicalRecordNotFoundException(MedicalRecordNotFoundException ex){
		
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		
		log.error(exceptionLogMessageBuilder(exceptionResponse));
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	@ExceptionHandler(LinkedFireStationNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleLinkedFireStationNotFoundException(LinkedFireStationNotFoundException ex){
		
		ExceptionResponse exceptionResponse = exceptionResponseBuild(ex);
		
		log.error(exceptionLogMessageBuilder(exceptionResponse));
		
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, getHttpStatusFromException(ex));
	}
	
	private ExceptionResponse exceptionResponseBuild(Exception ex) {
		
		String statusCode = getStatusCodeFromException(ex);
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),statusCode, ex.getClass().getSimpleName(), ex.getMessage());
		
		return exceptionResponse;
	}
	
	private HttpStatus getHttpStatusFromException(Exception ex) {

		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		HttpStatus status = responseStatus.value();
		
		return status ;
	}
	
	private String getStatusCodeFromException(Exception ex) {
		
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		HttpStatus status = responseStatus.code();
		
		return status.toString();
	}
	
	private String exceptionLogMessageBuilder(ExceptionResponse exceptionResponse) {
	
		String logMessage = System.lineSeparator()+exceptionResponse.getMessage()+System.lineSeparator()
		+"Leading to : "+exceptionResponse.getStatusCode()+System.lineSeparator()+"Caused by : "+exceptionResponse.getCausedBy();
		
		return logMessage;
	}
}
