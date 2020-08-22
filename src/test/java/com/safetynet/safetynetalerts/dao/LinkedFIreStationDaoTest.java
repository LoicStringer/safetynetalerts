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
import com.safetynet.safetynetalerts.exceptions.DuplicatedLinkedFireStationException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.LinkedFireStation;


@Tag("LinkedFireStationDaoTests")
@DisplayName("LinkedFireStationDao CRUD operations tests")
class LinkedFIreStationDaoTest {

	LinkedFireStationDao linkedFireStationDao;
	List<LinkedFireStation> linkedFireStations;
	
	@BeforeEach
	void setUp() {
		
		linkedFireStationDao = new LinkedFireStationDao();
		linkedFireStations = new ArrayList<LinkedFireStation>();
		DataContainer.reloadDataForTests();
	}

	@Nested
	@Tag("OperationsTests")
	@DisplayName("Operations checking")
	class OperationsTests {
		
		@Test
		void getAllTest() throws UnavailableDataException, EmptyDataException  {
			
			linkedFireStations = linkedFireStationDao.getAll();
			
			assertNotNull(linkedFireStations);
			assertEquals(13, linkedFireStations.size());
		}
		
		@Test
		void getOneTest() throws UnavailableDataException, EmptyDataException, LinkedFireStationNotFoundException  {
			
			LinkedFireStation linkedFireStationToGet = linkedFireStationDao.getOne("1509 Culver St");
			
			assertEquals("1509 Culver St", linkedFireStationToGet.getAddress());
			assertEquals(linkedFireStationToGet.getStation(),"3");
		}
		
		@Test
		void getDuplicatedLinkedFireStation() throws UnavailableDataException, EmptyDataException, LinkedFireStationNotFoundException {
			
			LinkedFireStation linkedFireStationToInsert = new LinkedFireStation();
			linkedFireStationToInsert.setAddress("1509 Culver St");
			linkedFireStationToInsert.setStation("5");
			
			linkedFireStationDao.insert(linkedFireStationToInsert);
			List<LinkedFireStation> duplicatedLinkedFireStations = linkedFireStationDao.getDuplicatedLinkedFireStation("1509 Culver St");
			
			assertEquals(duplicatedLinkedFireStations.size(),2);
			assertEquals(duplicatedLinkedFireStations.get(0).getAddress(),duplicatedLinkedFireStations.get(1).getAddress());
		}

		@Test
		void insertTest() throws DuplicatedLinkedFireStationException, UnavailableDataException, EmptyDataException  {
			
			LinkedFireStation linkedFireStationToInsert = new LinkedFireStation();
			linkedFireStationToInsert.setAddress("5, Rue Clavel");
			linkedFireStationToInsert.setStation("20");
			
			linkedFireStationDao.insert(linkedFireStationToInsert);
			linkedFireStations = linkedFireStationDao.getAll();
			
			assertEquals("20",linkedFireStations.get(linkedFireStations.size()-1).getStation());
		}
		
		@Test
		void updateTest() throws UnavailableDataException, EmptyDataException, LinkedFireStationNotFoundException, DuplicatedLinkedFireStationException  {
			
			linkedFireStations = linkedFireStationDao.getAll();
			LinkedFireStation linkedFireStationToUpdate = linkedFireStations.get(10);
			
			linkedFireStationToUpdate.setStation("20");
			linkedFireStationDao.update(linkedFireStationToUpdate);
			linkedFireStations = linkedFireStationDao.getAll();
			
			assertEquals("20", linkedFireStations.get(10).getStation());
		}
		
		@Test
		void deleteTest() throws UnavailableDataException, EmptyDataException, LinkedFireStationNotFoundException, DuplicatedLinkedFireStationException {
			
			linkedFireStations = linkedFireStationDao.getAll();
			LinkedFireStation linkedFireStationToDelete = linkedFireStations.get(0);
			
			linkedFireStationDao.delete(linkedFireStationToDelete);
			linkedFireStations = linkedFireStationDao.getAll();
			
			assertNotEquals("1509 Culver St", linkedFireStations.get(0).getAddress());
		}
		
	}
	
	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {
		
		@Test
		void isThrowingExceptionWhenLinkedFireStationIsNotFoundTest()  {
		
			LinkedFireStation linkedFireStationToGet = new LinkedFireStation();
			linkedFireStationToGet.setAddress("10 Downing Street");
			
			Exception exception = assertThrows(LinkedFireStationNotFoundException.class,()->linkedFireStationDao.getOne("10,Downing Street"));
			
			assertEquals(exception.getMessage(),"No fire station mapping found for address 10,Downing Street");
		}
		
		@Test
		void isThrowingExceptionWhenTryingToDeleteDuplicatedLinkedFireStation() throws UnavailableDataException, EmptyDataException {
			
			LinkedFireStation duplicatedLinkedFireStation = new LinkedFireStation();
			duplicatedLinkedFireStation.setAddress("1509 Culver St");
			duplicatedLinkedFireStation.setStation("3");
			
			linkedFireStationDao.insert(duplicatedLinkedFireStation);
			
			assertThrows(DuplicatedLinkedFireStationException.class, ()->linkedFireStationDao.delete(duplicatedLinkedFireStation));
		}
		
		@Test
void isThrowingExceptionWhenTryingToUpdateDuplicatedLinkedFireStation() throws UnavailableDataException, EmptyDataException {
			
			LinkedFireStation duplicatedLinkedFireStation = new LinkedFireStation();
			duplicatedLinkedFireStation.setAddress("1509 Culver St");
			duplicatedLinkedFireStation.setStation("3");
			
			linkedFireStationDao.insert(duplicatedLinkedFireStation);
			
			assertThrows(DuplicatedLinkedFireStationException.class, ()->linkedFireStationDao.update(duplicatedLinkedFireStation));
		}
	
		@Test
		void isThrowingExceptionWhenDataSourceIsEmpty() {
			
			DataContainer.linkedFireStationsData.removeAll();
			
			Exception exception = assertThrows(EmptyDataException.class,()->linkedFireStationDao.getAll());
			assertEquals(exception.getMessage(),"Fire station mappings list is empty");
		}
		
		@Test
		void isThrowingExceptionWhenDataSourceIsNull() {
			
			DataContainer.linkedFireStationsData=null;
			
			Exception exception = assertThrows(UnavailableDataException.class,()->linkedFireStationDao.getAll());
			assertEquals(exception.getMessage(),"Fire station mappings list is null");
		}
		
	}
	

	
	
	
}
