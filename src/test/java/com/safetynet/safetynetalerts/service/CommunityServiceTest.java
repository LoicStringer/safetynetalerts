package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonInfo;
import com.safetynet.safetynetalerts.responseentity.CommunityPersonsCoveredByFireStation;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
class CommunityServiceTest {

	@Mock
	private PersonDao personDao;

	@Mock
	private MedicalRecordDao medicalRecordDao;

	@Mock
	private LinkedFireStationDao linkedFireStationDao;

	@InjectMocks
	private CommunityService communityService;

	private List<Person> persons;

	@BeforeEach
	void setUp() {
		persons = new ArrayList<Person>();

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

	}

	@Test
	void getCommunityEmailsTest() {
		when(personDao.getAll()).thenReturn(persons);
		List<String> emails = communityService.getCommunityEmails("Culver");

		assertEquals(emails.size(), 8);
		assertEquals(emails.get(0), "jaboyd@email.com");
	}

	@Test
	void getPersonInfoTest() {
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();

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

		when(medicalRecordDao.getOne("JohnBoyd")).thenReturn(medicalRecords.get(0));

		when(personDao.getOne("JohnBoyd")).thenReturn(persons.get(0));

		CommunityPersonInfo communityPersonInfo = communityService.getPersonInfo("JohnBoyd");
		assertEquals(communityPersonInfo.getFirstName(), "John");

	}

	@Test
	void getPersonscoveredByFireStationsTest() {
		when(personDao.getAll()).thenReturn(persons);
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		List<LinkedFireStation> linkedFireStations = new ArrayList<LinkedFireStation>();
		
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

		when(linkedFireStationDao.getAll()).thenReturn(linkedFireStations);
		when(medicalRecordDao.getOne("JohnBoyd")).thenReturn(medicalRecords.get(0));
		when(medicalRecordDao.getOne("JacobBoyd")).thenReturn(medicalRecords.get(1));
		when(medicalRecordDao.getOne("TenleyBoyd")).thenReturn(medicalRecords.get(2));
		when(medicalRecordDao.getOne("RogerBoyd")).thenReturn(medicalRecords.get(3));
		when(medicalRecordDao.getOne("FeliciaBoyd")).thenReturn(medicalRecords.get(4));
		when(medicalRecordDao.getOne("SophiaZemicks")).thenReturn(medicalRecords.get(5));
		when(medicalRecordDao.getOne("WarrenZemicks")).thenReturn(medicalRecords.get(6));
		when(medicalRecordDao.getOne("ZachZemicks")).thenReturn(medicalRecords.get(7));
		
		CommunityPersonsCoveredByFireStation communityPersonsCoveredByFireStation = 
				communityService.getPersonsCoveredByFireStation("3");
		CommunityPersonsCoveredByFireStation communityPersonsCoveredByFireStation2 = 
				communityService.getPersonsCoveredByFireStation("2");
		
		assertEquals(communityPersonsCoveredByFireStation.getAdultCount(), 3);
		assertEquals(communityPersonsCoveredByFireStation2.getAdultCount(), 2);
	}

}
