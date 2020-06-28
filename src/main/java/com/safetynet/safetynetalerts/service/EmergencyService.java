package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class EmergencyService {

	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private LinkedFireStationDao linkedFireStationDao;
	
	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	@Autowired
	private CommunityService communityService;
	
	public List<LinkedHashMap<String, String>> getChildrenThere(String address) {
		LinkedHashMap<String,String> childrenInfo ;
		List<LinkedHashMap<String, String>> anyChildThere = new ArrayList<LinkedHashMap<String, String>>();
		ArrayList<String> otherPersons = new ArrayList<String>();
		
		List<Person> persons = personDao.getAll();
		List<MedicalRecord> medicalRecords = medicalRecordDao.getAll();
		
		for(Person person:persons) {	
			if(person.getAddress().equals(address)) {
				String personIdentifier = person.getFirstName()+person.getLastName();
				otherPersons.add(person.getFirstName()+" "+person.getLastName());
				for(MedicalRecord medicalRecord:medicalRecords) {
					String medicalIdentifier = medicalRecord.getFirstName()+medicalRecord.getLastName();
					int age = Integer.valueOf(communityService.getAgeFromBirthDate(medicalRecord.getBirthdate()));
					if(medicalIdentifier.equals(personIdentifier) && age<18) {
						otherPersons.remove(otherPersons.size()-1);
						childrenInfo = new LinkedHashMap<String,String>();
						childrenInfo.put("firstName", person.getFirstName());
						childrenInfo.put("lastName", person.getLastName());
						childrenInfo.put("age", String.valueOf(age));
						childrenInfo.put("otherPersons", otherPersons.toString());
						anyChildThere.add(childrenInfo);
					}
				}
			}
		}
		return anyChildThere;
	}

}
