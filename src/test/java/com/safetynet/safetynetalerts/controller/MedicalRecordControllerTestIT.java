package com.safetynet.safetynetalerts.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
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
import com.safetynet.safetynetalerts.data.DataContainer;
import com.safetynet.safetynetalerts.exceptions.DuplicatedMedicalRecordException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.RequestBodyException;
import com.safetynet.safetynetalerts.model.MedicalRecord;



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
	
	@BeforeEach
	void setUpForTests() {
		DataContainer.reloadDataForTests();
	}
	
	
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
	
	@Test
	void isExpectedExceptionHandledWhenTryingToUpdateHomonymousMedicalRecord() throws Exception{
		
		MedicalRecord homonymousMedicalRecord = new MedicalRecord();
		homonymousMedicalRecord.setFirstName("Ron");
		homonymousMedicalRecord.setLastName("Peters");
		medicalRecordDao.insert(homonymousMedicalRecord);
		
		mockMvc.perform(put("/medicalRecord")
		.content(objectMapper.writeValueAsString(homonymousMedicalRecord))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is3xxRedirection())
		.andExpect(result-> assertTrue(result.getResolvedException() instanceof DuplicatedMedicalRecordException));
	}
	

	@Test
	void isExpectedExceptionHandledWhenTryingToDeleteHomonymousMedicalRecord() throws Exception{
		
		MedicalRecord homonymousMedicalRecord = new MedicalRecord();
		homonymousMedicalRecord.setFirstName("Ron");
		homonymousMedicalRecord.setLastName("Peters");
		medicalRecordDao.insert(homonymousMedicalRecord);
		
		mockMvc.perform(delete("/medicalRecord")
		.content(objectMapper.writeValueAsString(homonymousMedicalRecord))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is3xxRedirection())
		.andExpect(result-> assertTrue(result.getResolvedException() instanceof DuplicatedMedicalRecordException));
	}
	
	@Test
	void isExpectedExceptionHandledWhenTryingToUpdateUnknownMedicalRecord() throws Exception{
		
		MedicalRecord unknownMedicalRecord = new MedicalRecord();
		unknownMedicalRecord.setFirstName("Carlito");
		unknownMedicalRecord.setLastName("Brigante");
		
		mockMvc.perform(put("/medicalRecord")
				.content(objectMapper.writeValueAsString(unknownMedicalRecord))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof MedicalRecordNotFoundException));
	}
	
	@Test
	void isExpectedExceptionHandledWhenTryingToProceedWithCorruptedData() throws Exception{
		
		MedicalRecord medicalRecordToUpdate = medicalRecordDao.getAll().get(0);
		medicalRecordToUpdate.setBirthdate("04/01/1978");
		
		MedicalRecord medicalRecordToDelete = medicalRecordDao.getAll().get(0);
		
		DataContainer.medicalRecordsData.removeAll();
		
		mockMvc.perform(put("/medicalRecord")
				.content(objectMapper.writeValueAsString(medicalRecordToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof MedicalRecordsDataNotFoundException));
	
		mockMvc.perform(delete("/medicalRecord")
				.content(objectMapper.writeValueAsString(medicalRecordToDelete))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof MedicalRecordsDataNotFoundException));
	}
	
	@Test
	void isExpectedExceptionHandledWhenPersonFirstNameOrLastNameIsBlank() throws Exception {
		
		MedicalRecord blankMedicalRecord = new MedicalRecord();
		blankMedicalRecord.setFirstName("");
		blankMedicalRecord.setLastName("");
		
		mockMvc.perform(post("/medicalRecord")
				.content(objectMapper.writeValueAsString(blankMedicalRecord))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));
		
		mockMvc.perform(put("/medicalRecord")
				.content(objectMapper.writeValueAsString(blankMedicalRecord))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));
		
		mockMvc.perform(delete("/medicalRecord")
				.content(objectMapper.writeValueAsString(blankMedicalRecord))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));	
	}
	
	@Test
	void isExpectedExceptionHandledWhenPersonFirstNameOrLastNameIsNotAlphabetical() throws Exception {
		
		MedicalRecord notAlphabeticalMedicalRecord = new MedicalRecord();
		notAlphabeticalMedicalRecord.setFirstName("1");
		notAlphabeticalMedicalRecord.setLastName("1");
		
		mockMvc.perform(post("/medicalRecord")
				.content(objectMapper.writeValueAsString(notAlphabeticalMedicalRecord))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));
		
		mockMvc.perform(put("/medicalRecord")
				.content(objectMapper.writeValueAsString(notAlphabeticalMedicalRecord))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));
		
		mockMvc.perform(delete("/medicalRecord")
				.content(objectMapper.writeValueAsString(notAlphabeticalMedicalRecord))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));
		
	}
}
