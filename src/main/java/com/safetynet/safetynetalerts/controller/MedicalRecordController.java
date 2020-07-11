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

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	
	@GetMapping("")
	public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords(){
		return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords());
	}
	
	@GetMapping("/{identifier}")
	public ResponseEntity<MedicalRecord> getOneMedicalRecord(@PathVariable("identifier") String identifier){
		return ResponseEntity.ok(medicalRecordService.getOneMedicalRecord(identifier));
	}
	
	@PostMapping("")
	public ResponseEntity<MedicalRecord> insertMedicalRecord(@RequestBody MedicalRecord medicalRecord){
		
		medicalRecordService.insertMedicalRecord(medicalRecord);
		
		return ResponseEntity.ok(medicalRecord);
	}
	
	@PutMapping("")
	public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
		return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(medicalRecord));	
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<Boolean> deleteMedicalRecord(@PathVariable("identifier") String identifier){
		return ResponseEntity.ok(medicalRecordService.deletePerson(identifier));
	}
	
	
	
	
	
}
