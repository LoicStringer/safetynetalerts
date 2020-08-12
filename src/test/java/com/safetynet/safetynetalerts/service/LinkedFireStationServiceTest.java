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

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedLinkedFireStationException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationNotFoundException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.LinkedFireStation;

@DisplayName("LinkedFireStationService CRUD operations tests")
@ExtendWith(MockitoExtension.class)
class LinkedFireStationServiceTest {

	@Mock
	private LinkedFireStationDao linkedFireStationDao;

	@InjectMocks
	private LinkedFireStationService linkedFireStationService;

	private static List<LinkedFireStation> linkedFireStations;

	@BeforeAll
	static void setUp() {
		linkedFireStations = new ArrayList<LinkedFireStation>();

		linkedFireStations.add(new LinkedFireStation("1509 Culver St", "3"));
		linkedFireStations.add(new LinkedFireStation("892 Downing Ct", "2"));
	}

	@Nested
	@Tag("OperationsTests")
	@DisplayName("Operations checking")
	class OperationsTests {

		@Test
		void getAllTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException,
				LinkedFireStationsDataNotFoundException {
			when(linkedFireStationDao.getAll()).thenReturn(linkedFireStations);

			List<LinkedFireStation> getAllFireStations = linkedFireStationService.getAllLinkedFireStations();

			assertEquals(getAllFireStations.size(), 2);
			assertEquals(getAllFireStations.get(0).getAddress(), "1509 Culver St");
		}

		@Test
		void getOneTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException,
				ItemNotFoundException, LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException {
			when(linkedFireStationDao.getOne("892 Downing Ct")).thenReturn(linkedFireStations.get(1));

			LinkedFireStation linkedFireStation = linkedFireStationService.getOneLinkedFireStation("892 Downing Ct");

			assertEquals(linkedFireStation.getStation(), "2");
		}

		@Test
		void insertTest() throws DuplicatedItemException, DataImportFailedException, UnavailableDataException,
				EmptyDataException, LinkedFireStationsDataNotFoundException, DuplicatedLinkedFireStationException {
			LinkedFireStation linkedFireStationToInsert = new LinkedFireStation("5 Rue Clavel", "20");
			when(linkedFireStationDao.insert(linkedFireStationToInsert)).thenReturn(linkedFireStationToInsert);
			assertEquals(linkedFireStationService.insertLinkedFireStation(linkedFireStationToInsert).getStation(),
					"20");
		}

		@Test
		void updateTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException,
				ItemNotFoundException, LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException {
			LinkedFireStation linkedFireStationToUpdate = linkedFireStations.get(0);
			linkedFireStationToUpdate.setStation("20");
			when(linkedFireStationDao.update(linkedFireStationToUpdate)).thenReturn(linkedFireStationToUpdate);
			assertEquals(linkedFireStationService.updateLinkedFireStation(linkedFireStationToUpdate).getStation(),
					"20");
		}

		@Test
		void deleteTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException,
				ItemNotFoundException, LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException {
			LinkedFireStation linkedFireStationToDelete = linkedFireStations.get(1);
			when(linkedFireStationDao.delete(linkedFireStationToDelete)).thenReturn(linkedFireStationToDelete);
			assertEquals(linkedFireStationService.deleteLinkedFireStation(linkedFireStationToDelete).getStation(), "2");
		}

	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenInsertingDuplicatedPersonTest() throws DuplicatedItemException,
				DataImportFailedException, UnavailableDataException, EmptyDataException {

			LinkedFireStation linkedFireStationToInsert = new LinkedFireStation();
			when(linkedFireStationDao.insert(linkedFireStationToInsert)).thenThrow(DuplicatedItemException.class);

			Exception exception = assertThrows(DuplicatedLinkedFireStationException.class,
					() -> linkedFireStationService.insertLinkedFireStation(linkedFireStationToInsert));

			assertEquals(exception.getMessage(), "Warning : a fire station mapping identified by " + linkedFireStationToInsert.getAddress()
					+ linkedFireStationToInsert.getStation() + " already exists");
		}

		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnknownPersonTest()
				throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {

			when(linkedFireStationDao.getOne("Toto")).thenThrow(ItemNotFoundException.class);

			Exception exception = assertThrows(LinkedFireStationNotFoundException.class, () -> linkedFireStationService.getOneLinkedFireStation("Toto"));

			assertEquals(exception.getMessage(), "Fire station mapping identified by Toto has not been found");

		}

		@Test
		void isExpectedExceptionThrownWhenDataSourceIsCorruptedTest()
				throws UnavailableDataException, EmptyDataException, DataImportFailedException {

			when(linkedFireStationDao.getAll()).thenThrow(UnavailableDataException.class);

			Exception exception = assertThrows(LinkedFireStationsDataNotFoundException.class, () -> linkedFireStationService.getAllLinkedFireStations());

			assertEquals(exception.getMessage(), "A problem occured while retrieving fire station mappings data");
		}
	}
}
