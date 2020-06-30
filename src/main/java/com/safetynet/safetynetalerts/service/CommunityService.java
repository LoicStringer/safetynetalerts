package com.safetynet.safetynetalerts.service;

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

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonInfo;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonCoveredByFireStation;

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

	public CommunityPersonInfo getPersonInfo(String identifier) {
		CommunityPersonInfo communityPersonInfo = new CommunityPersonInfo();

		Person personToGetInfoFrom = personDao.getOne(identifier);
		MedicalRecord medicalRecordToGetInfoFrom = medicalRecordDao.getOne(identifier);

		communityPersonInfo.setFirstName(personToGetInfoFrom.getFirstName());
		communityPersonInfo.setLastName(personToGetInfoFrom.getLastName());
		communityPersonInfo.setAge(this.getAgeFromBirthDate(medicalRecordToGetInfoFrom.getBirthdate()));
		communityPersonInfo.setAddress(personToGetInfoFrom.getAddress());
		communityPersonInfo.setEmail(personToGetInfoFrom.getEmail());
		communityPersonInfo.setMedications(Arrays.toString(medicalRecordToGetInfoFrom.getMedications()));
		communityPersonInfo.setAllergies(Arrays.toString(medicalRecordToGetInfoFrom.getAllergies()));

		return communityPersonInfo;
	}

	public List<Object> getPersonsCoveredByFireStations(String stationNumber) {
		List<Object> communityPersonsCoveredByFireStation = new ArrayList<Object>();
		int childCount = 0;
		int adultCount = 0;

		List<LinkedFireStation> linkedFireStations = linkedFireStationDao.getAll();
		List<Person> persons = personDao.getAll();
		List<MedicalRecord> medicalRecords = medicalRecordDao.getAll();

		for (LinkedFireStation linkedFireStation : linkedFireStations) {
			if (linkedFireStation.getStation().equals(stationNumber)) {
				String adressCovered = linkedFireStation.getAddress();
				for (Person person : persons) {
					if (adressCovered.equals(person.getAddress())) {
						CommunityPersonCoveredByFireStation communityPersonCoveredByFireStation = new CommunityPersonCoveredByFireStation(
								person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());
						communityPersonsCoveredByFireStation.add(communityPersonCoveredByFireStation);

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
		Map<String, Integer> personsCount = new HashMap<String, Integer>();
		personsCount.put("adultsCount", adultCount);
		personsCount.put("childrenCount", childCount);
		communityPersonsCoveredByFireStation.add(personsCount);

		return communityPersonsCoveredByFireStation;
	}

}
