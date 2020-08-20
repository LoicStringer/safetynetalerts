package com.safetynet.safetynetalerts.controller;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CommunityControllerTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void isCommunityEmailUrlFunctionalTest() throws Exception {

		String city = "Culver";

		mockMvc.perform(get("/communityEmail?city=" + city))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.[0]").value("jaboyd@email.com"))
				.andExpect(jsonPath("$.size()").value(23));
	}

	@Test
	void isCommunityPersonInfoUrlFunctionalTest() throws Exception {

		String firstName = "Eric";
		String lastName = "Cadigan";

		mockMvc.perform(get("/personInfo?firstName=" + firstName + "&lastName=" + lastName)).andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.firstName").value("Eric"))
				.andExpect(jsonPath("$.lastName").value("Cadigan"))
				.andExpect(jsonPath("$.age").value("75"));
	}

	@Test
	void isCommunityFireStationUrlFunctionalTest() throws Exception {

		String fireStationNumber = "1";

		mockMvc.perform(get("/firestation?stationNumber=" + fireStationNumber))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.childCount").value("1"))
				.andExpect(jsonPath("$.personsInfo.[0].firstName").value("Peter"));
	}

	@Test
	void exception() throws Exception{
		
		String city = "London";

		mockMvc.perform(get("/communityEmail?city=" + city))
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof PersonsDataNotFoundException));
	}
}
