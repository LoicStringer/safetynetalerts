package com.safetynet.safetynetalerts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootApplication
public class SafetynetalertsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetalertsApplication.class, args);
		MedicalRecordDao medicalRecordDao = new MedicalRecordDao();
		LinkedFireStationDao linkedFireStationDao =new LinkedFireStationDao();
		PersonDao personDao = new PersonDao();
		List<Person> persons = new ArrayList<Person>();
		persons= personDao.getAll();
		System.out.println(persons);
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		medicalRecords= medicalRecordDao.getAll();
		System.out.println(medicalRecords);
		List<LinkedFireStation> linkedFireStations = new ArrayList<LinkedFireStation>();
		linkedFireStations= linkedFireStationDao.getAll();
		System.out.println(linkedFireStations);
		
		
		
		
	}

}
