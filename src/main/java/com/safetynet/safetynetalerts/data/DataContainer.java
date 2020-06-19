package com.safetynet.safetynetalerts.data;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataContainer {

	private DataAccessor dataAccessor = new DataAccessor() ;
	private ObjectMapper om = new ObjectMapper();
	
	@PostConstruct
	public JsonNode containsData() {
		JsonNode data = null;
		try {
			data = om.readTree(new File(dataAccessor.getFiletpath()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
/*
	public JsonNode containsPersonsData() {
		JsonNode personData = null;
		personData = containsData().get(dataAccessor.getPersonsNode());
		return personData;
	}
	
	public JsonNode containsMedicalRecordsData() {
		JsonNode personData = null;
		personData = containsData().get(dataAccessor.getMedicalRecordsNode());
		return personData;
	}
	
	public JsonNode containsLinkedFireStationsData() {
		JsonNode personData = null;
		personData = containsData().get(dataAccessor.getLinkedFireStationsNode());
		return personData;
	}
*/
	
	
	
}