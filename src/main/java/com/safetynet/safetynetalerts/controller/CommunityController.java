package com.safetynet.safetynetalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.responseentity.CommunityPersonInfo;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonsCoveredByFireStation;
import com.safetynet.safetynetalerts.service.CommunityService;

@RestController
public class CommunityController {

	@Autowired
	private CommunityService communityService ;
	
	@GetMapping("/communityEmail")
	public ResponseEntity<List<String>> communityEmails (@RequestParam("city")String city){
		List<String> communityEmails = communityService.getCommunityEmails(city);
		return ResponseEntity.ok(communityEmails);
	}

	@GetMapping("/personInfo")
	public ResponseEntity<CommunityPersonInfo> personInfo 
	(@RequestParam("firstName")String firstName, @RequestParam("lastName")String lastName){		
		CommunityPersonInfo communityPersonInfo = communityService.getPersonInfo(firstName + lastName);
		return ResponseEntity.ok(communityPersonInfo);
	}
	
	@GetMapping("/firestation")
	public ResponseEntity<CommunityPersonsCoveredByFireStation> personsCoveredByFireStation
	(@RequestParam("stationNumber")String stationNumber){
		CommunityPersonsCoveredByFireStation personsCovered = communityService.getPersonsCoveredByFireStation(stationNumber);
		return ResponseEntity.ok(personsCovered);	
	}
	
	
	
	
	
	
}
