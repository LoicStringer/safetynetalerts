package com.safetynet.safetynetalerts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonCoveredByFireStation;
import com.safetynet.safetynetalerts.service.CommunityService;

@SpringBootApplication
public class SafetynetalertsApplication {

	

	
	public static void main(String[] args) {
		SpringApplication.run(SafetynetalertsApplication.class, args);
		
		MedicalRecordDao medicalRecordDao =new MedicalRecordDao();
		MedicalRecord medicalRecord = medicalRecordDao.getOne("ReginoldWalker");
		System.out.println(medicalRecord);
	
		
	}

}
