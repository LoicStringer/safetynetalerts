package com.safetynet.safetynetalerts.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.safetynet.safetynetalerts.exceptions.RequestParameterException;
import com.safetynet.safetynetalerts.responseentity.EmergencyChildAlert;
import com.safetynet.safetynetalerts.responseentity.EmergencyFireAddressInfos;
import com.safetynet.safetynetalerts.responseentity.EmergencyFloodInfos;
import com.safetynet.safetynetalerts.service.EmergencyService;

/**
 * <p>Controller class which exposes endpoints that may be used 
 * to retrieve important informations in situations considered as an emergency.</p>
 * 
 * @author newbie
 *@see EmergencyService
 */
@RestController
public class EmergencyController {

	private Logger log =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EmergencyService emergencyService;
	
	/**
	 * <p>Call to the {@link EmergencyService#getChildrenThere(String)} method,
	 * retrieving the inhabtitants list of the specified address,
	 * precising if they are children or adults.</p>
	 * 
	 * @return a {@link EmergencyChildAlert} custom object.
	 * @throws PersonsDataNotFoundException
	 * @throws MedicalRecordsDataNotFoundException
	 *  @throws MedicalRecordNotFoundException
	 * @throws RequestParameterException
	 * @see EmergencyService
	 */
	@GetMapping("/childAlert")
	public ResponseEntity<EmergencyChildAlert> getChildren
	(@RequestParam("address") String address) throws PersonsDataNotFoundException, MedicalRecordsDataNotFoundException,
			MedicalRecordNotFoundException, RequestParameterException{
		
		checkForBlankParameters(address);
		
		EmergencyChildAlert anyChildThere = emergencyService.getChildrenThere(address);
		
		log.info(System.lineSeparator()+"User has entered \"/childAlert\" endpoint (GET request) to get informed about children living at "+address+"."
				+System.lineSeparator()+ "Request has returned :" +anyChildThere);
		
		return ResponseEntity.ok(anyChildThere);
	}
	
	/**
	 * <p>Call to the {@link EmergencyService#getCoveredPersonsPhoneNumbers(String)} method,
	 * retrieving the persons phone numbers list covered by the specified fire station .</p>
	 * 
	 * @return a String {@link List} of phone numbers.
	 * @throws PersonsDataNotFoundException
	 * @throws LinkedFireStationsDataNotFoundException
	 *  @throws LinkedFireStationNotFoundException
	 * @throws RequestParameterException
	 * @see EmergencyService
	 */
	@GetMapping("/phoneAlert")
	public List<String> getPhoneNumbers
	(@RequestParam("firestation") String stationNumber) throws PersonsDataNotFoundException, LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException, RequestParameterException{
		
		checkForBlankParameters(stationNumber);
		checkForNumericParameter(stationNumber);
		
		List<String> phoneNumbers = emergencyService.getCoveredPersonsPhoneNumbers(stationNumber);
		
		log.info(System.lineSeparator()+"User has entered \"/phoneAlert\" endpoint (GET request) to get persons phone numbers list covered by fire station number "+stationNumber+"."
				+System.lineSeparator()+ "Request has returned :" +phoneNumbers);
		
		return phoneNumbers;
	}
	
	/**
	 * <p>Call to the {@link EmergencyService#getPersonsThereInfos(String)} method,
	 * retrieving the inhabtitants list of the specified address,
	 * and their relating informations.</p>
	 * 
	 * @return a {@link EmergencyFireAddressInfos} custom object.
	 * @throws LinkedFireStationsDataNotFoundException
	 * @throws PersonsDataNotFoundException
	 * @throws MedicalRecordsDataNotFoundException
	 * @throws LinkedFireStationNotFoundException
	 * @throws RequestParameterException
	 * @see EmergencyService
	 */
	@GetMapping("/fire")
	public  ResponseEntity<EmergencyFireAddressInfos> getInhabitants
	(@RequestParam("address") String address) throws LinkedFireStationsDataNotFoundException, PersonsDataNotFoundException, MedicalRecordsDataNotFoundException, LinkedFireStationNotFoundException, RequestParameterException{
		
		checkForBlankParameters(address);
		
		EmergencyFireAddressInfos inhabitantsThere = emergencyService.getPersonsThereInfos(address);
		
		log.info(System.lineSeparator()+"User has entered \"/fire\" endpoint (GET request) to get persons infos living at "+address+"."
				+System.lineSeparator()+ "Request has returned :" +inhabitantsThere);
		
		return ResponseEntity.ok(inhabitantsThere);
	}
	
	/**
	 * <p>Call to the {@link EmergencyService#getCoveredHomesInfos(List String)} method,
	 * retrieving the whole homes list covered by the specified fire stations list,
	 * their inhabitants and their informations, in ascending order and addresses.</p>
	 * 
	 * @return a {@link EmergencyFloodInfos} custom object.
	 * @throws PersonsDataNotFoundException
	 * @throws MedicalRecordsDataNotFoundException
	 * @throws LinkedFireStationsDataNotFoundException
	 * @throws LinkedFireStationNotFoundException
	 * @throws RequestParameterException
	 * @see EmergencyService
	 */
	@GetMapping("/flood/stations")
	public ResponseEntity<EmergencyFloodInfos> getHomesInfo
	(@RequestParam("stations")  List<String> stationNumbers) throws MedicalRecordsDataNotFoundException, PersonsDataNotFoundException, LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException, RequestParameterException{
		
		if(stationNumbers.isEmpty())
			throw new RequestParameterException("Request parameter can't be blank");
		
		EmergencyFloodInfos emergencyFloodInfos = emergencyService.getCoveredHomesInfos(stationNumbers);
		
		log.info(System.lineSeparator()+"User has entered \"/flood/stations\" endpoint (GET request) to get homes infos covered by fire station numbers list "+stationNumbers+"."
				+System.lineSeparator()+ "Request has returned :" +emergencyFloodInfos);
		
		return ResponseEntity.ok(emergencyFloodInfos);
	}
	
	private void checkForBlankParameters(String parameter) throws RequestParameterException {
		if(parameter.isBlank())
			throw new RequestParameterException("Request parameter can't be blank");
	}
	
	private void checkForNumericParameter(String parameter) throws RequestParameterException {
		if(!StringUtils.isNumericSpace(parameter))
			throw new RequestParameterException("Request parameter has to be a number");
	}
}
