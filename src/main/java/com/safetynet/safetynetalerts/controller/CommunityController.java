package com.safetynet.safetynetalerts.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.service.CommunityService;

@RestController
public class CommunityController {

	@Autowired
	private CommunityService communityService ;
	
	@GetMapping("/communityEmail")
	public List<String> communityEmails (@RequestParam("city")String city){
		List<String> communityEmails = new ArrayList<String>();
		
		communityEmails = communityService.getCommunityEmails(city);
		
		return communityEmails;
	}

	@GetMapping("/personInfo")
	public LinkedHashMap<String,String> personInfo 
	(@RequestParam("firstName")String firstName, @RequestParam("lastName")String lastName){
		String identifier = firstName + lastName ;
		
		LinkedHashMap<String,String> personInfo = communityService.getPersonInfo(identifier);
		
		return personInfo;
	}
	
	@GetMapping("/firestation")
	public List<LinkedHashMap<String,String>> personsCoveredByFireStation
	(@RequestParam("stationNumber")String stationNumber){
		List<LinkedHashMap<String,String>> personsCovered = communityService.getPersonsCoveredByFireStations(stationNumber);
		
		return personsCovered;
		
	}
	
	
	
	
	
	
}
