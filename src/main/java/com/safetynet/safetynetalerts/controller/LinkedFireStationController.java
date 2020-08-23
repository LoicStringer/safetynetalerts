package com.safetynet.safetynetalerts.controller;

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

import com.safetynet.safetynetalerts.exceptions.DuplicatedLinkedFireStationException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationNotFoundException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.RequestBodyException;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.service.LinkedFireStationService;

@RestController
@RequestMapping("/firestation")
public class LinkedFireStationController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LinkedFireStationService linkedFireStationService;

	@PostMapping
	public ResponseEntity<LinkedFireStation> insertLinkedFireStation(@RequestBody LinkedFireStation linkedFireStation)
			throws LinkedFireStationsDataNotFoundException, DuplicatedLinkedFireStationException, RequestBodyException {

		checkForInvalidBody(linkedFireStation);
		
		log.info(System.lineSeparator()
				+ "User has entered \"/firestation\" endpoint (POST request) to insert a new fire station mapping for address: "
				+ linkedFireStation.getAddress() + " ." + System.lineSeparator() + "Request has returned :"
				+ linkedFireStation.toString());

		return ResponseEntity.ok(linkedFireStationService.insertLinkedFireStation(linkedFireStation));
	}

	@PutMapping("")
	public ResponseEntity<LinkedFireStation> updateLinkedFireStation(@RequestBody LinkedFireStation linkedFireStation)
			throws LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException,
			DuplicatedLinkedFireStationException, RequestBodyException {

		checkForInvalidBody(linkedFireStation);
		
		log.info(System.lineSeparator()
				+ "User has entered \"/firestation\" endpoint (PUT request) to update fire station mapping for address: "
				+ linkedFireStation.getAddress() + " ." + System.lineSeparator() + "Request has returned :"
				+ linkedFireStation.toString());

		return ResponseEntity.ok(linkedFireStationService.updateLinkedFireStation(linkedFireStation));
	}

	@DeleteMapping("")
	public ResponseEntity<LinkedFireStation> deleteLinkedFireStation(@RequestBody LinkedFireStation linkedFireStation)
			throws LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException,
			DuplicatedLinkedFireStationException, RequestBodyException {

		checkForInvalidBody(linkedFireStation);
	
		log.info(System.lineSeparator()
				+ "User has entered \"/firestation\" endpoint (DELETE request) to delete fire station mapping for address: "
				+ linkedFireStation.getAddress() + " ." + System.lineSeparator() + "Request has returned :"
				+ linkedFireStation.toString());

		return ResponseEntity.ok(linkedFireStationService.deleteLinkedFireStation(linkedFireStation));
	}

	private void checkForInvalidBody(LinkedFireStation linkedFireStation) throws RequestBodyException {
		if (linkedFireStation.getAddress().isBlank())
			throw new RequestBodyException("Fire station mapping's address can't be empty");
	}
	

}
