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

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.exceptions.DuplicatedLinkedFireStationException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedPersonException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationNotFoundException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
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
		void getAllTest() throws UnavailableDataException, EmptyDataException, LinkedFireStationsDataNotFoundException {
			
			when(linkedFireStationDao.getAll()).thenReturn(linkedFireStations);

			List<LinkedFireStation> getAllFireStations = linkedFireStationService.getAllLinkedFireStations();

			assertEquals(getAllFireStations.size(), 2);
			assertEquals(getAllFireStations.get(0).getAddress(), "1509 Culver St");
		}

		@Test
		void getOneTest() throws UnavailableDataException, EmptyDataException, LinkedFireStationsDataNotFoundException,
				LinkedFireStationNotFoundException {
			
			when(linkedFireStationDao.getOne("892 Downing Ct")).thenReturn(linkedFireStations.get(1));

			LinkedFireStation linkedFireStation = linkedFireStationService.getOneLinkedFireStation("892 Downing Ct");

			assertEquals(linkedFireStation.getStation(), "2");
		}
		
		@Test
		void getDuplicatedLinkedFireStationsTest() throws UnavailableDataException, EmptyDataException,
				LinkedFireStationNotFoundException, LinkedFireStationsDataNotFoundException {
			
			List<LinkedFireStation> duplicatedLinkedFireStationsList = new ArrayList<LinkedFireStation>();
			LinkedFireStation duplicatedLinkedFireStation = new LinkedFireStation();
			
			duplicatedLinkedFireStation.setAddress("1509 Culver St");
			duplicatedLinkedFireStation.setStation("3");
			duplicatedLinkedFireStationsList.add(linkedFireStations.get(1));
			duplicatedLinkedFireStationsList.add(duplicatedLinkedFireStation);
			
			when(linkedFireStationDao.getDuplicatedLinkedFireStation("JacobBoyd")).thenReturn(duplicatedLinkedFireStationsList);
			
			assertEquals(linkedFireStationService.getDuplicatedLinkedFireStations("JacobBoyd"),duplicatedLinkedFireStationsList);
		}

		@Test
		void insertTest() throws UnavailableDataException, EmptyDataException, LinkedFireStationsDataNotFoundException {
			
			LinkedFireStation linkedFireStationToInsert = new LinkedFireStation("5 Rue Clavel", "20");
			
			when(linkedFireStationDao.insert(linkedFireStationToInsert)).thenReturn(linkedFireStationToInsert);
			
			assertEquals(linkedFireStationService.insertLinkedFireStation(linkedFireStationToInsert).getStation(),
					"20");
		}

		@Test
		void updateTest() throws UnavailableDataException, EmptyDataException, LinkedFireStationsDataNotFoundException,
				LinkedFireStationNotFoundException, DuplicatedLinkedFireStationException {
			
			LinkedFireStation linkedFireStationToUpdate = linkedFireStations.get(0);
			linkedFireStationToUpdate.setStation("20");
			
			when(linkedFireStationDao.update(linkedFireStationToUpdate)).thenReturn(linkedFireStationToUpdate);
			
			assertEquals(linkedFireStationService.updateLinkedFireStation(linkedFireStationToUpdate).getStation(),
					"20");
		}

		@Test
		void deleteTest() throws UnavailableDataException, EmptyDataException, LinkedFireStationsDataNotFoundException,
				LinkedFireStationNotFoundException, DuplicatedLinkedFireStationException {
			
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
		void isExpectedExceptionThrowmWhenTryingToUpdateDuplicatedLinkedFireStationn() throws UnavailableDataException, EmptyDataException, LinkedFireStationNotFoundException, DuplicatedLinkedFireStationException {

			LinkedFireStation linkedFireStationToUpdate = new LinkedFireStation();
			
			when(linkedFireStationDao.update(any(LinkedFireStation.class))).thenThrow(DuplicatedLinkedFireStationException.class);
			
			assertThrows(DuplicatedLinkedFireStationException.class, ()->linkedFireStationService.updateLinkedFireStation(linkedFireStationToUpdate));
		}

		@Test
		void isExpectedExceptionThrowmWhenTryingToDeleteDuplicatedLinkedFireStation() throws UnavailableDataException, EmptyDataException, PersonNotFoundException, DuplicatedPersonException, LinkedFireStationNotFoundException, DuplicatedLinkedFireStationException {

			LinkedFireStation linkedFireStationToDelete = new LinkedFireStation();
			
			when(linkedFireStationDao.delete(any(LinkedFireStation.class))).thenThrow(DuplicatedLinkedFireStationException.class);
			
			assertThrows(DuplicatedLinkedFireStationException.class, ()->linkedFireStationService.deleteLinkedFireStation(linkedFireStationToDelete));
		}

		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnknownLinkedFireStationTest()
				throws UnavailableDataException, EmptyDataException, LinkedFireStationNotFoundException {

			when(linkedFireStationDao.getOne("10, Downing Street")).thenThrow(LinkedFireStationNotFoundException.class);

			Exception exception = assertThrows(LinkedFireStationNotFoundException.class,
					() -> linkedFireStationService.getOneLinkedFireStation("10, Downing Street"));

			assertEquals(exception.getMessage(),
					"Fire station mapping for address 10, Downing Street has not been found");

		}

		@Test
		void isExpectedExceptionThrownWhenDataSourceIsCorruptedTest()
				throws UnavailableDataException, EmptyDataException {

			when(linkedFireStationDao.getAll()).thenThrow(UnavailableDataException.class);

			Exception exception = assertThrows(LinkedFireStationsDataNotFoundException.class,
					() -> linkedFireStationService.getAllLinkedFireStations());

			assertEquals(exception.getMessage(), "A problem occured while retrieving fire station mappings data");
		}
	}
}
