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
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonInfo;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonsCoveredByFireStation;
import com.safetynet.safetynetalerts.service.CommunityService;

/**
 * Controller class which exposes endpoints dealing with the people informations
 * such as emails or specific person infos.
 * @author newbie
 * @see CommunityService
 *
 */
@RestController
public class CommunityController {

	private Logger log =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommunityService communityService ;
	
	/**
	 * Call to the {@link CommunityService#getCommunityEmails(String)} method,
	 * retrieving the emails' list of all the specified city's inhabitants.
	 * @param String city's name.
	 * @return a {@link List} of emails as String.
	 * @throws PersonsDataNotFoundException 
	 * @see CommunityService
	 */
	@GetMapping("/communityEmail")
	public ResponseEntity<List<String>> communityEmails (@RequestParam("city")String city) throws PersonsDataNotFoundException{
		
		List<String> communityEmails = communityService.getCommunityEmails(city);
		
		log.info(System.lineSeparator()+"User has entered \"/communityEmail\" endpoint (GET request) to get "+city+" inhabitants emails list."
				+System.lineSeparator()+ "Request has returned :" +communityEmails);
		
		return ResponseEntity.ok(communityEmails);	
	}

	/**
	 * Call to the {@link CommunityService#getPersonInfo(String)} method,
	 * retrieving a specific person's informations.
	 * @param Person's first name, the "firstName" attribute's value.
	 * @param Person's last name, the "lastName" attribute's value.
	 * @return a {@link CommunityPersonInfo} custom object 
	 * containing the required person informations. 
	 * @throws MedicalRecordNotFoundException
	 * @throws MedicalRecordsDataNotFoundException 
	 * @throws PersonsDataNotFoundException 
	 * @throws PersonNotFoundException 
	 * @see CommunityPersonInfo
	 * @see Person
	 * @see MedicalRecord
	 */
	@GetMapping("/personInfo")
	public ResponseEntity<CommunityPersonInfo> personInfo 
	(@RequestParam("firstName")String firstName, @RequestParam("lastName")String lastName) throws PersonNotFoundException, PersonsDataNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException{		
		
		CommunityPersonInfo communityPersonInfo = communityService.getPersonInfo(firstName + lastName);
		
		log.info(System.lineSeparator()+"User has entered \"/personInfo\" endpoint (GET request) to get "+firstName +" "+ lastName+" infos."
				+System.lineSeparator()+ "Request has returned :" +communityPersonInfo);
		
		return ResponseEntity.ok(communityPersonInfo);
	}
	
	/**
	 * Call to the {@link CommunityService#getPersonsCoveredByFireStation(String)}
	 * retrieving a list of the persons covered by the specified fire station,
	 * identified by its ("id") number.
	 * @param a LinkedFireStation stationNumber as int 
	 * @return a {@link CommunityPersonsCoveredByFireStation} custom object
	 * containing all persons' informations.
	 * @throws MedicalRecordNotFoundException 
	 * @throws MedicalRecordsDataNotFoundException 
	 * @throws PersonsDataNotFoundException 
	 * @throws LinkedFireStationsDataNotFoundException 
	 * @throws LinkedFireStationNotFoundException 
	 * @see CommunityPersonsCoveredByFireStation
	 */
	@GetMapping("/firestation")
	public ResponseEntity<CommunityPersonsCoveredByFireStation> personsCoveredByFireStation
	(@RequestParam("stationNumber")String stationNumber) throws LinkedFireStationsDataNotFoundException, PersonsDataNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException, LinkedFireStationNotFoundException{
		
		CommunityPersonsCoveredByFireStation personsCovered = communityService.getPersonsCoveredByFireStation(stationNumber);
		
		log.info(System.lineSeparator()+"User has entered \"/firestation\" endpoint (GET request) to get persons infos covered by fire station number "+stationNumber+"."
				+System.lineSeparator()+ "Request has returned :" +personsCovered.toString());
		
		return ResponseEntity.ok(personsCovered);	
	}
	
}
