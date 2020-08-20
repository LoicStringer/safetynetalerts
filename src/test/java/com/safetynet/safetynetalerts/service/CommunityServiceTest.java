package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;



import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
class CommunityServiceTest {

	@Mock
	private PersonService personService;

	@Mock
	private MedicalRecordService medicalRecordService;

	@Mock
	private LinkedFireStationService linkedFireStationService;

	@InjectMocks
	private CommunityService communityService;

	private static List<Person> persons;
	private static List<MedicalRecord> medicalRecords; 
	private static List<LinkedFireStation> linkedFireStations; 

	@BeforeAll
	static void setUp() {
		
		persons = new ArrayList<Person>();
		medicalRecords = new ArrayList<MedicalRecord>();
		linkedFireStations = new ArrayList<LinkedFireStation>();
	
		persons.add(
				new Person("John", "Boyd", "1509 Culver St", "Culver", "97541", "841-874-6512", "jaboyd@email.com"));
		persons.add(new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97541", "841-874-6513", "drk@email.com"));
		persons.add(
				new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97541", "841-874-6512", "tenz@email.com"));
		persons.add(
				new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97541", "841-874-6512", "jaboyd@email.com"));
		persons.add(
				new Person("Felicia", "Boyd", "1509 Culver St", "Culver", "97541", "841-874-6544", "jaboyd@email.com"));
		persons.add(
				new Person("Sophia", "Zemicks", "892 Downing Ct", "Culver", "97541", "841-874-7878", "soph@email.com"));
		persons.add(
				new Person("Warren", "Zemicks", "892 Downing Ct", "Culver", "97541", "841-874-7512", "ward@email.com"));
		persons.add(
				new Person("Zach", "Zemicks", "892 Downing Ct", "Culver", "97541", "841-874-7512", "zarc@email.com"));


		medicalRecords.add(new MedicalRecord("John", "Boyd", "03/06/1984",
				new String[] { "aznol:350mg", "hydrapermazol:100mg" }, new String[] { "nillacilan" }));
		medicalRecords.add(new MedicalRecord("Jacob", "Boyd", "03/06/1989",
				new String[] { "pharmacol:5000mg", "terazine:10mg", "noznazol:250mg" }, new String[] { "" }));
		medicalRecords
				.add(new MedicalRecord("Tenley", "Boyd", "02/18/2012", new String[] { "" }, new String[] { "peanut" }));
		medicalRecords.add(new MedicalRecord("Roger", "Boyd", "09/06/2017", new String[] { "" }, new String[] { "" }));
		medicalRecords.add(new MedicalRecord("Felicia", "Boyd", "01/08/1986", new String[] { "tetracyclaz:650mg" },
				new String[] { "xilliathal" }));
		medicalRecords.add(new MedicalRecord("Sophia", "Zemicks", "03/06/1988",
				new String[] { "aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg" },
				new String[] { "peanut", "shellfish", "aznol" }));
		medicalRecords
				.add(new MedicalRecord("Warren", "Zemicks", "03/06/1985", new String[] { "" }, new String[] { "" }));
		medicalRecords
				.add(new MedicalRecord("Zach", "Zemicks", "03/06/2017", new String[] { "" }, new String[] { "" }));
		
		linkedFireStations.add(new LinkedFireStation("1509 Culver St", "3"));
		linkedFireStations.add(new LinkedFireStation("892 Downing Ct", "2"));
		
	}

	@Test
	void getCommunityEmailsTest() throws PersonsDataNotFoundException {
		
		when(personService.getAllPersons()).thenReturn(persons);
		
		List<String> emails = communityService.getCommunityEmails("Culver");

		assertEquals(emails.size(), 8);
		assertEquals(emails.get(0), "jaboyd@email.com");
	}

	@Test
	void getPersonInfoTest() throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException, PersonNotFoundException, PersonsDataNotFoundException {
		
		when(medicalRecordService.getOneMedicalRecord("JohnBoyd")).thenReturn(medicalRecords.get(0));
		when(personService.getOnePerson("JohnBoyd")).thenReturn(persons.get(0));

		CommunityPersonInfo communityPersonInfo = communityService.getPersonInfo("JohnBoyd");
		
		assertEquals(communityPersonInfo.getPersonsInfo().get(0).getFirstName(),"John");
	}

	@Test
	void getPersonscoveredByFireStationsTest() throws PersonsDataNotFoundException, LinkedFireStationsDataNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException, LinkedFireStationNotFoundException {
		
		when(personService.getAllPersons()).thenReturn(persons);
		when(linkedFireStationService.getAllLinkedFireStations()).thenReturn(linkedFireStations);
		when(medicalRecordService.getOneMedicalRecord("JohnBoyd")).thenReturn(medicalRecords.get(0));
		when(medicalRecordService.getOneMedicalRecord("JacobBoyd")).thenReturn(medicalRecords.get(1));
		when(medicalRecordService.getOneMedicalRecord("TenleyBoyd")).thenReturn(medicalRecords.get(2));
		when(medicalRecordService.getOneMedicalRecord("RogerBoyd")).thenReturn(medicalRecords.get(3));
		when(medicalRecordService.getOneMedicalRecord("FeliciaBoyd")).thenReturn(medicalRecords.get(4));
		when(medicalRecordService.getOneMedicalRecord("SophiaZemicks")).thenReturn(medicalRecords.get(5));
		when(medicalRecordService.getOneMedicalRecord("WarrenZemicks")).thenReturn(medicalRecords.get(6));
		when(medicalRecordService.getOneMedicalRecord("ZachZemicks")).thenReturn(medicalRecords.get(7));
		
		CommunityPersonsCoveredByFireStation communityPersonsCoveredByFireStation = 
				communityService.getPersonsCoveredByFireStation("3");
		CommunityPersonsCoveredByFireStation communityPersonsCoveredByFireStation2 = 
				communityService.getPersonsCoveredByFireStation("2");
		
		assertEquals(communityPersonsCoveredByFireStation.getAdultCount(), 3);
		assertEquals(communityPersonsCoveredByFireStation2.getAdultCount(), 2);
	}

	@Test
	void isThrowingExpectedExceptionWhenCityIsNotRegisteredTest() throws PersonsDataNotFoundException {
		
		when(personService.getAllPersons()).thenReturn(persons);
		
		Exception exception = assertThrows(PersonsDataNotFoundException.class, () -> communityService.getCommunityEmails("Paris"));

		assertEquals(exception.getMessage(), "This city Paris is not registered in persons data");
	}
	
	@Test
	void isThrowingExpectedExceptionWhenStationNumberIsNotRegistered() throws LinkedFireStationsDataNotFoundException {
		 
		when(linkedFireStationService.getAllLinkedFireStations()).thenReturn(linkedFireStations);
		
		Exception exception = assertThrows(LinkedFireStationNotFoundException.class, ()-> communityService.getPersonsCoveredByFireStation("5"));
		
		assertEquals(exception.getMessage(), "This station number 5 is not registered in fire station mappings data");
	}
	
}
