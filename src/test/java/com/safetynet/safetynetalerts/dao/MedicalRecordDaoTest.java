package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalerts.model.MedicalRecord;

class MedicalRecordDaoTest {

	MedicalRecordDao medicalRecordDao;
	MedicalRecord medicalRecord;
	List<MedicalRecord> medicalRecords;

	@BeforeEach
	void setUp() {
		medicalRecordDao = new MedicalRecordDao();
		medicalRecord = new MedicalRecord();
		medicalRecords = new ArrayList<MedicalRecord>();
	}

	@Test
	void getAllTest() {
		medicalRecords = medicalRecordDao.getAll();
		assertEquals(23, medicalRecords.size());
	}
/*
	@Test
	void insertTest() {
		medicalRecord.setFirstName("Newbie");
		
		boolean isInserted = medicalRecordDao.insert(medicalRecord);
		medicalRecords = medicalRecordDao.getAll();
		
		assertTrue(isInserted);
		assertEquals("Newbie",medicalRecords.get(medicalRecords.size()-1).getFirstName());
	}
	
	@Test
	void updateTest() {
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Boyd");
		
		medicalRecord.setBirthdate("01/04/1978");
		boolean isUpdated = medicalRecordDao.update(medicalRecord);
		medicalRecords = medicalRecordDao.getAll();
		
		assertTrue(isUpdated);
		assertEquals("01/04/1978", medicalRecords.get(0).getBirthdate());
	}
	
	@Test
	void deleteTest() {
		medicalRecord.setFirstName("John");
		medicalRecord.setLastName("Boyd");
		
		boolean isDeleted = medicalRecordDao.delete(medicalRecord);
		medicalRecords = medicalRecordDao.getAll();
		
		assertTrue(isDeleted);
		assertNotEquals("John", medicalRecords.get(0).getFirstName());
		
	}
*/	

}
