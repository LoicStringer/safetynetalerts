package com.safetynet.safetynetalerts.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.data.DataAccessor;
import com.safetynet.safetynetalerts.data.DataContainer;

public abstract class AbstractDataDao {
	
	private DataContainer dataContainer = new DataContainer();
	private ObjectMapper objectMapper = new ObjectMapper();
	private DataAccessor dataAccessor = new DataAccessor();
	
	protected JsonNode getAllData() {
		JsonNode data = null;
		data = dataContainer.containsData();
		return data;
	}

	protected JsonNode getPersonsData() {
		JsonNode personsData = null;
		personsData = dataContainer.containsData().get(dataAccessor.getPersonsNode());
		return personsData;
	}
	
	protected JsonNode getMedicalRecordsData() {
		JsonNode medicalRecordsData = null;
		medicalRecordsData = dataContainer.containsData().get(dataAccessor.getMedicalRecordsNode());
		return medicalRecordsData;
	}
	
	protected JsonNode getLinkedFirestationData() {
		JsonNode linkedFireStationsData = null;
		linkedFireStationsData = dataContainer.containsData().get(dataAccessor.getLinkedFireStationsNode());
		return linkedFireStationsData;
	}
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	
	
	
}
