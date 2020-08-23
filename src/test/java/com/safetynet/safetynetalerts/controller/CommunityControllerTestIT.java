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

import com.safetynet.safetynetalerts.exceptions.LinkedFireStationNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.RequestParameterException;

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

		String firstName = "Tony";
		String lastName = "Cooper";

		mockMvc.perform(get("/personInfo?firstName=" + firstName + "&lastName=" + lastName)).andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.personsInfo.[0].firstName").value("Tony"))
				.andExpect(jsonPath("$.personsInfo.[0].lastName").value("Cooper"))
				.andExpect(jsonPath("$.personsInfo.[0].age").value("26"));
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
	void isExpectedExceptionHandledWhenCityIsUnknownTest() throws Exception{
		
		String city = "London";

		mockMvc.perform(get("/communityEmail?city=" + city))
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof PersonsDataNotFoundException));
	}
	
	@Test
	void isExpectedExceptionHandledWhenStationNumberIsUnknown() throws Exception{
		
		String fireStationNumber = "12";
		
		mockMvc.perform(get("/firestation?stationNumber=" + fireStationNumber))
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof LinkedFireStationNotFoundException));
	}
	
	@Test
	void isExpectedExceptionHandledWhenPersonIsUnknown() throws Exception{
		
		String firstName = "Tony";
		String lastName = "Montana";
		
		mockMvc.perform(get("/personInfo?firstName=" + firstName + "&lastName=" + lastName))
		.andExpect(result-> assertTrue(result.getResolvedException() instanceof PersonNotFoundException));
	}
	
	@Test
	void isExpectedExceptionHandledWhenRequestParameterIsBlank() throws Exception{
		
		String parameter ="";
		
		mockMvc.perform(get("/communityEmail?city=" + parameter))
		.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestParameterException));
		
		mockMvc.perform(get("/firestation?stationNumber=" + parameter))
		.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestParameterException));
		
		mockMvc.perform(get("/personInfo?firstName=" + parameter + "&lastName=" + parameter))
		.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestParameterException));
	}
	
	@Test
	void isExpectedExceptionHandledWhenRequestParameterIsNotAlphabetical() throws Exception{
		
		String parameter ="0";
		
		mockMvc.perform(get("/communityEmail?city=" + parameter))
		.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestParameterException));
				
		mockMvc.perform(get("/personInfo?firstName=" + parameter + "&lastName=" + parameter))
		.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestParameterException));
	}
	
	@Test
	void isExpectedExceptionHandledWhenRequestParameterIsNotNumeric() throws Exception{
		
		String parameter ="A";
	
		mockMvc.perform(get("/firestation?stationNumber=" + parameter))
		.andExpect(result-> assertTrue(result.getResolvedException() instanceof RequestParameterException));
	}
	
}
