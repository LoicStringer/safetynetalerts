package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;


@ExtendWith(MockitoExtension.class)
class LinkedFireStationServiceTest {

	@Mock
	private LinkedFireStationDao linkedFireStationDao;
	
	@InjectMocks
	private LinkedFireStationService linkedFireStationService;
	
	List<LinkedFireStation> linkedFireStations;
	
	@BeforeEach
	void setUp() {
		linkedFireStations = new ArrayList<LinkedFireStation>();
		
		linkedFireStations.add(new LinkedFireStation("1509 Culver St","3"));
		linkedFireStations.add(new LinkedFireStation("892 Downing Ct","2"));
	}
	
	@Test
	void getAllTest() {
		when(linkedFireStationDao.getAll()).thenReturn(linkedFireStations);
		
		List<LinkedFireStation> getAllFireStations = linkedFireStationService.getAllLinkedFireStations();
		
		assertEquals(getAllFireStations.size(),2);
		assertEquals(getAllFireStations.get(0).getAddress(),"1509 Culver St");
	}
	
	@Test
	void getOneTest() {
		when(linkedFireStationDao.getOne("892 Downing Ct")).thenReturn(linkedFireStations.get(1));
		
		LinkedFireStation linkedFireStation = linkedFireStationService.getOneLinkedFireStation("892 Downing Ct");
		
		assertEquals(linkedFireStation.getStation(),"2");
	}
	
	@Test
	void insertTest() {
		LinkedFireStation linkedFireStationToInsert = new LinkedFireStation("5 Rue Clavel","20");
		when(linkedFireStationDao.insert(linkedFireStationToInsert)).thenReturn(linkedFireStationToInsert);
		assertEquals(linkedFireStationService.insertLinkedFireStation(linkedFireStationToInsert).getStation(),"20");
		
	}
	
	@Test
	void updateTest() {
		LinkedFireStation linkedFireStationToUpdate = linkedFireStations.get(0);
		linkedFireStationToUpdate.setStation("20");
		when(linkedFireStationDao.update(linkedFireStationToUpdate)).thenReturn(linkedFireStationToUpdate);
		assertEquals(linkedFireStationService.updateLinkedFireStation(linkedFireStationToUpdate).getStation(),"20");
	}
	
	@Test
	void deleteTest() {
		LinkedFireStation linkedFireStationToDelete = linkedFireStations.get(0);
		when(linkedFireStationDao.delete(linkedFireStationToDelete)).thenReturn(linkedFireStationToDelete);
		assertEquals(linkedFireStationService.deleteLinkedFireStation(linkedFireStationToDelete).getStation(),"3");
	}
	
	
	
	
	
	

}
