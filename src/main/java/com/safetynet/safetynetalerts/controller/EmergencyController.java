package com.safetynet.safetynetalerts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.exceptions.LinkedFireStationNotFoundException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.responseentity.EmergencyChildAlert;
import com.safetynet.safetynetalerts.responseentity.EmergencyFireAddressInfos;
import com.safetynet.safetynetalerts.responseentity.EmergencyFloodInfos;
import com.safetynet.safetynetalerts.service.EmergencyService;

/**
 * Controller class which exposes endpoints dealing with
 * @author newbie
 *
 */
@RestController
public class EmergencyController {

	private Logger log =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EmergencyService emergencyService;
	
	@GetMapping("/childAlert")
	public ResponseEntity<EmergencyChildAlert> getChildren
	(@RequestParam ("address") String address) throws PersonsDataNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException{
		
		EmergencyChildAlert anyChildThere = emergencyService.getChildrenThere(address);
		
		log.info(System.lineSeparator()+"User has entered \"/childAlert\" endpoint (GET request) to get informed about children living at "+address+"."
				+System.lineSeparator()+ "Request has returned :" +anyChildThere);
		
		return ResponseEntity.ok(anyChildThere);
	}
	
	@GetMapping("/phoneAlert")
	public List<String> getPhoneNumbers
	(@RequestParam("firestation") String stationNumber) throws PersonsDataNotFoundException, LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException{
		
		List<String> phoneNumbers = emergencyService.getCoveredPersonsPhoneNumbers(stationNumber);
		
		log.info(System.lineSeparator()+"User has entered \"/phoneAlert\" endpoint (GET request) to get persons phone numbers list covered by fire station number "+stationNumber+"."
				+System.lineSeparator()+ "Request has returned :" +phoneNumbers);
		
		return phoneNumbers;
	}
	
	@GetMapping("/fire")
	public  ResponseEntity<EmergencyFireAddressInfos> getInhabitants
	(@RequestParam("address") String address) throws LinkedFireStationsDataNotFoundException, PersonsDataNotFoundException, MedicalRecordsDataNotFoundException, LinkedFireStationNotFoundException{
		
		EmergencyFireAddressInfos inhabitantsThere = emergencyService.getPersonsThereInfos(address);
		
		log.info(System.lineSeparator()+"User has entered \"/fire\" endpoint (GET request) to get persons infos living at "+address+"."
				+System.lineSeparator()+ "Request has returned :" +inhabitantsThere);
		
		return ResponseEntity.ok(inhabitantsThere);
	}
	
	@GetMapping("/flood/stations")
	public ResponseEntity<EmergencyFloodInfos> getHomesInfo
	(@RequestParam("stations")  List<String> stationNumbers) throws MedicalRecordsDataNotFoundException, PersonsDataNotFoundException, LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException{
		
		EmergencyFloodInfos emergencyFloodInfos = emergencyService.getCoveredHomesInfos(stationNumbers);
		
		log.info(System.lineSeparator()+"User has entered \"/flood/stations\" endpoint (GET request) to get homes infos covered by fire station numbers list "+stationNumbers+"."
				+System.lineSeparator()+ "Request has returned :" +emergencyFloodInfos);
		
		return ResponseEntity.ok(emergencyFloodInfos);
	}
	
}
