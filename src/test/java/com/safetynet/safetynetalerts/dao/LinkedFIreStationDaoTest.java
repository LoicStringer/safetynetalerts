package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalerts.model.LinkedFireStation;


@Tag("LinkedFireStationDaoTests")
@DisplayName("LinkedFireStationDao CRUD operations tests")
class LinkedFIreStationDaoTest {

	LinkedFireStationDao linkedFireStationDao;
	LinkedFireStation linkedFireStation;
	List<LinkedFireStation> linkedFireStations;
	String address;

	@BeforeEach
	void setUp() {
		linkedFireStationDao = new LinkedFireStationDao();
		linkedFireStation = new LinkedFireStation();
		linkedFireStations = new ArrayList<LinkedFireStation>();
		address = "1509 Culver St";
	}

	@Test
	void getAllTest() {
		linkedFireStations = linkedFireStationDao.getAll();
		assertEquals(13, linkedFireStations.size());
	}
	
	@Test
	void getOneTest() {
		LinkedFireStation linkedFireStationToGet = linkedFireStationDao.getOne(address);
		assertEquals(address, linkedFireStationToGet.getAddress());
	}

	@Test
	void insertTest() {
		linkedFireStation.setStation("20");
		
		linkedFireStationDao.insert(linkedFireStation);
		linkedFireStations = linkedFireStationDao.getAll();
		
		assertEquals("20",linkedFireStations.get(linkedFireStations.size()-1).getStation());
	}
	
	@Test
	void updateTest() {
		linkedFireStation.setAddress("1509 Culver St");
		
		linkedFireStation.setStation("20");
		linkedFireStationDao.update(linkedFireStation);
		linkedFireStations = linkedFireStationDao.getAll();
		
		assertEquals("20", linkedFireStations.get(0).getStation());
	}
	
	@Test
	void deleteTest() {
		linkedFireStation.setAddress("1509 Culver St");
		
		linkedFireStationDao.delete(linkedFireStation);
		linkedFireStations = linkedFireStationDao.getAll();
		
		assertNotEquals("1509 Culver St", linkedFireStations.get(0).getAddress());
	}

	
	
}
