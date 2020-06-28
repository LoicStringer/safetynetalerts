package com.safetynet.safetynetalerts.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.servivce.CommunityService;

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
	
}
