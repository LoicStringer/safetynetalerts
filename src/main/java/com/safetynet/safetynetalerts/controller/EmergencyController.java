package com.safetynet.safetynetalerts.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	
	
}
