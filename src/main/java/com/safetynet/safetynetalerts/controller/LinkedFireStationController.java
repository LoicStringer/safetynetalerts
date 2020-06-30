package com.safetynet.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping("/{address}")
	public ResponseEntity<LinkedFireStation> updateLinkedFireStation
	(@PathVariable("address")String address) {
		LinkedFireStation linkedFireStation = new LinkedFireStation();
	
		return ResponseEntity.ok(linkedFireStation);
	}
	
	@DeleteMapping("/{address}")
	public boolean deleteLinkedFireStation
	(@PathVariable String address) {
		
		return false;
		
	}
	

}
