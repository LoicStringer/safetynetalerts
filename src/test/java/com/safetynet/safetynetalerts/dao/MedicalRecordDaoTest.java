package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalerts.data.DataContainer;
import com.safetynet.safetynetalerts.exceptions.DuplicatedMedicalRecordException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
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
		DataContainer.reloadDataForTests();
	}

	@Nested
	@Tag("OperationsTests")
	@DisplayName("Operations checking")
	class OperationsTests {
		
		@Test
		void getAllTest() throws UnavailableDataException, EmptyDataException  {
			
			medicalRecords = medicalRecordDao.getAll();
			
			assertNotNull(medicalRecords);
			assertEquals(23, medicalRecords.size());
		}

		@Test
		void getOneTest() throws UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException  {
			
			MedicalRecord medicalRecordToGet = medicalRecordDao.getOne("FosterShepard");
			
			assertEquals(medicalRecordToGet.getFirstName()+medicalRecordToGet.getLastName(),"FosterShepard");
			assertEquals(medicalRecordToGet.getBirthdate(), "01/08/1980");
		}
		
		@Test
		void getDuplicatedMedicalRecordsTest() throws UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException {
			
			MedicalRecord medicalRecordToInsert = new MedicalRecord();
			medicalRecordToInsert.setFirstName("John");
			medicalRecordToInsert.setLastName("Boyd");
			
			medicalRecordDao.insert(medicalRecordToInsert);
			List<MedicalRecord> duplicatedMedicalRecords = medicalRecordDao.getDuplicatedMedicalRecords("JohnBoyd");
			
			assertEquals(duplicatedMedicalRecords.size(),2);
			assertEquals(duplicatedMedicalRecords.get(0).getFirstName(),duplicatedMedicalRecords.get(1).getFirstName());
		}
		
		@Test
		void insertTest() throws UnavailableDataException, EmptyDataException {
			
			MedicalRecord medicalRecordToInsert = new MedicalRecord(); 
			medicalRecordToInsert.setFirstName("Newbie");
			medicalRecordToInsert.setLastName("Noob");
			
			medicalRecordDao.insert(medicalRecordToInsert);
			medicalRecords = medicalRecordDao.getAll();
			
			assertEquals(medicalRecords.get(medicalRecords.size()-1).getFirstName(),"Newbie");
		}
		
		@Test
		void updateTest() throws UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException, DuplicatedMedicalRecordException  {
			
			medicalRecords = medicalRecordDao.getAll();
			MedicalRecord medicalRecordToUpdate = medicalRecords.get(0);
			
			medicalRecordToUpdate.setBirthdate("04/01/1978");
			medicalRecordDao.update(medicalRecordToUpdate);
			medicalRecords = medicalRecordDao.getAll();
			
			assertEquals(medicalRecords.get(0).getBirthdate(),"04/01/1978");
		}
		
		@Test
		void deleteTest() throws UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException, DuplicatedMedicalRecordException  {
			
			medicalRecords = medicalRecordDao.getAll();
			MedicalRecord medicalRecordToDelete = medicalRecords.get(0);
			
			medicalRecordDao.delete(medicalRecordToDelete);
			medicalRecords = medicalRecordDao.getAll();
			
			assertNotEquals(medicalRecords.get(0).getFirstName(),"John");
		}
	}
	
	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {
		
		@Test
		void isThrowingExceptionWhenMedicalRecordIsNotFoundTest()  {
		
			MedicalRecord medicalRecordToGet = new MedicalRecord();
			medicalRecordToGet.setFirstName("Toto");
			medicalRecordToGet.setLastName("Toto");
			String identifier = medicalRecordToGet.getFirstName()+" "+medicalRecordToGet.getLastName();
			
			Exception exception = assertThrows(MedicalRecordNotFoundException.class,()->medicalRecordDao.getOne(identifier));
			
			assertEquals(exception.getMessage(),"No medical record found for identifier Toto Toto");
		}
		
		@Test
		void isThrowingExceptionWhenTryingToDeleteHomonymousMedicalRecord() throws UnavailableDataException, EmptyDataException {
			
			MedicalRecord homonymousPerson = new MedicalRecord();
			homonymousPerson.setFirstName("John");
			homonymousPerson.setLastName("Boyd");
			
			medicalRecordDao.insert(homonymousPerson);
			
			assertThrows(DuplicatedMedicalRecordException.class, ()->medicalRecordDao.delete(homonymousPerson));
		}
		
		@Test
		void isThrowingExceptionWhenTryingToUpdateHomonymousMedicalRecord() throws UnavailableDataException, EmptyDataException {
			
			MedicalRecord personToUpdate = new MedicalRecord();
			personToUpdate.setFirstName("John");
			personToUpdate.setLastName("Boyd");
			
			medicalRecordDao.insert(personToUpdate);
			
			assertThrows(DuplicatedMedicalRecordException.class, ()->medicalRecordDao.update(personToUpdate));
		}
	
		@Test
		void isThrowingExceptionWhenDataSourceIsEmpty() {
			
			DataContainer.medicalRecordsData.removeAll();
			
			Exception exception = assertThrows(EmptyDataException.class,()->medicalRecordDao.getAll());
			assertEquals(exception.getMessage(),"Medical records list is empty");
		}
		
		@Test
		void isThrowingExceptionWhenDataSourceIsNull() {
			
			DataContainer.medicalRecordsData=null;
			
			Exception exception = assertThrows(UnavailableDataException.class,()->medicalRecordDao.getAll());
			assertEquals(exception.getMessage(),"Medical records list is null");
		}
	}
	
	

}
