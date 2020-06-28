package com.safetynet.safetynetalerts.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmergencyController {

	@GetMapping("/childAlert")
	public List<LinkedHashMap<String,String>> getChildren
	(@RequestParam("address")String address){
		
		
		
		return null;
		
	}
	
}
