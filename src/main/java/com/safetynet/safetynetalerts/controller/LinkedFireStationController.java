package com.safetynet.safetynetalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.service.LinkedFireStationService;

@RestController
@RequestMapping("/firestation")
public class LinkedFireStationController {
	
	@Autowired
	private LinkedFireStationService linkedFireStationService;
	
	@GetMapping("")
	public ResponseEntity<List<LinkedFireStation>> getAllLinkedFireStations(){
		return ResponseEntity.ok(linkedFireStationService.getAllLinkedFireStations());
	}
	
	@GetMapping("/{address}")
	public ResponseEntity<LinkedFireStation> getOneLinkedFireStation
	(@PathVariable("address") String address){
		return ResponseEntity.ok(linkedFireStationService.getOneLinkedFireStation(address));
		
	}
	
	@PostMapping
	public ResponseEntity<LinkedFireStation> insertLinkedFireStation
	(@RequestBody LinkedFireStation linkedFireStation){	
		linkedFireStationService.insertLinkedFireStation(linkedFireStation);
		return ResponseEntity.ok(linkedFireStation);
	}
	
	@PutMapping("")
	public ResponseEntity<LinkedFireStation> updateLinkedFireStation
	(@RequestBody LinkedFireStation linkedFireStation) {
		return ResponseEntity.ok(linkedFireStationService.updateLinkedFireStation(linkedFireStation));
	}
	
	@DeleteMapping("/{address}")
	public ResponseEntity<Boolean> deleteLinkedFireStation
	(@PathVariable("address") String address) {
		return ResponseEntity.ok(linkedFireStationService.deleteLinkedFireStation(address));
		
	}
	

}
