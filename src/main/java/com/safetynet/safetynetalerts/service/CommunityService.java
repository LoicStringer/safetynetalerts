package com.safetynet.safetynetalerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonCoveredByFireStation;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonInfo;

@Service
public class CommunityService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private MedicalRecordDao medicalRecordDao;

	@Autowired
	private LinkedFireStationDao linkedFireStationDao;

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
		HashMap <String,String> personsCount = new HashMap<String,String>();
		
		List<String> adressesCovered = linkedFireStationDao.getAll().stream()
				.filter(ad -> ad.getStation().equals(stationNumber))
				.map(LinkedFireStation::getAddress)
				.collect(Collectors.toList());
		
		List<Person> personsCovered = personDao.getAll().stream()
				.filter(p -> adressesCovered.contains(p.getAddress()))
				.collect(Collectors.toList());
		
		List<CommunityPersonCoveredByFireStation> personsCoveredByFireStations = 
				personsCovered.stream()
				.map(temp -> {
					CommunityPersonCoveredByFireStation personCovered = new CommunityPersonCoveredByFireStation();
					personCovered.setFirstName(temp.getFirstName());
					personCovered.setLastName(temp.getLastName());
					personCovered.setAddress(temp.getAddress());
					personCovered.setPhoneNumber(temp.getPhone());
					return personCovered;
				}).collect(Collectors.toList());

		/*
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
*/
		List<String> identifiers = personsCovered.stream()
				.map(id ->  id.getFirstName() + id.getLastName())
				.collect(Collectors.toList());
		
		long childCount = medicalRecordDao.getAll().stream()
				.filter(mr -> identifiers.contains(mr.getFirstName()+mr.getLastName()))
				.filter(mr -> Integer.valueOf(getAgeFromBirthDate(mr.getBirthdate()))<18)
				.count();
		
		personsCount.put("adultsCount",String.valueOf((personsCoveredByFireStations.size()-childCount)));
		personsCount.put("childrenCount", String.valueOf(childCount));
		
		communityPersonsCoveredByFireStation.add(personsCoveredByFireStations);
		communityPersonsCoveredByFireStation.add(personsCount);
		
		return communityPersonsCoveredByFireStation;
	}

	public String getAgeFromBirthDate(String birthDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate birthDateToDate = LocalDate.parse(birthDate, formatter);
		String age = String.valueOf(Period.between(birthDateToDate, LocalDate.now()).getYears());
		
		return age;
	}
	
	
	
}
