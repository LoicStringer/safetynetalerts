package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.MedicalRecord;


@Tag("MedicalRecordDaoTests")
@DisplayName("MedicalRecordDao CRUD operations tests")
class MedicalRecordDaoTest {

	MedicalRecordDao medicalRecordDao;
	MedicalRecord medicalRecordForTests;
	List<MedicalRecord> medicalRecords;

	@BeforeEach
	void setUp() {
		
		medicalRecordDao = new MedicalRecordDao();
		medicalRecordForTests = new MedicalRecord();
		medicalRecords = new ArrayList<MedicalRecord>();
		medicalRecordForTests.setFirstName("John");
		medicalRecordForTests.setLastName("Boyd");
		
	}

	@Test
	void getAllTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException  {
		
		medicalRecords = medicalRecordDao.getAll();
		
		assertEquals(23, medicalRecords.size());
	}

	@Test
	void getOneTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException  {
		
		MedicalRecord medicalRecordToGet = medicalRecordDao.getOne("JohnBoyd");
		
		assertEquals("JohnBoyd",medicalRecordToGet.getFirstName()+medicalRecordToGet.getLastName());
		assertEquals(medicalRecordToGet.getBirthdate(), "03/06/1984");
	}
	
	@Test
	void insertTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, DuplicatedItemException  {
		
		MedicalRecord medicalRecordToInsert = new MedicalRecord(); 
		medicalRecordToInsert.setFirstName("Newbie");
		medicalRecordToInsert.setLastName("Noob");
		
		medicalRecordDao.insert(medicalRecordToInsert);
		medicalRecords = medicalRecordDao.getAll();
		
		assertEquals("Newbie",medicalRecords.get(medicalRecords.size()-1).getFirstName());
	}
	
	@Test
	void updateTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException  {
		
		medicalRecordForTests.setBirthdate("04/01/1978");
		medicalRecordDao.update(medicalRecordForTests);
		medicalRecords = medicalRecordDao.getAll();
		
		assertEquals("04/01/1978", medicalRecords.get(0).getBirthdate());
	}
	
	@Test
	void deleteTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException  {
		
		medicalRecordDao.delete(medicalRecordForTests);
		medicalRecords = medicalRecordDao.getAll();
		
		assertNotEquals("John", medicalRecords.get(0).getFirstName());
	}
	
	@Test
	void isThrowingExceptionWhenInsertingDuplicatedIdentifierMedicalRecordTest() {
		
		Exception exception = assertThrows(DuplicatedItemException.class, ()->medicalRecordDao.insert(medicalRecordForTests));
		
		assertEquals(exception.getMessage(),"Warning : a medical record with the same firstname and lastname already exists in data container");
	}
	
	@Test
	void isThrowingExceptionWhenMedicalRecordIsNotFoundTest()  {
	
		Exception exception = assertThrows(ItemNotFoundException.class,()->medicalRecordDao.getOne("Toto"));
		
		assertEquals(exception.getMessage(),"No medical record found for identifier Toto");
	}
	

}
