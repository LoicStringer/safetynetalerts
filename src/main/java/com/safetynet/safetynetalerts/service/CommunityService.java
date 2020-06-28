package com.safetynet.safetynetalerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class CommunityService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private MedicalRecordDao medicalRecordDao;

	@Autowired
	private LinkedFireStationDao linkedFireStationDao;

	public String getAgeFromBirthDate(String birthDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate birthDateToDate = LocalDate.parse(birthDate, formatter);
		String age = String.valueOf(Period.between(birthDateToDate, LocalDate.now()).getYears());
		return age;
	}

	public List<String> getCommunityEmails(String city) {
		List<String> emails = new ArrayList<String>();

		List<Person> persons = personDao.getAll();
		
		for (Person person : persons) {
			if (person.getCity().equals(city))
				emails.add(person.getEmail());
		}
		return emails;
	}

	public LinkedHashMap<String, String> getPersonInfo(String identifier) {
		LinkedHashMap<String, String> infos = new LinkedHashMap<String, String>();
		
		Person personToGetInfoFrom = personDao.getOne(identifier);
		MedicalRecord medicalRecordToGetInfoFrom = medicalRecordDao.getOne(identifier);

		infos.put("firstName", personToGetInfoFrom.getFirstName());
		infos.put("lastName", personToGetInfoFrom.getLastName());
		infos.put("age", this.getAgeFromBirthDate(medicalRecordToGetInfoFrom.getBirthdate()));
		infos.put("address", personToGetInfoFrom.getAddress());
		infos.put("email", personToGetInfoFrom.getEmail());
		infos.put("medications", Arrays.toString(medicalRecordToGetInfoFrom.getMedications()));
		infos.put("allergies", Arrays.toString(medicalRecordToGetInfoFrom.getAllergies()));

		return infos;
	}

	public List<LinkedHashMap<String, String>> getPersonsCoveredByFireStations(String stationNumber) {
		LinkedHashMap<String, String> ageCount = new LinkedHashMap<String, String>();
		List<LinkedHashMap<String, String>> personsCovered = new ArrayList<LinkedHashMap<String, String>>();
		int childCount = 0;
		int adultCount = 0;
		LinkedHashMap<String, String> personsInfo;
		
		List<LinkedFireStation> linkedFireStations = linkedFireStationDao.getAll();
		List<Person> persons = personDao.getAll();
		List<MedicalRecord> medicalRecords = medicalRecordDao.getAll();

		for (LinkedFireStation linkedFireStation : linkedFireStations) {
			if (linkedFireStation.getStation().equals(stationNumber)) {
				String adressCovered = linkedFireStation.getAddress();
				for (Person person : persons) {
					if (adressCovered.equals(person.getAddress())) {

						personsInfo = new LinkedHashMap<String, String>();
						personsInfo.put("firstname", person.getFirstName());
						personsInfo.put("lastName", person.getLastName());
						personsInfo.put("address", person.getAddress());
						personsInfo.put("phone", person.getPhone());
						personsCovered.add(personsInfo);
						adultCount++;

						for (MedicalRecord medicalRecord : medicalRecords) {
							if ((medicalRecord.getFirstName() + medicalRecord.getLastName())
									.equals((person.getFirstName() + person.getLastName()))) {
								String age = getAgeFromBirthDate(medicalRecord.getBirthdate());

								if (Integer.valueOf(age) < 18) {
									adultCount--;
									childCount++;
								}
							}

						}

					}

				}
			}

		}
		ageCount.put("adults", String.valueOf(adultCount));
		ageCount.put("children", String.valueOf(childCount));
		personsCovered.add(ageCount);
		
		return personsCovered;
	}

}
