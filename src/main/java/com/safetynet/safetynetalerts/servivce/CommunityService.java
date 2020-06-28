package com.safetynet.safetynetalerts.servivce;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class CommunityService {

	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private MedicalRecordDao medicalRecordDao;

	
	public String getAgeFromBirthDate(String birthDate ) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		LocalDate birthDateToDate = LocalDate.parse(birthDate, formatter);
		String age = String.valueOf(Period.between(birthDateToDate, LocalDate.now()).getYears());
		return age;
	}
	
	public List<String> getCommunityEmails(String city) {
		List<Person> persons = new ArrayList<Person>();
		List<String> emails = new ArrayList<String>();

		persons = personDao.getAll();

		for (Person person : persons) {
			if (person.getCity().equals(city))
				emails.add(person.getEmail());
		}
		return emails;
	}

	public LinkedHashMap<String,String> getPersonInfo(String identifier) {
		Person personToGetInfoFrom = personDao.getOne(identifier);
		MedicalRecord medicalRecordToGetInfoFrom = medicalRecordDao.getOne(identifier);
		LinkedHashMap<String,String> infos = new LinkedHashMap<String,String>();
		
		infos.put("firstName", personToGetInfoFrom.getFirstName());
		infos.put("lastName", personToGetInfoFrom.getLastName());
		infos.put("age",this.getAgeFromBirthDate(medicalRecordToGetInfoFrom.getBirthdate()));
		infos.put("address", personToGetInfoFrom.getAddress());
		infos.put("email", personToGetInfoFrom.getEmail());
		infos.put("medications",Arrays.toString(medicalRecordToGetInfoFrom.getMedications()));
		infos.put("allergies", Arrays.deepToString(medicalRecordToGetInfoFrom.getAllergies()));
		
		return infos;
	}

}
