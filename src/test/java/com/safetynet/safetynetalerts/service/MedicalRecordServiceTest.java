package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedMedicalRecordException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
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
		void getAllTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, MedicalRecordsDataNotFoundException  {
			when(medicalRecordDao.getAll()).thenReturn(medicalRecords);

			List<MedicalRecord> getAllMedicalRecords = medicalRecordService.getAllMedicalRecords();

			assertEquals(getAllMedicalRecords.size(), 2);
			assertEquals(getAllMedicalRecords.get(0).getFirstName(), "Jonathan");
		}

		@Test
		void getOneTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException  {
			when(medicalRecordDao.getOne("SophiaZemicks")).thenReturn(medicalRecords.get(1));

			MedicalRecord medicalRecordToGet = medicalRecordService.getOneMedicalRecord("SophiaZemicks");

			assertEquals(medicalRecordToGet.getLastName(), "Zemicks");
		}

		@Test
		void insertTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, DuplicatedItemException, MedicalRecordsDataNotFoundException, DuplicatedMedicalRecordException  {
			MedicalRecord medicalRecordToInsert = new MedicalRecord("Newbie", "Noob", "04/01/1978", new String[] { "" },
					new String[] { "" });
			when(medicalRecordDao.insert(medicalRecordToInsert)).thenReturn(medicalRecordToInsert);
			assertEquals(medicalRecordService.insertMedicalRecord(medicalRecordToInsert).getFirstName(), "Newbie");
		}

		@Test
		void updateTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException {
			MedicalRecord medicalRecordToUpdate = medicalRecords.get(0);
			medicalRecordToUpdate.setBirthdate("04/01/1978");
			when(medicalRecordDao.update(medicalRecordToUpdate)).thenReturn(medicalRecordToUpdate);
			assertEquals(medicalRecordService.updateMedicalRecord(medicalRecordToUpdate).getBirthdate(), "04/01/1978");
		}

		@Test
		void deleteTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException, MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException  {
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
		void isExpectedExceptionThrownWhenInsertingDuplicatedPersonTest() throws DuplicatedItemException,
				DataImportFailedException, UnavailableDataException, EmptyDataException {

			MedicalRecord medicalRecortToInsert = new MedicalRecord();
			when(medicalRecordDao.insert(medicalRecortToInsert)).thenThrow(DuplicatedItemException.class);

			Exception exception = assertThrows(DuplicatedMedicalRecordException.class,
					() -> medicalRecordService.insertMedicalRecord(medicalRecortToInsert));

			assertEquals(exception.getMessage(), "Warning : a medical record identified by " + medicalRecortToInsert.getFirstName()
					+ medicalRecortToInsert.getLastName() + " already exists");
		}

		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnknownPersonTest()
				throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {

			when(medicalRecordDao.getOne("Toto")).thenThrow(ItemNotFoundException.class);

			Exception exception = assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.getOneMedicalRecord("Toto"));

			assertEquals(exception.getMessage(), "Medical record identified by Toto has not been found");

		}

		@Test
		void isExpectedExceptionThrownWhenDataSourceIsCorruptedTest()
				throws UnavailableDataException, EmptyDataException, DataImportFailedException {

			when(medicalRecordDao.getAll()).thenThrow(UnavailableDataException.class);

			Exception exception = assertThrows(MedicalRecordsDataNotFoundException.class, () -> medicalRecordService.getAllMedicalRecords());

			assertEquals(exception.getMessage(), "A problem occured while retrieving medical records data");
		}
	}
}
