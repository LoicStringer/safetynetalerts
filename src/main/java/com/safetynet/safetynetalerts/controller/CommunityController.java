package com.safetynet.safetynetalerts.controller;

import java.util.ArrayList;
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
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.RequestBodyException;
import com.safetynet.safetynetalerts.exceptions.RequestParameterException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonInfo;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonsCoveredByFireStation;
import com.safetynet.safetynetalerts.service.CommunityService;

/**
 * <p>Controller class which exposes endpoints dealing with the people informations
 * such as emails or specific person infos.</p>
 * 
 * @author newbie
 * @see CommunityService
 *
 */
@RestController
public class CommunityController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommunityService communityService;

	/**
	 * <p>Call to the {@link CommunityService#getCommunityEmails(String)} method,
	 * retrieving the emails' list of all the specified city's inhabitants.</p>
	 * 
	 * @return a String {@link ArrayList} of emails.
	 * @throws PersonsDataNotFoundException
	 * @throws RequestBodyException
	 * @see CommunityService
	 */
	@GetMapping("/communityEmail")
	public ResponseEntity<List<String>> communityEmails(@RequestParam("city") String city)
			throws PersonsDataNotFoundException, RequestParameterException {

		checkForBlankParameters(city);
		checkForAlphabeticalParameters(city);

		List<String> communityEmails = communityService.getCommunityEmails(city);

		log.info(System.lineSeparator() + "User has entered \"/communityEmail\" endpoint (GET request) to get " + city
				+ " inhabitants emails list." + System.lineSeparator() + "Request has returned :" + communityEmails);

		return ResponseEntity.ok(communityEmails);
	}

	/**
	 * <p>Call to the {@link CommunityService#getPersonInfo(String)} method, retrieving
	 * a specific person's informations.If several persons wear the same name, they are
	 * all retrieved.</p>
	 *
	 * @return a {@link CommunityPersonInfo} custom object 
	 * @throws MedicalRecordNotFoundException
	 * @throws MedicalRecordsDataNotFoundException
	 * @throws PersonsDataNotFoundException
	 * @throws PersonNotFoundException
	 * @throws RequestBodyException
	 * @see CommunityPersonInfo
	 * @see Person
	 * @see MedicalRecord
	 */
	@GetMapping("/personInfo")
	public ResponseEntity<CommunityPersonInfo> personInfo(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) throws PersonNotFoundException, PersonsDataNotFoundException,
			MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException, RequestParameterException {

		checkForBlankParameters(firstName);
		checkForBlankParameters(lastName);
		checkForAlphabeticalParameters(firstName);
		checkForAlphabeticalParameters(lastName);

		CommunityPersonInfo communityPersonInfo = communityService.getPersonInfo(firstName + lastName);

		log.info(System.lineSeparator() + "User has entered \"/personInfo\" endpoint (GET request) to get " + firstName
				+ " " + lastName + " infos." + System.lineSeparator() + "Request has returned :" + communityPersonInfo);

		return ResponseEntity.ok(communityPersonInfo);
	}

	/**
	 * <p>Call to the {@link CommunityService#getPersonsCoveredByFireStation(String)}
	 * retrieving the persons list covered by the specified fire station,
	 * identified by its ("id") number.</p>
	 * 
	 * @return a {@link CommunityPersonsCoveredByFireStation} custom object
	 * @throws MedicalRecordNotFoundException
	 * @throws MedicalRecordsDataNotFoundException
	 * @throws PersonsDataNotFoundException
	 * @throws LinkedFireStationsDataNotFoundException
	 * @throws LinkedFireStationNotFoundException
	 * @throws RequestBodyException
	 * @see CommunityPersonsCoveredByFireStation
	 */
	@GetMapping("/firestation")
	public ResponseEntity<CommunityPersonsCoveredByFireStation> personsCoveredByFireStation(
			@RequestParam("stationNumber") String stationNumber) throws LinkedFireStationsDataNotFoundException,
			PersonsDataNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException,
			LinkedFireStationNotFoundException, RequestParameterException {

		checkForBlankParameters(stationNumber);
		checkForNumericParameter(stationNumber);

		CommunityPersonsCoveredByFireStation personsCovered = communityService
				.getPersonsCoveredByFireStation(stationNumber);

		log.info(System.lineSeparator()
				+ "User has entered \"/firestation\" endpoint (GET request) to get persons infos covered by fire station number "
				+ stationNumber + "." + System.lineSeparator() + "Request has returned :" + personsCovered.toString());

		return ResponseEntity.ok(personsCovered);
	}

	private void checkForBlankParameters(String parameter) throws RequestParameterException {
		if (parameter.isBlank())
			throw new RequestParameterException("Request parameter can't be blank");
	}

	private void checkForAlphabeticalParameters(String parameter) throws RequestParameterException {
		if(!StringUtils.isAlphaSpace(parameter))
			throw new RequestParameterException("Request parameter has to be alphabetical");
	}
	
	private void checkForNumericParameter(String parameter) throws RequestParameterException {
		if(!StringUtils.isNumericSpace(parameter))
			throw new RequestParameterException("Request parameter has to be a number");
	}
}
