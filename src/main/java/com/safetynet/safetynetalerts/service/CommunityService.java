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
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonInfo;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonInfo.PersonInfo;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonsCoveredByFireStation;

/**
 * <p>This class includes methods related to the {@link CommunityController}.
 * Each public method matches an URL, filters and retrieves informations 
 * in a specific "response" object or a simple list.</p>
 * @author newbie
 *
 */
@Service
public class CommunityService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

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

		if (!personService.getAllPersons().stream().anyMatch(p -> p.getCity().equalsIgnoreCase(city)))
			throw new PersonsDataNotFoundException("This city " + city + " is not registered in persons data");

		List<String> communityEmails = new ArrayList<String>();

		communityEmails = personService.getAllPersons().stream().filter(p -> p.getCity().equalsIgnoreCase(city))
				.map(p -> p.getEmail()).collect(Collectors.toList());

		log.debug(System.lineSeparator() + "Checking if city " + city
				+ " exists, filtering through persons data, collecting matching ones.");

		return communityEmails;
	}

	/**
	 * <p>For a given first name and last name, returns a specific {@link CommunityPersonInfo}
	 * containing the person required informations, including medical ones, 
	 * by filtering persons and medical records data.
	 * If several persons got the same name, they all appear.</p>
	 * @param identifier
	 * @return CommunityPersonInfo
	 * @throws PersonNotFoundException
	 * @throws PersonsDataNotFoundException
	 * @throws MedicalRecordsDataNotFoundException
	 * @throws MedicalRecordNotFoundException
	 */
	public CommunityPersonInfo getPersonInfo(String identifier) throws PersonNotFoundException,
			PersonsDataNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException {

		CommunityPersonInfo communityPersonInfo = new CommunityPersonInfo();

		List<Person> personsToGetInfoFrom = personService.getHomonymousPersons(identifier);
		List<MedicalRecord> medicalRecordsToGetInfoFrom = medicalRecordService.getHomonymousMedicalRecords(identifier);
		PersonInfo personInfo = new CommunityPersonInfo().new PersonInfo();

		for (Person person : personsToGetInfoFrom) {
			personInfo.setFirstName(person.getFirstName());
			personInfo.setLastName(person.getLastName());
			for (MedicalRecord medicalRecord : medicalRecordsToGetInfoFrom) {
				if ((personInfo.getFirstName() + personInfo.getLastName())
						.equalsIgnoreCase((medicalRecord.getFirstName() + medicalRecord.getLastName()))) {
					personInfo.setAge(this.getAgeFromBirthDate(medicalRecord.getBirthdate()));
					personInfo.setAddress(person.getAddress());
					personInfo.setEmail(person.getEmail());
					personInfo.setMedications(medicalRecord.getMedications());
					personInfo.setAllergies(medicalRecord.getAllergies());
				}
			}
			communityPersonInfo.addPersonInfo(personInfo);
		}

		log.debug(System.lineSeparator()
				+ "Calling the person dao \"getHomonymousPersons\" method, that retrieves the persons list matching with the specified name, "
				+ System.lineSeparator()
				+ "collecting infos by filtering through medical records data, building a specific CommunityPersonInfo object");

		return communityPersonInfo;
	}

	/**
	 * <p>Returns, in a specific {@link CommunityPersonsCoveredByFireStation} object,
	 * the persons list covered by the specified fire station, 
	 * their informations, and the number of children and adults.</p>
	 * <p>The {@link #getAdressesCoveredByFirestation(String)} method retrieves addresses covered
	 * by the station number and the {@link #getPersonsThere(List)} method builds 
	 * the persons living there list, filtering the addresses through persons data.</p>
	 * @param stationNumber
	 * @return CommunityPersonsCoveredByFireStation
	 * @throws LinkedFireStationNotFoundException
	 * @throws PersonsDataNotFoundException
	 * @throws MedicalRecordsDataNotFoundException
	 * @throws MedicalRecordNotFoundException
	 * @throws LinkedFireStationsDataNotFoundException
	 */
	public CommunityPersonsCoveredByFireStation getPersonsCoveredByFireStation(String stationNumber)
			throws LinkedFireStationNotFoundException, PersonsDataNotFoundException,
			MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException,
			LinkedFireStationsDataNotFoundException {

		if (!linkedFireStationService.getAllLinkedFireStations().stream()
				.anyMatch(lfs -> lfs.getStation().equalsIgnoreCase(stationNumber)))
			throw new LinkedFireStationNotFoundException(
					"This station number " + stationNumber + " is not registered in fire station mappings data");

		CommunityPersonsCoveredByFireStation communityPersonsCoveredByFireStation = new CommunityPersonsCoveredByFireStation();

		List<String> addressesCovered = getAdressesCoveredByFirestation(stationNumber);
		List<Person> personsCovered = getPersonsThere(addressesCovered);

		for (Person coveredPerson : personsCovered)
			communityPersonsCoveredByFireStation.addCoveredPerson(coveredPerson.getFirstName(),
					coveredPerson.getLastName(), coveredPerson.getAddress(), coveredPerson.getPhone(),
					getPersonAge(coveredPerson));

		log.debug(System.lineSeparator() + "Checking if station number " + stationNumber + " exists, "
				+ System.lineSeparator()
				+ "retrieving a specific CommunityPersonsCoveredByFireStation object conataining persons infos covered by this fire station.");

		return communityPersonsCoveredByFireStation;
	}

	private int getPersonAge(Person p) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException {

		String birthDate = medicalRecordService.getOneMedicalRecord(p.getFirstName() + p.getLastName()).getBirthdate();

		return this.getAgeFromBirthDate(birthDate);
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
		
		if(birthDate==null||birthDate.isBlank())
			birthDate = LocalDate.now().format(formatter);
		
		LocalDate birthDateToDate = LocalDate.parse(birthDate, formatter);
		return Period.between(birthDateToDate, LocalDate.now()).getYears();
	}

}
