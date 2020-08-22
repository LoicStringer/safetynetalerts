package com.safetynet.safetynetalerts.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LinkedFireStationControllerTestIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private LinkedFireStationDao linkedFireStationDao;
	
	@Test
	void isInsertLinkedFireStationEndpointFunctionalTest() throws Exception {
		
		LinkedFireStation linkedFireStationToInsert = new LinkedFireStation();
		linkedFireStationToInsert.setAddress("5, Rue Clavel");
		linkedFireStationToInsert.setStation("20");
		
		
		mockMvc.perform(post("/firestation")
				.content(objectMapper.writeValueAsString(linkedFireStationToInsert))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(linkedFireStationDao.getOne("5, Rue Clavel").getStation(),"20");
		assertEquals(linkedFireStationDao.getAll().get(linkedFireStationDao.getAll().size()-1).getAddress(),"5, Rue Clavel");	
	}
	
	@Test
	void isUpdateLinkedFireStationEndpointFunctionalTest() throws Exception {
		
		LinkedFireStation linkedFireStationToUpdate = linkedFireStationDao.getAll().get(0);
		linkedFireStationToUpdate.setStation("20");
		
		mockMvc.perform(put("/firestation")
				.content(objectMapper.writeValueAsString(linkedFireStationToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(linkedFireStationDao.getAll().get(0).getStation(),"20");
	}

	@Test
	void isDeleteLinkedFireStationEndpointFunctional() throws Exception {
		
		LinkedFireStation linkedFireStationToDelete = linkedFireStationDao.getAll().get(0);
		
		mockMvc.perform(delete("/firestation")
				.content(objectMapper.writeValueAsString(linkedFireStationToDelete))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertNotEquals(linkedFireStationDao.getAll().get(0),linkedFireStationToDelete);
	}
	

}
