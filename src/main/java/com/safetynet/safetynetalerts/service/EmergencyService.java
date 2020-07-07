package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.safetynet.safetynetalerts.responseentity.EmergencyChildAlert;
import com.safetynet.safetynetalerts.responseentity.EmergencyFireAddressInfos;

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

	public List<Object> getChildrenThere(String address) {
		List<Object> childrenThere = new ArrayList<Object>();
		
		List<Person> personsThere = personDao.getAll().stream()
				.filter(p -> p.getAddress().equals(address))
				.collect(Collectors.toList());
		
		List<String> identifiers = personsThere.stream()
				.map(id ->  id.getFirstName() + id.getLastName())
				.collect(Collectors.toList());
		
		List<HashMap<String,String>> adultsThere = medicalRecordDao.getAll().stream()
				.filter(mr ->  identifiers.contains(mr.getFirstName()+mr.getLastName()))
				.filter(mr -> Integer.valueOf(communityService.getAgeFromBirthDate(mr.getBirthdate()))>18)
				.map(temp -> {
					HashMap <String,String> adult = new HashMap<String,String>();
					adult.put("firstName", temp.getFirstName());
					adult.put("lastName", temp.getLastName());
					adult.put("age", communityService.getAgeFromBirthDate(temp.getBirthdate()));
					return adult;
				}).collect(Collectors.toList());
				
		List<EmergencyChildAlert> anyChildThere = medicalRecordDao.getAll().stream()
				.filter(mr -> identifiers.contains(mr.getFirstName()+mr.getLastName()))
				.filter(mr -> Integer.valueOf(communityService.getAgeFromBirthDate(mr.getBirthdate()))<18)
				.map(temp ->{
					EmergencyChildAlert childThere = new EmergencyChildAlert();
					childThere.setFirstName(temp.getFirstName());
					childThere.setLastName(temp.getLastName());
					childThere.setAge(communityService.getAgeFromBirthDate(temp.getBirthdate()));
					return childThere;
				}).collect(Collectors.toList());
		
		
		/*
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
		*/
		childrenThere.add(anyChildThere);
		childrenThere.add(adultsThere);
		return childrenThere;
	}

	public List<String> getCoveredPersonsPhoneNumbers(String stationNumber) {
		
		List<String> addressesCovered = linkedFireStationDao.getAll().stream()
				.filter(lfs -> lfs.getStation().equals(stationNumber))
				.map(LinkedFireStation::getAddress)
				.collect(Collectors.toList());
		
		List<String> phoneNumbers = personDao.getAll().stream()
				.filter(p -> addressesCovered.contains(p.getAddress()))
				.map(Person::getPhone)
				.collect(Collectors.toList());
				
				
		/*
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
		 */
		return phoneNumbers;
	}
/*
	public List<Object> whosThere(String address) {
		
		List<Person> personsThere = personDao.getAll().stream()
				.filter(p -> p.getAddress().equals(address))
				.collect(Collectors.toList());
	
		List<String> identifiers = personsThere.stream()
				.map(id ->  id.getFirstName() + id.getLastName())
				.collect(Collectors.toList());
		
		List<EmergencyFireAddressInfos> addressesInfos = medicalRecordDao.getAll().stream()
				.filter(mr -> identifiers.contains(mr.getFirstName()+mr.getLastName()))
				.map(temp -> {
					EmergencyFireAddressInfos addressInfo = new EmergencyFireAddressInfos();
					addressInfo.setAge(communityService.getAgeFromBirthDate(temp.getBirthdate()));
					addressInfo.setFirstName(temp.getFirstName());
					addressInfo.setLastname(temp.getLastName());
					addressInfo.setMedications(temp.getMedications());
					addressInfo.setAllergies(temp.getAllergies());
					return addressInfo;
				}).collect(Collectors.toList());
		
					
				
				*/
		
		
		
		/*
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
		
		
		List<Object> inhabitantsThere = new ArrayList<Object>();
		inhabitantsThere.add(stationNumber);
		inhabitantsThere.add(addressesInfos);
		
		return inhabitantsThere;
	}
*/
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
					//inhabitantsInfo = this.whosThere(linkedFireStation.getAddress());
					inhabitantsInfo.remove(0);
					homesInfo.put(coveredHomes, inhabitantsInfo);
				}
				homesInfoByFireStation.put("fireStation number" + " " + stationNumber, homesInfo);
			}
			
			
		}
			
			
		
					
					
					
					
					
				

		

		return homesInfoByFireStation;
	}

}
