package com.safetynet.safetynetalerts.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.responseentity.EmergencyChildAlert;
import com.safetynet.safetynetalerts.responseentity.EmergencyFireAddressInfos;
import com.safetynet.safetynetalerts.responseentity.EmergencyFloodInfos;



@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
class EmergencyServiceTest {

	
	@Mock
	private PersonDao personDao ;
	
	@Mock
	private MedicalRecordDao medicalRecordDao;
	
	@Mock
	private LinkedFireStationDao linkedFireStationDao;
	
	@InjectMocks
	private EmergencyService emergencyService;
	
	@BeforeEach
	void  setUp() {
		List<Person> persons = new ArrayList<Person>();
		
		persons.add(new Person("John","Boyd","1509 Culver St","Culver","97541","841-874-6512","jaboyd@email.com"));
		persons.add(new Person("Jacob","Boyd","1509 Culver St","Culver","97541","841-874-6513","drk@email.com"));
		persons.add(new Person("Tenley","Boyd","1509 Culver St","Culver","97541","841-874-6512","tenz@email.com"));
		persons.add(new Person("Roger","Boyd","1509 Culver St","Culver","97541","841-874-6512","jaboyd@email.com"));
		persons.add(new Person("Felicia","Boyd","1509 Culver St","Culver","97541","841-874-6544","jaboyd@email.com"));
		persons.add(new Person("Sophia","Zemicks","892 Downing Ct","Culver","97541","841-874-7878","soph@email.com"));
		persons.add(new Person("Warren","Zemicks","892 Downing Ct","Culver","97541","841-874-7512","ward@email.com"));
		persons.add(new Person("Zach","Zemicks","892 Downing Ct","Culver","97541","841-874-7512","zarc@email.com"));
		
		when(personDao.getAll()).thenReturn(persons);
	}
	
	@Test
	void getChildrenThereTest() {
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		
		medicalRecords.add(new MedicalRecord("John","Boyd","03/06/1984",new String[]{"aznol:350mg", "hydrapermazol:100mg"},new String[]{"nillacilan"}));
		medicalRecords.add(new MedicalRecord("Jacob","Boyd","03/06/1989",new String[]{"pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"},new String[]{""}));
		medicalRecords.add(new MedicalRecord("Tenley","Boyd","02/18/2012",new String[]{""},new String[]{"peanut"}));
		medicalRecords.add(new MedicalRecord("Roger","Boyd","09/06/2017",new String[]{""},new String[]{""}));
		medicalRecords.add(new MedicalRecord("Felicia","Boyd","01/08/1986",new String[]{"tetracyclaz:650mg"},new String[]{"xilliathal"}));
		medicalRecords.add(new MedicalRecord("Sophia","Zemicks","03/06/1988",new String[]{"aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"},new String[]{"peanut", "shellfish", "aznol"}));
		medicalRecords.add(new MedicalRecord("Warren","Zemicks","03/06/1985",new String[]{""},new String[]{""}));
		medicalRecords.add(new MedicalRecord("Zach","Zemicks","03/06/2017",new String[]{""},new String[]{""}));
		
		when(medicalRecordDao.getOne("JohnBoyd")).thenReturn(medicalRecords.get(0));
		when(medicalRecordDao.getOne("JacobBoyd")).thenReturn(medicalRecords.get(1));
		when(medicalRecordDao.getOne("TenleyBoyd")).thenReturn(medicalRecords.get(2));
		when(medicalRecordDao.getOne("RogerBoyd")).thenReturn(medicalRecords.get(3));
		when(medicalRecordDao.getOne("FeliciaBoyd")).thenReturn(medicalRecords.get(4));
		when(medicalRecordDao.getOne("SophiaZemicks")).thenReturn(medicalRecords.get(5));
		when(medicalRecordDao.getOne("WarrenZemicks")).thenReturn(medicalRecords.get(6));
		when(medicalRecordDao.getOne("ZachZemicks")).thenReturn(medicalRecords.get(7));
		
		EmergencyChildAlert emergencyChildAlert = emergencyService.getChildrenThere("1509 Culver St");
		EmergencyChildAlert emergencyChildAlert2 = emergencyService.getChildrenThere("892 Downing Ct");
		
		assertEquals(emergencyChildAlert.getChildrenThere().size(),2);
		assertEquals(emergencyChildAlert.getOtherPersonsThere().size(), 3);
		assertEquals(emergencyChildAlert.getChildrenThere().get(0).getFirstName(),"Tenley");
		assertEquals(emergencyChildAlert2.getChildrenThere().size(), 1);
		assertEquals(emergencyChildAlert2.getOtherPersonsThere().size(), 2);	
	}
	
	@Test
	void getCoveredPersonsPhoneNumberTest() {
		
		List<LinkedFireStation> linkedFireStations = new ArrayList<LinkedFireStation>();
		
		linkedFireStations.add(new LinkedFireStation("1509 Culver St","3"));
		linkedFireStations.add(new LinkedFireStation("892 Downing Ct","2"));
		
		when(linkedFireStationDao.getAll()).thenReturn(linkedFireStations);
		
		List<String> phoneNumbers = emergencyService.getCoveredPersonsPhoneNumbers("3");
		
		assertEquals(phoneNumbers.size(), 5 );
		assertEquals(phoneNumbers.get(0),"841-874-6512");
	}
	
	@Test
	void getPersonsThereTest() {
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		
		medicalRecords.add(new MedicalRecord("John","Boyd","03/06/1984",new String[]{"aznol:350mg", "hydrapermazol:100mg"},new String[]{"nillacilan"}));
		medicalRecords.add(new MedicalRecord("Jacob","Boyd","03/06/1989",new String[]{"pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"},new String[]{""}));
		medicalRecords.add(new MedicalRecord("Tenley","Boyd","02/18/2012",new String[]{""},new String[]{"peanut"}));
		medicalRecords.add(new MedicalRecord("Roger","Boyd","09/06/2017",new String[]{""},new String[]{""}));
		medicalRecords.add(new MedicalRecord("Felicia","Boyd","01/08/1986",new String[]{"tetracyclaz:650mg"},new String[]{"xilliathal"}));
		medicalRecords.add(new MedicalRecord("Sophia","Zemicks","03/06/1988",new String[]{"aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"},new String[]{"peanut", "shellfish", "aznol"}));
		medicalRecords.add(new MedicalRecord("Warren","Zemicks","03/06/1985",new String[]{""},new String[]{""}));
		medicalRecords.add(new MedicalRecord("Zach","Zemicks","03/06/2017",new String[]{""},new String[]{""}));
		
		when(medicalRecordDao.getAll()).thenReturn(medicalRecords);
		
		List<LinkedFireStation> linkedFireStations = new ArrayList<LinkedFireStation>();
		
		linkedFireStations.add(new LinkedFireStation("1509 Culver St","3"));
		linkedFireStations.add(new LinkedFireStation("892 Downing Ct","2"));
		
		when(linkedFireStationDao.getAll()).thenReturn(linkedFireStations);
		EmergencyFireAddressInfos emergencyFireAddressInfos = emergencyService.getPersonsThereInfos("1509 Culver St");
		EmergencyFireAddressInfos emergencyFireAddressInfos2 = emergencyService.getPersonsThereInfos("892 Downing Ct");
		
		assertEquals(emergencyFireAddressInfos.getInhabitantsThere().size(), 5);
		assertEquals(emergencyFireAddressInfos.getStationNumber(), "3");
		assertEquals(emergencyFireAddressInfos2.getInhabitantsThere().size(), 3);
		assertEquals(emergencyFireAddressInfos2.getStationNumber(), "2");
	}
	
	@Test
	void getCoveredHomesInfosTest() {
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		
		medicalRecords.add(new MedicalRecord("John","Boyd","03/06/1984",new String[]{"aznol:350mg", "hydrapermazol:100mg"},new String[]{"nillacilan"}));
		medicalRecords.add(new MedicalRecord("Jacob","Boyd","03/06/1989",new String[]{"pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"},new String[]{""}));
		medicalRecords.add(new MedicalRecord("Tenley","Boyd","02/18/2012",new String[]{""},new String[]{"peanut"}));
		medicalRecords.add(new MedicalRecord("Roger","Boyd","09/06/2017",new String[]{""},new String[]{""}));
		medicalRecords.add(new MedicalRecord("Felicia","Boyd","01/08/1986",new String[]{"tetracyclaz:650mg"},new String[]{"xilliathal"}));
		medicalRecords.add(new MedicalRecord("Sophia","Zemicks","03/06/1988",new String[]{"aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"},new String[]{"peanut", "shellfish", "aznol"}));
		medicalRecords.add(new MedicalRecord("Warren","Zemicks","03/06/1985",new String[]{""},new String[]{""}));
		medicalRecords.add(new MedicalRecord("Zach","Zemicks","03/06/2017",new String[]{""},new String[]{""}));
		
		when(medicalRecordDao.getAll()).thenReturn(medicalRecords);
		List<LinkedFireStation> linkedFireStations = new ArrayList<LinkedFireStation>();
		
		linkedFireStations.add(new LinkedFireStation("1509 Culver St","3"));
		linkedFireStations.add(new LinkedFireStation("892 Downing Ct","2"));
		
		when(linkedFireStationDao.getAll()).thenReturn(linkedFireStations);
		List<String> stationNumbers = new ArrayList<String>();
		stationNumbers.add("2");
		stationNumbers.add("3");
		EmergencyFloodInfos emergencyFloodInfos = emergencyService.getCoveredHomesInfos(stationNumbers);
		assertEquals(emergencyFloodInfos.getCoveredHomesList().size(), 2);
		assertEquals(emergencyFloodInfos.getCoveredHomesList().get(0).getStationNumber(), "2");
		assertEquals(emergencyFloodInfos.getCoveredHomesList().get(0).getHomesInfos().get(0).getInhabitantsThere().size(), 3);
		
	}
	
}
