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
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.data.DataContainer;
import com.safetynet.safetynetalerts.exceptions.DuplicatedPersonException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.RequestBodyException;
import com.safetynet.safetynetalerts.model.Person;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTestIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PersonDao personDao;
	
	@BeforeEach
	void setUpForTests() {
		DataContainer.reloadDataForTests();
	}
	
	@Test
	void isInsertPersonEndpointFunctionalTest() throws Exception {
		
		Person personToInsert = new Person();
		personToInsert.setFirstName("Newbie");
		personToInsert.setLastName("Noob");
		personToInsert.setCity("Paris");
		
		mockMvc.perform(post("/person")
				.content(objectMapper.writeValueAsString(personToInsert))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(personDao.getOne("NewbieNoob").getCity(),"Paris");
		assertEquals(personDao.getAll().get(personDao.getAll().size()-1).getFirstName(),"Newbie");	
	}
	
	@Test
	void isUpdatePersonEndpointFunctionalTest() throws Exception {
		
		Person personToUpdate = personDao.getAll().get(0);
		personToUpdate.setCity("Paris");
		
		mockMvc.perform(put("/person")
				.content(objectMapper.writeValueAsString(personToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(personDao.getAll().get(0).getCity(),"Paris");
	}

	@Test
	void isDeletePersonEndpointFunctional() throws Exception {
		
		Person personToDelete = personDao.getAll().get(5);
		
		mockMvc.perform(delete("/person")
				.content(objectMapper.writeValueAsString(personToDelete))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertNotEquals(personDao.getAll().get(5),personToDelete);
	}
	
	@Test
	void isExpectedExceptionHandledWhenTryingToUpdateHomonymousPerson() throws Exception{
		
		Person homonymousPerson = new Person();
		homonymousPerson.setFirstName("Ron");
		homonymousPerson.setLastName("Peters");
		personDao.insert(homonymousPerson);
		
		mockMvc.perform(put("/person")
		.content(objectMapper.writeValueAsString(homonymousPerson))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is3xxRedirection())
		.andExpect(result-> assertTrue(result.getResolvedException() instanceof DuplicatedPersonException));
	}
	

	@Test
	void isExpectedExceptionHandledWhenTryingToDeleteHomonymousPerson() throws Exception{
		
		Person homonymousPerson = new Person();
		homonymousPerson.setFirstName("Ron");
		homonymousPerson.setLastName("Peters");
		personDao.insert(homonymousPerson);
		
		mockMvc.perform(delete("/person")
		.content(objectMapper.writeValueAsString(homonymousPerson))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is3xxRedirection())
		.andExpect(result-> assertTrue(result.getResolvedException() instanceof DuplicatedPersonException));
	}
	
	@Test
	void isExpectedExceptionHandledWhenTryingToProceedWithCorruptedData() throws Exception{
		
		Person personToUpdate = personDao.getAll().get(0);
		personToUpdate.setCity("Paris");
		
		Person personToDelete = personDao.getAll().get(5);
		
		DataContainer.personsData.removeAll();
		
		mockMvc.perform(put("/person")
				.content(objectMapper.writeValueAsString(personToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof PersonsDataNotFoundException));
		
		mockMvc.perform(delete("/person")
				.content(objectMapper.writeValueAsString(personToDelete))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof PersonsDataNotFoundException));
		
	}
	
	@Test
	void isExpectedExceptionHandledWhenPersonFirstNameOrLastNameIsBlank() throws Exception {
		
		Person blankPerson = new Person();
		blankPerson.setFirstName("");
		blankPerson.setLastName("");
		
		mockMvc.perform(post("/person")
				.content(objectMapper.writeValueAsString(blankPerson))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));
		
		mockMvc.perform(put("/person")
				.content(objectMapper.writeValueAsString(blankPerson))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));
		
		mockMvc.perform(delete("/person")
				.content(objectMapper.writeValueAsString(blankPerson))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));	
	}
	
	@Test
	void isExpectedExceptionHandledWhenPersonFirstNameOrLastNameIsNotAlphabetical() throws Exception {
		
		Person notAlphabeticalPerson = new Person();
		notAlphabeticalPerson.setFirstName("1");
		notAlphabeticalPerson.setLastName("1");
		
		mockMvc.perform(post("/person")
				.content(objectMapper.writeValueAsString(notAlphabeticalPerson))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));
		
		mockMvc.perform(put("/person")
				.content(objectMapper.writeValueAsString(notAlphabeticalPerson))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));
		
		mockMvc.perform(delete("/person")
				.content(objectMapper.writeValueAsString(notAlphabeticalPerson))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestBodyException));
		
	}
}

