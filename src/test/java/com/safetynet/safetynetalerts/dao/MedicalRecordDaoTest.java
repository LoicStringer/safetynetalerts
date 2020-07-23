package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalerts.model.MedicalRecord;


@Tag("MedicalRecordDaoTests")
@DisplayName("MedicalRecordDao CRUD operations tests")
class MedicalRecordDaoTest {

	MedicalRecordDao medicalRecordDao;
	MedicalRecord medicalRecord;
	List<MedicalRecord> medicalRecords;
	String identifier;

	@BeforeEach
	void setUp() {
		medicalRecordDao = new MedicalRecordDao();
		medicalRecord = new MedicalRecord();
		medicalRecords = new ArrayList<MedicalRecord>();
		identifier = "JohnBoyd";
	}

	@Test
	void getAllTest() {
		medicalRecords = medicalRecordDao.getAll();
		assertEquals(23, medicalRecords.size());
	}

	@Test
	void getOneTest() {
		MedicalRecord medicalRecordToGet = medicalRecordDao.getOne(identifier);
		assertEquals(identifier,medicalRecordToGet.getFirstName()+medicalRecordToGet.getLastName());
		assertEquals(medicalRecordToGet.getBirthdate(), "03/06/1984");
	}
	
	@Test
	void insertTest() {
		medicalRecord.setFirstName("Newbie");
		
		medicalRecordDao.insert(medicalRecord);
		medicalRecords = medicalRecordDao.getAll();
		
		assertEquals("Newbie",medicalRecords.get(medicalRecords.size()-1).getFirstName());
	}
	
	@Test
	void updateTest() {
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Boyd");
		
		medicalRecord.setBirthdate("04/01/1978");
		medicalRecordDao.update(medicalRecord);
		medicalRecords = medicalRecordDao.getAll();
		
		assertEquals("04/01/1978", medicalRecords.get(0).getBirthdate());
	}
	
	@Test
	void deleteTest() {
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Boyd");
		
		medicalRecordDao.delete(medicalRecord);
		medicalRecords = medicalRecordDao.getAll();
		
		assertNotEquals("John", medicalRecords.get(0).getFirstName());
	}
	

}
