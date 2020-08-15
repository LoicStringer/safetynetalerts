package com.safetynet.safetynetalerts.controller;

import static org.junit.jupiter.api.Assertions.*;
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
import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordControllerTestIT {


	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	
	@Test
	void isInsertMedicalRecordEndpointFunctionalTest() throws Exception {
		
		MedicalRecord medicalRecordToInsert = new MedicalRecord();
		medicalRecordToInsert.setFirstName("Newbie");
		medicalRecordToInsert.setLastName("Noob");
		medicalRecordToInsert.setBirthdate("04/01/1978");
		
		mockMvc.perform(post("/medicalRecord")
				.content(objectMapper.writeValueAsString(medicalRecordToInsert))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(medicalRecordDao.getOne("NewbieNoob").getBirthdate(),"04/01/1978");
		assertEquals(medicalRecordDao.getAll().get(medicalRecordDao.getAll().size()-1).getFirstName(),"Newbie");	
	}
	
	@Test
	void isUpdateMedicalRecordEndpointFunctionalTest() throws Exception {
		
		MedicalRecord medicalRecordToUpdate = medicalRecordDao.getAll().get(0);
		medicalRecordToUpdate.setBirthdate("04/01/1978");
		
		mockMvc.perform(put("/medicalRecord")
				.content(objectMapper.writeValueAsString(medicalRecordToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(medicalRecordDao.getAll().get(0).getBirthdate(),"04/01/1978");
	}

	@Test
	void isDeleteMedicalRecordEndpointFunctional() throws Exception {
		
		MedicalRecord medicalRecordToDelete = medicalRecordDao.getAll().get(0);
		
		mockMvc.perform(delete("/medicalRecord")
				.content(objectMapper.writeValueAsString(medicalRecordToDelete))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertNotEquals(medicalRecordDao.getAll().get(0),medicalRecordToDelete);
	}
	
	
}
