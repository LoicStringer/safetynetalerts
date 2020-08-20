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
class EmergencyControllerTestIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void isEmergencyChildAlertUrlFunctionalTest() throws Exception{
		
		String address = "947 E. Rose Dr";
		
		mockMvc.perform(get("/childAlert?address="+address))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.childrenThere[0].firstName").value("Kendrik"))
				.andExpect(jsonPath("$.otherPersonsThere[0].firstName").value("Brian"));
	}

	@Test
	void isEmergencyPhoneAlertUrFunctionalTest() throws Exception {
		
		String fireStationNumber = "2";
		
		mockMvc.perform(get("/phoneAlert?firestation="+fireStationNumber))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.size()").value(5))
				.andExpect(jsonPath("$[0]").value("841-874-6513"));
	}
	
	@Test
	void isEmergencyFireUrlFunctionalTest() throws Exception {
		
		String address = "947 E. Rose Dr";
		
		mockMvc.perform(get("/fire?address="+address))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.stationNumber").value("1"))
				.andExpect(jsonPath("$.inhabitantsThere[0].firstName").value("Brian"));
	}
	
	@Test
	void isEmergencyFloodStationsUrlFunctionalTest() throws Exception {
		
		String stationsNumbers = "1,2,3";
		
		mockMvc.perform(get("/flood/stations?stations="+stationsNumbers))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.coveredHomesList[0].stationNumber").value("1"))
				.andExpect(jsonPath("$.coveredHomesList[0].homesInfos[0].address").value("644 Gershwin Cir"));
				
	}
	
}
