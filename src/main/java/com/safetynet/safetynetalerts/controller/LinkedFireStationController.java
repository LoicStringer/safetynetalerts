package com.safetynet.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.dao.IDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;

@RestController
@RequestMapping("/firestation")
public class LinkedFireStationController {
	
	
	@PutMapping
	public boolean insertLinkedFireStation () {
		
		return false;
	}
	
	@PatchMapping("/{address}")
	public boolean updateLinkedFireStation
	(@PathVariable String address) {
		
	
		return false;
		
	}
	
	@DeleteMapping("/{address}")
	public boolean deleteLinkedFireStation
	(@PathVariable String address) {
		
		return false;
		
	}
	

}
