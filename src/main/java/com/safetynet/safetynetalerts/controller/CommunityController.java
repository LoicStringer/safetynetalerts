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

/**
 * Controller class which exposes endpoints dealing with the people informations
 * such as emails or specific person infos.
 * @author newbie
 * @see CommunityService
 *
 */
@RestController
public class CommunityController {

	@Autowired
	private CommunityService communityService ;
	
	/**
	 * Call to the {@link CommunityService#getCommunityEmails(String)} method,
	 * retrieving the emails' list of all the specified city's inhabitants.
	 * @param String city's name.
	 * @return a {@link List} of emails as String.
	 * @see CommunityService
	 */
	@GetMapping("/communityEmail")
	public ResponseEntity<List<String>> communityEmails (@RequestParam("city")String city){
		List<String> communityEmails = communityService.getCommunityEmails(city);
		return ResponseEntity.ok(communityEmails);
	}

	/**
	 * Call to the {@link CommunityService#getPersonInfo(String)} method,
	 * retrieving a specific person's informations.
	 * @param Person's first name, the "firstName" attribute's value.
	 * @param Person's last name, the "lastName" attribute's value.
	 * @return a {@link CommunityPersonInfo} custom object 
	 * containing the required person informations. 
	 * @see CommunityPersonInfo
	 * @see Person
	 * @see MedicalRecord
	 */
	@GetMapping("/personInfo")
	public ResponseEntity<CommunityPersonInfo> personInfo 
	(@RequestParam("firstName")String firstName, @RequestParam("lastName")String lastName){		
		CommunityPersonInfo communityPersonInfo = communityService.getPersonInfo(firstName + lastName);
		return ResponseEntity.ok(communityPersonInfo);
	}
	
	/**
	 * Call to the {@link CommunityService#getPersonsCoveredByFireStation(String)
	 * retrieving a list of the persons covered by the specified fire station,
	 * identified by its ("id") number.
	 * @param a LinkedFireStation stationNumber as int 
	 * @return a {@link CommunityPersonsCoveredByFireStation} custom object
	 * containing all persons' informations.
	 * @see CommunityPersonsCoveredByFireStation
	 */
	@GetMapping("/firestation")
	public ResponseEntity<CommunityPersonsCoveredByFireStation> personsCoveredByFireStation
	(@RequestParam("stationNumber")String stationNumber){
		CommunityPersonsCoveredByFireStation personsCovered = communityService.getPersonsCoveredByFireStation(stationNumber);
		return ResponseEntity.ok(personsCovered);	
	}
	
}
