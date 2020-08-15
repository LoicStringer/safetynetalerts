package com.safetynet.safetynetalerts.controller;

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

		String firstName = "John";
		String lastName = "Boyd";

		mockMvc.perform(get("/personInfo?firstName=" + firstName + "&lastName=" + lastName)).andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Boyd"))
				.andExpect(jsonPath("$.age").value("36"));
	}

	@Test
	void isCommunityFireStationUrlFunctionalTest() throws Exception {

		String fireStationNumber = "3";

		mockMvc.perform(get("/firestation?stationNumber=" + fireStationNumber))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.childCount").value("3"))
				.andExpect(jsonPath("$.personsInfo.[0].firstName").value("John"));
	}

}
