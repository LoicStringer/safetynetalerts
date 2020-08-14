package com.safetynet.safetynetalerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.exceptions.LinkedFireStationNotFoundException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonInfo;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonsCoveredByFireStation;

@Service
public class CommunityService {

	
	@Autowired
	private PersonService personService;

	@Autowired
	private LinkedFireStationService linkedFireStationService;

	@Autowired
	private MedicalRecordService medicalRecordService;

	/**
	 * Retrieves a emails list of the specified city inhabitants mapped from a
	 * Person database filtered stream by the city's name provided.
	 * 
	 * @param city's name as a String.
	 * @return a {@link List} of String.
	 * @throws PersonsDataNotFoundException
	 */
	public List<String> getCommunityEmails(String city) throws PersonsDataNotFoundException {
		
		if(!personService.getAllPersons().stream().anyMatch(p -> p.getCity().equalsIgnoreCase(city)))
			throw new PersonsDataNotFoundException("This city "+city+" is not registered in persons data");
	
		List<String> communityEmails = new ArrayList<String>();

		communityEmails = personService.getAllPersons().stream().filter(p -> p.getCity().equalsIgnoreCase(city))
				.map(p -> p.getEmail()).collect(Collectors.toList());

		return communityEmails;
	}

	public CommunityPersonInfo getPersonInfo(String identifier) throws PersonNotFoundException,
			PersonsDataNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException {

		CommunityPersonInfo communityPersonInfo = new CommunityPersonInfo();

		Person personToGetInfoFrom = personService.getOnePerson(identifier);
		MedicalRecord medicalRecordToGetInfoFrom = medicalRecordService.getOneMedicalRecord(identifier);

		communityPersonInfo.setFirstName(personToGetInfoFrom.getFirstName());
		communityPersonInfo.setLastName(personToGetInfoFrom.getLastName());
		communityPersonInfo.setAge(this.getAgeFromBirthDate(medicalRecordToGetInfoFrom.getBirthdate()));
		communityPersonInfo.setAddress(personToGetInfoFrom.getAddress());
		communityPersonInfo.setEmail(personToGetInfoFrom.getEmail());
		communityPersonInfo.setMedications(medicalRecordToGetInfoFrom.getMedications());
		communityPersonInfo.setAllergies(medicalRecordToGetInfoFrom.getAllergies());

		return communityPersonInfo;
	}

	public CommunityPersonsCoveredByFireStation getPersonsCoveredByFireStation(String stationNumber)
			throws LinkedFireStationNotFoundException, PersonsDataNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException, LinkedFireStationsDataNotFoundException {

		if(!linkedFireStationService.getAllLinkedFireStations().stream().anyMatch(lfs -> lfs.getStation().equalsIgnoreCase(stationNumber)))
			throw new LinkedFireStationNotFoundException("This station number "+ stationNumber +" is not registered in fire station mappings data");
		
		CommunityPersonsCoveredByFireStation communityPersonsCoveredByFireStation = new CommunityPersonsCoveredByFireStation();

		List<String> addressesCovered = getAdressesCoveredByFirestation(stationNumber);
		List<Person> personsCovered = getPersonsThere(addressesCovered);

		for(Person coveredPerson : personsCovered) 
			communityPersonsCoveredByFireStation.addCoveredPerson(coveredPerson.getFirstName(), coveredPerson.getLastName(), coveredPerson.getAddress(),
					coveredPerson.getPhone(), getPersonAge(coveredPerson));
		
		/*
		personsCovered.stream().forEach(p -> {
			communityPersonsCoveredByFireStation.addCoveredPerson(p.getFirstName(), p.getLastName(), p.getAddress(),
					p.getPhone(), getPersonAge(p));
		});
		*/
		return communityPersonsCoveredByFireStation;
	}

	private int getPersonAge(Person p) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException {

		String birthDate = new String();

		birthDate = medicalRecordService.getOneMedicalRecord(p.getFirstName() + p.getLastName()).getBirthdate();

		return Integer.valueOf(this.getAgeFromBirthDate(birthDate));
	}

	private List<Person> getPersonsThere(List<String> addressesCovered) throws PersonsDataNotFoundException {
		return personService.getAllPersons().stream().filter(p -> addressesCovered.contains(p.getAddress()))
				.collect(Collectors.toList());
	}

	private List<String> getAdressesCoveredByFirestation(String stationNumber)
			throws LinkedFireStationsDataNotFoundException {
		return linkedFireStationService.getAllLinkedFireStations().stream()
				.filter(ad -> ad.getStation().equalsIgnoreCase(stationNumber)).map(LinkedFireStation::getAddress)
				.collect(Collectors.toList());
	}

	private int getAgeFromBirthDate(String birthDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate birthDateToDate = LocalDate.parse(birthDate, formatter);
		return Period.between(birthDateToDate, LocalDate.now()).getYears();
	}

}
