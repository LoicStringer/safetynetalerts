package com.safetynet.safetynetalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.responseentity.EmergencyChildAlert;
import com.safetynet.safetynetalerts.responseentity.EmergencyFireAddressInfos;
import com.safetynet.safetynetalerts.responseentity.EmergencyFloodInfos;
import com.safetynet.safetynetalerts.service.EmergencyService;

@RestController
public class EmergencyController {

	@Autowired
	private EmergencyService emergencyService;
	
	@GetMapping("/childAlert")
	public ResponseEntity<EmergencyChildAlert> getChildren
	(@RequestParam("address")String address){
		
		EmergencyChildAlert anyChildThere = emergencyService.getChildrenThere(address);
		
		return ResponseEntity.ok(anyChildThere);
	}
	
	@GetMapping("/phoneAlert")
	public List<String> getPhoneNumbers
	(@RequestParam("firestation")String stationNumber){
		
		List<String> phoneNumbers = emergencyService.getCoveredPersonsPhoneNumbers(stationNumber);
		
		return phoneNumbers;
	}
	
	@GetMapping("/fire")
	public  ResponseEntity<EmergencyFireAddressInfos> getInhabitants
	(@RequestParam("address")String address){
		
		EmergencyFireAddressInfos inhabitantsThere = emergencyService.getPersonsThereInfos(address);
		
		return ResponseEntity.ok(inhabitantsThere);
	}
	
	@GetMapping("/flood/stations")
	public ResponseEntity<EmergencyFloodInfos> getHomesInfo
	(@RequestParam("stations")List<String> stationNumbers){
		
		EmergencyFloodInfos emergencyFloodInfos = emergencyService.getCoveredHomesInfos(stationNumbers);
		
		return ResponseEntity.ok(emergencyFloodInfos);
	}
	
}
