package com.safetynet.safetynetalerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
import com.safetynet.safetynetalerts.responseentity.CommunityPersonInfo;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonsCoveredByFireStation;

@Service
public class CommunityService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private MedicalRecordDao medicalRecordDao;

	@Autowired
	private LinkedFireStationDao linkedFireStationDao;

	public List<String> getCommunityEmails(String city) {
		
		List<String> emails =  personDao.getAll().stream()
				.filter(p -> p.getCity().equals(city))
				.map(p -> p.getEmail())
				.collect(Collectors.toList());
		
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
		communityPersonInfo.setMedications(medicalRecordToGetInfoFrom.getMedications());
		communityPersonInfo.setAllergies(medicalRecordToGetInfoFrom.getAllergies());

		return communityPersonInfo;
	}

	public CommunityPersonsCoveredByFireStation getPersonsCoveredByFireStation(String stationNumber) {
		
		List<String> addressesCovered = getAdressesCoveredByFirestation(stationNumber);
		List<Person> personsCovered = getPersonsInAddresses(addressesCovered);
		
		CommunityPersonsCoveredByFireStation communityPersonsCoveredByFireStation
			= new CommunityPersonsCoveredByFireStation();
		personsCovered.stream()
		.forEach(p -> communityPersonsCoveredByFireStation.addPerson(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone(),getPersonAge(p)));
		
		return communityPersonsCoveredByFireStation;
	}
	
	private int getPersonAge(Person p) {
		String birthDate = medicalRecordDao.getOne(p.getFirstName()+p.getLastName()).getBirthdate();
		int age = Integer.valueOf(this.getAgeFromBirthDate(birthDate));
		return age;
	}

	private List<Person> getPersonsInAddresses(List<String> addressesCovered) {
		return personDao.getAll().stream()
				.filter(p -> addressesCovered.contains(p.getAddress()))
				.collect(Collectors.toList());
	}

	private List<String> getAdressesCoveredByFirestation(String stationNumber) {
		return linkedFireStationDao.getAll().stream()
				.filter(ad -> ad.getStation().equals(stationNumber))
				.map(LinkedFireStation::getAddress)
				.collect(Collectors.toList());
	}

	public String getAgeFromBirthDate(String birthDate) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate birthDateToDate = LocalDate.parse(birthDate, formatter);
		String age = String.valueOf(Period.between(birthDateToDate, LocalDate.now()).getYears());
		
		return age;
	}
	
}
