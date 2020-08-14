package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
	List<MedicalRecord> medicalRecords;

	@BeforeEach
	void setUp() {
		
		medicalRecordDao = new MedicalRecordDao();
		medicalRecords = new ArrayList<MedicalRecord>();
	}

	@Test
	void getAllTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException  {
		
		medicalRecords = medicalRecordDao.getAll();
		
		assertNotNull(medicalRecords);
		assertEquals(23, medicalRecords.size());
	}

	@Test
	void getOneTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException  {
		
		MedicalRecord medicalRecordToGet = medicalRecordDao.getOne("FosterShepard");
		
		assertEquals(medicalRecordToGet.getFirstName()+medicalRecordToGet.getLastName(),"FosterShepard");
		assertEquals(medicalRecordToGet.getBirthdate(), "01/08/1980");
	}
	
	@Test
	void insertTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, DuplicatedItemException  {
		
		MedicalRecord medicalRecordToInsert = new MedicalRecord(); 
		medicalRecordToInsert.setFirstName("Newbie");
		medicalRecordToInsert.setLastName("Noob");
		
		medicalRecordDao.insert(medicalRecordToInsert);
		medicalRecords = medicalRecordDao.getAll();
		
		assertEquals(medicalRecords.get(medicalRecords.size()-1).getFirstName(),"Newbie");
	}
	
	@Test
	void updateTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException  {
		
		medicalRecords = medicalRecordDao.getAll();
		MedicalRecord medicalRecordToUpdate = medicalRecords.get(0);
		
		medicalRecordToUpdate.setBirthdate("04/01/1978");
		medicalRecordDao.update(medicalRecordToUpdate);
		medicalRecords = medicalRecordDao.getAll();
		
		assertEquals(medicalRecords.get(0).getBirthdate(),"04/01/1978");
	}
	
	@Test
	void deleteTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException  {
		
		medicalRecords = medicalRecordDao.getAll();
		MedicalRecord medicalRecordToDelete = medicalRecords.get(0);
		
		medicalRecordDao.delete(medicalRecordToDelete);
		medicalRecords = medicalRecordDao.getAll();
		
		assertNotEquals(medicalRecords.get(0).getFirstName(),"John");
	}
	
	@Test
	void isThrowingExceptionWhenInsertingDuplicatedIdentifierMedicalRecordTest() {
		

		MedicalRecord medicalRecordToInsert = new MedicalRecord();
		medicalRecordToInsert.setFirstName("Foster");
		medicalRecordToInsert.setLastName("Shepard");
		
		Exception exception = assertThrows(DuplicatedItemException.class, ()->medicalRecordDao.insert(medicalRecordToInsert));
		
		assertEquals(exception.getMessage(),"Warning : a medical record with the same firstname and lastname "+medicalRecordToInsert.getFirstName()+" "+medicalRecordToInsert.getLastName()+" already exists in data container");
	}
	
	@Test
	void isThrowingExceptionWhenMedicalRecordIsNotFoundTest()  {
	
		MedicalRecord medicalRecordToGet = new MedicalRecord();
		medicalRecordToGet.setFirstName("Toto");
		medicalRecordToGet.setLastName("Toto");
		String identifier = medicalRecordToGet.getFirstName()+" "+medicalRecordToGet.getLastName();
		
		Exception exception = assertThrows(ItemNotFoundException.class,()->medicalRecordDao.getOne(identifier));
		
		assertEquals(exception.getMessage(),"No medical record found for identifier Toto Toto");
	}
	

}
