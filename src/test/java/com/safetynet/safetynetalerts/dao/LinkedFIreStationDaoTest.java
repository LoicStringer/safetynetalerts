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
	}

	@Test
	void getAllTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException  {
		
		linkedFireStations = linkedFireStationDao.getAll();
		
		assertNotNull(linkedFireStations);
		assertEquals(13, linkedFireStations.size());
	}
	
	@Test
	void getOneTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException  {
		
		LinkedFireStation linkedFireStationToGet = linkedFireStationDao.getOne("1509 Culver St");
		
		assertEquals("1509 Culver St", linkedFireStationToGet.getAddress());
		assertEquals(linkedFireStationToGet.getStation(),"3");
	}

	@Test
	void insertTest() throws DuplicatedItemException, DataImportFailedException, UnavailableDataException, EmptyDataException  {
		
		LinkedFireStation linkedFireStationToInsert = new LinkedFireStation();
		linkedFireStationToInsert.setAddress("5, Rue Clavel");
		linkedFireStationToInsert.setStation("20");
		
		linkedFireStationDao.insert(linkedFireStationToInsert);
		linkedFireStations = linkedFireStationDao.getAll();
		
		assertEquals("20",linkedFireStations.get(linkedFireStations.size()-1).getStation());
	}
	
	@Test
	void updateTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException  {
		
		linkedFireStations = linkedFireStationDao.getAll();
		LinkedFireStation linkedFireStationToUpdate = linkedFireStations.get(10);
		
		linkedFireStationToUpdate.setStation("20");
		linkedFireStationDao.update(linkedFireStationToUpdate);
		linkedFireStations = linkedFireStationDao.getAll();
		
		assertEquals("20", linkedFireStations.get(10).getStation());
	}
	
	@Test
	void deleteTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {
		
		linkedFireStations = linkedFireStationDao.getAll();
		LinkedFireStation linkedFireStationToDelete = linkedFireStations.get(0);
		
		linkedFireStationDao.delete(linkedFireStationToDelete);
		linkedFireStations = linkedFireStationDao.getAll();
		
		assertNotEquals("1509 Culver St", linkedFireStations.get(0).getAddress());
	}

	@Test
	void isThrowingExceptionWhenInsertingDuplicatedAddressTest() {
		
		LinkedFireStation linkedFireStationToInsert = new LinkedFireStation();
		linkedFireStationToInsert.setAddress("1509 Culver St");
		
		Exception exception = assertThrows(DuplicatedItemException.class, ()->linkedFireStationDao.insert(linkedFireStationToInsert));
		
		assertEquals(exception.getMessage(),"Warning : a fire station mapping with the same address already exists in data container");
	}
	
	@Test
	void isThrowingExceptionWhenLinkedFireStationIsNotFoundTest()  {
	
		LinkedFireStation linkedFireStationToGet = new LinkedFireStation();
		linkedFireStationToGet.setAddress("10 Downing Street");
		
		Exception exception = assertThrows(ItemNotFoundException.class,()->linkedFireStationDao.getOne("10,Downing Street"));
		
		assertEquals(exception.getMessage(),"No fire station mapping found for address 10,Downing Street");
	}
	
	
	
}
