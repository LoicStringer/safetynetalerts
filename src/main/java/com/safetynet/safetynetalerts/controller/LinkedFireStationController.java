package com.safetynet.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.service.LinkedFireStationService;

@RestController
@RequestMapping("/firestation")
public class LinkedFireStationController {
	
	@Autowired
	private LinkedFireStationService linkedFireStationService;
		
	@PostMapping
	public ResponseEntity<LinkedFireStation> insertLinkedFireStation
	(@RequestBody LinkedFireStation linkedFireStation){	
		linkedFireStationService.insertLinkedFireStation(linkedFireStation);
		return ResponseEntity.ok(linkedFireStation);
	}
	
	@PutMapping("")
	public ResponseEntity<LinkedFireStation> updateLinkedFireStation
	(@RequestBody LinkedFireStation linkedFireStation) {
		return ResponseEntity.ok(linkedFireStationService.updateLinkedFireStation(linkedFireStation));
	}
	
	@DeleteMapping("")
	public ResponseEntity<LinkedFireStation> deleteLinkedFireStation
	(@RequestBody LinkedFireStation linkedFireStation) {
		return ResponseEntity.ok(linkedFireStationService.deleteLinkedFireStation(linkedFireStation));
		
	}
	

}
