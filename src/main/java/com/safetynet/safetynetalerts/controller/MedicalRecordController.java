package com.safetynet.safetynetalerts.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.exceptions.DuplicatedMedicalRecordException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.RequestBodyException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;

/**
 * <p>Controller class which exposes endpoints for CRUD operations 
 * related to the medical records.</>
 * @author newbie
 * @see MedicalRecordService
 */
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

	private Logger log =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MedicalRecordService medicalRecordService;
		
	@PostMapping("")
	public ResponseEntity<MedicalRecord> insertMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws MedicalRecordsDataNotFoundException, DuplicatedMedicalRecordException, RequestBodyException{
		
		checkForInvalidBody(medicalRecord);
		checkForAlphabeticalParameters(medicalRecord);
		
		log.info(System.lineSeparator()+"User has entered \"/medicalRecord\" endpoint((POST request) to insert a new person: "+medicalRecord.getFirstName()+" "+medicalRecord.getLastName()+" ."
				+System.lineSeparator()+ "Request has returned :" +medicalRecord.toString());
		
		return ResponseEntity.ok(medicalRecordService.insertMedicalRecord(medicalRecord));
	}
	
	@PutMapping("")
	public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException, DuplicatedMedicalRecordException, RequestBodyException{
		
		checkForInvalidBody(medicalRecord);
		checkForAlphabeticalParameters(medicalRecord);
		
		log.info(System.lineSeparator()+"User has entered \"/medicalRecord\" endpoint (PUT request) to update "+medicalRecord.getFirstName()+" "+medicalRecord.getLastName()+" medical record."
				+System.lineSeparator()+ "Request has returned :" +medicalRecord.toString());
		
		return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(medicalRecord));	
	}
	
	@DeleteMapping("")
	public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException, DuplicatedMedicalRecordException, RequestBodyException{
		
		checkForInvalidBody(medicalRecord);
		checkForAlphabeticalParameters(medicalRecord);
		
		log.info(System.lineSeparator()+"User has entered \"/medicalRecord\" endpoint (DELETE request) to delete "+medicalRecord.getFirstName()+" "+medicalRecord.getLastName()+" medical record."
				+System.lineSeparator()+ "Request has returned :" +medicalRecord.toString());
		
		return ResponseEntity.ok(medicalRecordService.deleteMedicalRecord(medicalRecord));
	}
	
	private void checkForInvalidBody(MedicalRecord medicalRecord) throws RequestBodyException {
		if(medicalRecord.getFirstName().isBlank()||medicalRecord.getLastName().isBlank())
			throw new RequestBodyException("Medical record's first name or last name can't be empty");
	}
	
	private void checkForAlphabeticalParameters(MedicalRecord medicalRecord) throws RequestBodyException {
		if(!StringUtils.isAlphanumeric(medicalRecord.getFirstName())||!StringUtils.isAlphaSpace(medicalRecord.getLastName()))
			throw new RequestBodyException("Request parameter has to be alphabetical");
	}
	
	
}
