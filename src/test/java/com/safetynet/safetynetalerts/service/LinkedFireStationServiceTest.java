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
		
		List<LinkedFireStation> linkedFireStations = linkedFireStationService.getAllLinkedFireStations();
		
		assertEquals(linkedFireStations.size(),2);
		assertEquals(linkedFireStations.get(0).getAddress(),"1509 Culver St");
	}
	
	@Test
	void getOneTest() {
		when(linkedFireStationDao.getOne("892 Downing Ct")).thenReturn(linkedFireStations.get(1));
		
		LinkedFireStation linkedFireStation = linkedFireStationService.getOneLinkedFireStation("892 Downing Ct");
		
		assertEquals(linkedFireStation.getStation(),"2");
	}
	
	@Test
	void insertTest() {
		LinkedFireStation linkedFireStationToInsert = new LinkedFireStation("85 Rue de Belleville","75020");
		when(linkedFireStationDao.insert(linkedFireStationToInsert)).thenReturn(linkedFireStationToInsert);
		
	}
	@Test
	void updateTest() {
		LinkedFireStation updatedLinkedFireStation = linkedFireStationService.updateLinkedFireStation(new LinkedFireStation("892 Downing Ct","75020"));
		when(linkedFireStationDao.update(updatedLinkedFireStation)).thenReturn(updatedLinkedFireStation);
		assertEquals(linkedFireStations.get(1).getStation(), updatedLinkedFireStation.getStation());
	}
	
	@Test
	void deleteTest() {
		when(linkedFireStationDao.getOne("892 Downing Ct")).thenReturn(linkedFireStations.get(1));
		
	}
	
	
	
	
	
	

}
