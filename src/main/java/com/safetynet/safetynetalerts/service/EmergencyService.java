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
import com.safetynet.safetynetalerts.model.LinkedFireStation;
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
		LinkedHashMap<String, String> childrenInfo;
		List<LinkedHashMap<String, String>> anyChildThere = new ArrayList<LinkedHashMap<String, String>>();
		ArrayList<String> otherPersons = new ArrayList<String>();

		List<Person> persons = personDao.getAll();
		List<MedicalRecord> medicalRecords = medicalRecordDao.getAll();

		for (Person person : persons) {
			if (person.getAddress().equals(address)) {
				String personIdentifier = person.getFirstName() + person.getLastName();
				otherPersons.add(person.getFirstName() + " " + person.getLastName());
				for (MedicalRecord medicalRecord : medicalRecords) {
					String medicalIdentifier = medicalRecord.getFirstName() + medicalRecord.getLastName();
					int age = Integer.valueOf(communityService.getAgeFromBirthDate(medicalRecord.getBirthdate()));
					if (medicalIdentifier.equals(personIdentifier) && age < 18) {
						otherPersons.remove(otherPersons.size() - 1);
						childrenInfo = new LinkedHashMap<String, String>();
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

	public List<String> getCoveredPersonsPhoneNumbers(String stationNumber) {
		List<String> phoneNumbers = new ArrayList<String>();

		List<LinkedFireStation> linkedFireStations = linkedFireStationDao.getAll();
		List<Person> persons = personDao.getAll();

		for (LinkedFireStation linkedFireStation : linkedFireStations) {
			String coveredAdress = linkedFireStation.getAddress();
			if (linkedFireStation.getStation().equals(stationNumber)) {
				for (Person person : persons) {
					String personAdress = person.getAddress();
					if (personAdress.equals(coveredAdress)) {
						phoneNumbers.add(person.getPhone());
					}
				}
			}
		}

		return phoneNumbers;
	}

	public List<LinkedHashMap<String, LinkedHashMap<String, String>>> whosThere(String address) {
		List<LinkedHashMap<String, LinkedHashMap<String, String>>> inhabitantsThere = new ArrayList<LinkedHashMap<String, LinkedHashMap<String, String>>>();
		LinkedHashMap<String, LinkedHashMap<String, String>> inhabitants;
		LinkedHashMap<String, String> inhabitantInfo;
		int inhabitantCount = 0;

		List<LinkedFireStation> linkedFireStations = linkedFireStationDao.getAll();
		List<Person> persons = personDao.getAll();
		List<MedicalRecord> medicalRecords = medicalRecordDao.getAll();

		for (LinkedFireStation linkedFireStation : linkedFireStations) {
			if (linkedFireStation.getAddress().equals(address)) {
				inhabitants = new LinkedHashMap<String, LinkedHashMap<String, String>>();
				inhabitantInfo = new LinkedHashMap<String, String>();
				inhabitantInfo.put("stationNumber", linkedFireStation.getStation());
				inhabitants.put("coveringFireStation", inhabitantInfo);
				inhabitantsThere.add(inhabitants);
			}
		}
		for (Person person : persons) {
			String personIdentifier = person.getFirstName() + person.getLastName();
			if (person.getAddress().equals(address)) {
				for (MedicalRecord medicalRecord : medicalRecords) {
					String medicalIdentifier = medicalRecord.getFirstName() + medicalRecord.getLastName();
					if (personIdentifier.equals(medicalIdentifier)) {
						inhabitantCount++;
						inhabitantInfo = new LinkedHashMap<String, String>();
						inhabitants = new LinkedHashMap<String, LinkedHashMap<String, String>>();
						inhabitantInfo.put("firstName", medicalRecord.getFirstName());
						inhabitantInfo.put("lastName", medicalRecord.getLastName());
						inhabitantInfo.put("age", communityService.getAgeFromBirthDate(medicalRecord.getBirthdate()));
						inhabitantInfo.put("phoneNumber", person.getPhone());
						inhabitantInfo.put("medications", Arrays.toString(medicalRecord.getMedications()));
						inhabitantInfo.put("allergies", Arrays.toString(medicalRecord.getAllergies()));
						inhabitants.put("inhabitant" + " " + inhabitantCount, inhabitantInfo);
						inhabitantsThere.add(inhabitants);
					}
				}
			}
		}

		return inhabitantsThere;
	}

	public LinkedHashMap<String, LinkedHashMap<LinkedHashMap<String, String>, List<LinkedHashMap<String, LinkedHashMap<String, String>>>>> getHomesInfoByFireStations(
			List<String> stationNumbers) {

		LinkedHashMap<String, LinkedHashMap<LinkedHashMap<String, String>, List<LinkedHashMap<String, LinkedHashMap<String, String>>>>> homesInfoByFireStation 
		= new LinkedHashMap<String, LinkedHashMap<LinkedHashMap<String, String>, List<LinkedHashMap<String, LinkedHashMap<String, String>>>>>();
		LinkedHashMap<String, String> coveredHomes;
		List<LinkedHashMap<String, LinkedHashMap<String, String>>> inhabitantsInfo;
		LinkedHashMap<LinkedHashMap<String, String>, List<LinkedHashMap<String, LinkedHashMap<String, String>>>> homesInfo
		= new LinkedHashMap<LinkedHashMap<String, String>, List<LinkedHashMap<String, LinkedHashMap<String, String>>>>();
		int homesCount = 0;

		List<LinkedFireStation> linkedFireStations = linkedFireStationDao.getAll();
		
		for (LinkedFireStation linkedFireStation : linkedFireStations) {
			String linkedStationNumber = linkedFireStation.getStation();
			for (String stationNumber : stationNumbers) {
				if (linkedStationNumber.equals(stationNumber)) {
					homesCount++;
					coveredHomes = new LinkedHashMap<String, String>();
					coveredHomes.put("home" + " " + homesCount, linkedFireStation.getAddress());
					inhabitantsInfo = new ArrayList<LinkedHashMap<String, LinkedHashMap<String, String>>>();
					inhabitantsInfo = this.whosThere(linkedFireStation.getAddress());
					inhabitantsInfo.remove(0);
					homesInfo.put(coveredHomes, inhabitantsInfo);
				}
				homesInfoByFireStation.put("fireStation number" + " " + stationNumber, homesInfo);
			}
			
			
		}
			
			
		
					
					
					
					
					
				

		

		return homesInfoByFireStation;
	}

}
