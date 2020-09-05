package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.exceptions.DuplicatedMedicalRecordException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@DisplayName("MedicalRecordService CRUD operations tests")
@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

	@Mock
	private MedicalRecordDao medicalRecordDao;

	@InjectMocks
	private MedicalRecordService medicalRecordService;

	private static List<MedicalRecord> medicalRecords;

	@BeforeAll
	static void setUp() {
		
		medicalRecords = new ArrayList<MedicalRecord>();

		medicalRecords
				.add(new MedicalRecord("Jonathan", "Marrack", "01/03/1989", new String[] { "" }, new String[] { "" }));
		medicalRecords.add(new MedicalRecord("Sophia", "Zemicks", "03/06/1988",
				new String[] { "aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg" },
				new String[] { "peanut", "shellfish", "aznol" }));
	}

	@Nested
	@Tag("OperationsTests")
	@DisplayName("Operations checking")
	class OperationsTests {

		@Test
		void getAllTest() throws UnavailableDataException, EmptyDataException,
				MedicalRecordsDataNotFoundException {
			
			when(medicalRecordDao.getAll()).thenReturn(medicalRecords);

			List<MedicalRecord> getAllMedicalRecords = medicalRecordService.getAllMedicalRecords();

			assertEquals(getAllMedicalRecords.size(), 2);
			assertEquals(getAllMedicalRecords.get(0).getFirstName(), "Jonathan");
		}

		@Test
		void getOneTest() throws UnavailableDataException, EmptyDataException,
				MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException {
			
			when(medicalRecordDao.getOne("SophiaZemicks")).thenReturn(medicalRecords.get(1));

			MedicalRecord medicalRecordToGet = medicalRecordService.getOneMedicalRecord("SophiaZemicks");

			assertEquals(medicalRecordToGet.getLastName(), "Zemicks");
		}

		@Test
		void getHomonymousMedicalRecordsTest() throws UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException, MedicalRecordsDataNotFoundException {
			
			List<MedicalRecord> homonymousMedicalRecordsList = new ArrayList<MedicalRecord>();
			MedicalRecord homonymousMedicalRecord = new MedicalRecord();
			
			homonymousMedicalRecord.setFirstName("Sophia");
			homonymousMedicalRecord.setLastName("Zemicks");
			homonymousMedicalRecordsList.add(medicalRecords.get(1));
			homonymousMedicalRecordsList.add(homonymousMedicalRecord);
			
			when(medicalRecordDao.getDuplicatedMedicalRecords("JacobBoyd")).thenReturn(homonymousMedicalRecordsList);
			
			assertEquals(medicalRecordService.getHomonymousMedicalRecords("JacobBoyd"),homonymousMedicalRecordsList);
		}
		
		@Test
		void insertTest() throws UnavailableDataException, EmptyDataException,
				 MedicalRecordsDataNotFoundException, DuplicatedMedicalRecordException {
			
			MedicalRecord medicalRecordToInsert = new MedicalRecord("Newbie", "Noob", "04/01/1978", new String[] { "" },
					new String[] { "" });
			
			when(medicalRecordDao.insert(medicalRecordToInsert)).thenReturn(medicalRecordToInsert);
			
			assertEquals(medicalRecordService.insertMedicalRecord(medicalRecordToInsert).getFirstName(), "Newbie");
		}

		@Test
		void updateTest() throws UnavailableDataException, EmptyDataException,
				MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException,
				DuplicatedMedicalRecordException {
			
			MedicalRecord medicalRecordToUpdate = medicalRecords.get(0);
			medicalRecordToUpdate.setBirthdate("04/01/1978");
			
			when(medicalRecordDao.update(medicalRecordToUpdate)).thenReturn(medicalRecordToUpdate);
			
			assertEquals(medicalRecordService.updateMedicalRecord(medicalRecordToUpdate).getBirthdate(), "04/01/1978");
		}

		@Test
		void deleteTest() throws UnavailableDataException, EmptyDataException,
				MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException,
				DuplicatedMedicalRecordException {
			
			MedicalRecord medicalRecordTodelete = medicalRecords.get(0);
			
			when(medicalRecordDao.delete(medicalRecordTodelete)).thenReturn(medicalRecordTodelete);
			
			assertEquals(medicalRecordService.deleteMedicalRecord(medicalRecordTodelete).getLastName(), "Marrack");
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrowmWhenTryingToUpdateDuplicatedMedicalRecord() throws UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException, DuplicatedMedicalRecordException {

			MedicalRecord medicalRecordToUpdate = new MedicalRecord();
			
			when(medicalRecordDao.update(any(MedicalRecord.class))).thenThrow(DuplicatedMedicalRecordException.class);
			
			assertThrows(DuplicatedMedicalRecordException.class, ()->medicalRecordService.updateMedicalRecord(medicalRecordToUpdate));
		}

		@Test
		void isExpectedExceptionThrowmWhenTryingToDeleteDuplicatedMedicalRecord() throws UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException, DuplicatedMedicalRecordException {

			MedicalRecord medicalRecordToDelete = new MedicalRecord();
			
			when(medicalRecordDao.delete(any(MedicalRecord.class))).thenThrow(DuplicatedMedicalRecordException.class);
			
			assertThrows(DuplicatedMedicalRecordException.class, ()->medicalRecordService.deleteMedicalRecord(medicalRecordToDelete));
		}
		
		
		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnknownMedicalRecordTest()
				throws UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException {

			when(medicalRecordDao.getOne("Toto")).thenThrow(MedicalRecordNotFoundException.class);

			Exception exception = assertThrows(MedicalRecordNotFoundException.class,
					() -> medicalRecordService.getOneMedicalRecord("Toto"));

			assertEquals(exception.getMessage(), "Medical record identified by Toto has not been found");
		}

		@Test
		void isExpectedExceptionThrownWhenDataSourceIsCorruptedTest()
				throws UnavailableDataException, EmptyDataException {

			when(medicalRecordDao.getAll()).thenThrow(UnavailableDataException.class);

			Exception exception = assertThrows(MedicalRecordsDataNotFoundException.class,
					() -> medicalRecordService.getAllMedicalRecords());

			assertEquals(exception.getMessage(), "A problem occured while retrieving medical records data");
		}
	}
}
