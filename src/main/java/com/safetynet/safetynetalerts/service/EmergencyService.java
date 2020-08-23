package com.safetynet.safetynetalerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.exceptions.LinkedFireStationNotFoundException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.responseentity.EmergencyChildAlert;
import com.safetynet.safetynetalerts.responseentity.EmergencyFireAddressInfos;
import com.safetynet.safetynetalerts.responseentity.EmergencyFloodInfos;
import com.safetynet.safetynetalerts.responseentity.EmergencyFloodInfos.HomeInfo;
import com.safetynet.safetynetalerts.responseentity.InhabitantInfos;

@Service
public class EmergencyService {
	
	private Logger log =LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersonService personService;

	@Autowired
	private LinkedFireStationService linkedFireStationService;

	@Autowired
	private MedicalRecordService medicalRecordService;

	public EmergencyChildAlert getChildrenThere(String address)
			throws PersonsDataNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException {

		EmergencyChildAlert childrenThere = new EmergencyChildAlert();

		List<Person> personsThere = getPersonsThere(address);

		for (Person person : personsThere)
			childrenThere.addPerson(person.getFirstName(), person.getLastName(), getPersonAge(person));

		log.debug(System.lineSeparator()+"Retrieving for the specified address "+address
				+ System.lineSeparator()+", the number of children and other persons living there, and their infos");
		
		return childrenThere;
	}

	public List<String> getCoveredPersonsPhoneNumbers(String stationNumber) throws PersonsDataNotFoundException,
			LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException {

		List<String> addressesCovered = getAdressesCoveredByFirestation(stationNumber);

		log.debug(System.lineSeparator()+"Retrieving persons covered by the specified fire station "+stationNumber+" phone numbers list.");
		
		return personService.getAllPersons().stream().filter(p -> addressesCovered.contains(p.getAddress()))
				.map(Person::getPhone).collect(Collectors.toList());
	}

	public EmergencyFireAddressInfos getPersonsThereInfos(String address)
			throws LinkedFireStationsDataNotFoundException, PersonsDataNotFoundException,
			MedicalRecordsDataNotFoundException, LinkedFireStationNotFoundException {

		EmergencyFireAddressInfos emergencyFireAddressInfos = new EmergencyFireAddressInfos();

		emergencyFireAddressInfos.setStationNumber(getStationNumberCoveringAddress(address));

		List<Person> personsThere = getPersonsThere(address);
		List<String> identifiers = getPersonIdentifiers(personsThere);
		List<InhabitantInfos> inhabitantsThere = getInhabitantInfos(identifiers);

		emergencyFireAddressInfos.setInhabitantsThere(inhabitantsThere);

		log.debug(System.lineSeparator()+"Retrieving for the specified address "+address
				+ System.lineSeparator()+" the station number that covers it and their inhabitants infos list.");
		
		return emergencyFireAddressInfos;
	}

	public EmergencyFloodInfos getCoveredHomesInfos(List<String> stationNumbersList)
			throws MedicalRecordsDataNotFoundException, PersonsDataNotFoundException,
			LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException {

		EmergencyFloodInfos emergencyFloodInfos = new EmergencyFloodInfos();

		for (String stationNumber : stationNumbersList) {
			emergencyFloodInfos.addStationInfos(stationNumber,
					getHomesInfos(getAdressesCoveredByFirestation(stationNumber)));
		}

		log.debug(System.lineSeparator()+"Retrieving the whole homes inhabitants detailed infos list "
				+ System.lineSeparator()+"covered by fire stations corresponding to the specified list of station numbers "+stationNumbersList+" .");
		
		return emergencyFloodInfos;
	}

	private List<Person> getPersonsThere(String address) throws PersonsDataNotFoundException {

		List<Person> persons = personService.getAllPersons();
		List<Person> personsThere = persons.stream().filter(p -> p.getAddress().equalsIgnoreCase(address))
				.collect(Collectors.toList());

		if (personsThere.isEmpty())
			throw new PersonsDataNotFoundException("This address " + address + " is not registered");

		return personsThere;
	}

	private int getPersonAge(Person p) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException {

		String birthDate = medicalRecordService.getOneMedicalRecord(p.getFirstName() + p.getLastName()).getBirthdate();

		return this.getAgeFromBirthDate(birthDate);
	}

	private List<String> getAdressesCoveredByFirestation(String stationNumber)
			throws LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException {

		List<String> addressesCovered = linkedFireStationService.getAllLinkedFireStations().stream()
				.filter(ad -> ad.getStation().equalsIgnoreCase(stationNumber)).map(LinkedFireStation::getAddress)
				.collect(Collectors.toList());

		if (addressesCovered.isEmpty())
			throw new LinkedFireStationNotFoundException("This station number " + stationNumber + " is not registered");

		return addressesCovered;
	}

	private String getStationNumberCoveringAddress(String address)
			throws LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException {

		String stationNumber = linkedFireStationService.getAllLinkedFireStations().stream()
				.filter(lfs -> lfs.getAddress().equalsIgnoreCase(address)).map(lfs -> lfs.getStation()).findAny()
				.orElse(null);

		if (stationNumber == null)
			throw new LinkedFireStationNotFoundException("There's no fire station mapping for this address " + address);

		return stationNumber;
	}

	private String getPhoneNumber(String identifier) throws PersonsDataNotFoundException {

		return personService.getAllPersons().stream()
				.filter(p -> (p.getFirstName() + p.getLastName()).equalsIgnoreCase(identifier)).map(p -> p.getPhone())
				.findAny().orElse(null);
	}

	private List<String> getPersonIdentifiers(List<Person> personsThere) {
		return personsThere.stream().map(p -> p.getFirstName() + p.getLastName()).collect(Collectors.toList());
	}

	private List<InhabitantInfos> getInhabitantInfos(List<String> identifiers)
			throws MedicalRecordsDataNotFoundException, PersonsDataNotFoundException {

		List<InhabitantInfos> inhabitantInfos = new ArrayList<InhabitantInfos>();

		List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();

		for (MedicalRecord medicalRecord : medicalRecords) {
			if (identifiers.contains(medicalRecord.getFirstName() + medicalRecord.getLastName())) {
				InhabitantInfos inhabitantThere = new InhabitantInfos();
				inhabitantThere.setFirstName(medicalRecord.getFirstName());
				inhabitantThere.setLastName(medicalRecord.getLastName());
				inhabitantThere.setAge(this.getAgeFromBirthDate(medicalRecord.getBirthdate()));
				inhabitantThere
						.setPhoneNumber(getPhoneNumber(medicalRecord.getFirstName() + medicalRecord.getLastName()));
				inhabitantThere.setMedications(medicalRecord.getMedications());
				inhabitantThere.setAllergies(medicalRecord.getAllergies());
				inhabitantInfos.add(inhabitantThere);
			}

		}

		return inhabitantInfos;
	}

	private List<HomeInfo> getHomesInfos(List<String> addresses)
			throws MedicalRecordsDataNotFoundException, PersonsDataNotFoundException {

		List<HomeInfo> homesInfos = new ArrayList<HomeInfo>();

		for (String address : addresses) {
			HomeInfo homeInfo = new EmergencyFloodInfos().new HomeInfo();
			homeInfo.setAddress(address);
			homeInfo.setInhabitantsThere(getInhabitantInfos(getPersonIdentifiers(getPersonsThere(address))));
			homesInfos.add(homeInfo);
		}

		return homesInfos;
	}

	private int getAgeFromBirthDate(String birthDate) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		if(birthDate==null||birthDate.isBlank())
			birthDate = LocalDate.now().format(formatter);
		
		LocalDate birthDateToDate = LocalDate.parse(birthDate, formatter);
		return Period.between(birthDateToDate, LocalDate.now()).getYears();
	}
}
