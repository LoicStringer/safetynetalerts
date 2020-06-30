package com.safetynet.safetynetalerts.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.service.EmergencyService;

@RestController
public class EmergencyController {

	@Autowired
	private EmergencyService emergencyService;
	
	@GetMapping("/childAlert")
	public List<LinkedHashMap<String,String>> getChildren
	(@RequestParam("address")String address){
		
		List<LinkedHashMap<String,String>> anyChildThere = emergencyService.getChildrenThere(address);
		
		return anyChildThere;
	}
	
	@GetMapping("/phoneAlert")
	public List<String> getPhoneNumbers
	(@RequestParam("firestation")String stationNumber){
		
		List<String> phoneNumbers = emergencyService.getCoveredPersonsPhoneNumbers(stationNumber);
		
		return phoneNumbers;
	}
	
	@GetMapping("/fire")
	public  ResponseEntity<List<LinkedHashMap<String, LinkedHashMap<String,String>>>> getInhabitants
	(@RequestParam("address")String address){
		
		List<LinkedHashMap<String, LinkedHashMap<String,String>>> inhabitantsThere = emergencyService.whosThere(address);
		
		return ResponseEntity.ok(inhabitantsThere);
	}
	
	@GetMapping("/flood/stations")
	public LinkedHashMap<String, LinkedHashMap<LinkedHashMap<String, String>, List<LinkedHashMap<String, LinkedHashMap<String, String>>>>>
	getHomesInfo(@RequestParam("stations")List<String> stationNumbers){
		
		LinkedHashMap<String, LinkedHashMap<LinkedHashMap<String, String>, List<LinkedHashMap<String, LinkedHashMap<String, String>>>>>
		homesInfo = emergencyService.getHomesInfoByFireStations(stationNumbers);
		
		return homesInfo;
	}
	
}
