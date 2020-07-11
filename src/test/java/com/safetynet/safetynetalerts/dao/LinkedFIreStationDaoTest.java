package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalerts.model.LinkedFireStation;

class LinkedFIreStationDaoTest {

	LinkedFireStationDao linkedFireStationDao;
	LinkedFireStation linkedFireStation;
	List<LinkedFireStation> linkedFireStations;

	@BeforeEach
	void setUp() {
		linkedFireStationDao = new LinkedFireStationDao();
		linkedFireStation = new LinkedFireStation();
		linkedFireStations = new ArrayList<LinkedFireStation>();
	}

	@Test
	void getAllTest() {
		linkedFireStations = linkedFireStationDao.getAll();
		assertEquals(13, linkedFireStations.size());
	}
/*
	@Test
	void insertTest() {
		linkedFireStation.setStation("20");
		
		boolean isInserted = linkedFireStationDao.insert(linkedFireStation);
		linkedFireStations = linkedFireStationDao.getAll();
		
		assertTrue(isInserted);
		assertEquals("20",linkedFireStations.get(linkedFireStations.size()-1).getStation());
	}
	
	@Test
	void updateTest() {
		linkedFireStation.setAddress("1509 Culver St");
		
		linkedFireStation.setStation("20");
		boolean isUpdated = linkedFireStationDao.update(linkedFireStation);
		linkedFireStations = linkedFireStationDao.getAll();
		
		assertTrue(isUpdated);
		assertEquals("20", linkedFireStations.get(0).getStation());
	}
	
	@Test
	void deleteTest() {
		linkedFireStation.setAddress("1509 Culver St");
		linkedFireStation.setStation("3");
		
		boolean isDeleted = linkedFireStationDao.delete(linkedFireStation);
		linkedFireStations = linkedFireStationDao.getAll();
		
		assertTrue(isDeleted);
		assertNotEquals("1509 Culver St", linkedFireStations.get(0).getAddress());
	}
*/
	
	
}
