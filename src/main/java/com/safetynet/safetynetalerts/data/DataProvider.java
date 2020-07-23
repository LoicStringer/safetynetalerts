package com.safetynet.safetynetalerts.data;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public abstract class DataProvider {

	
	private ObjectMapper objectMapper = new ObjectMapper();
	private DataAccessor dataAccessor = new DataAccessor();
	private DataContainer dataContainer = new DataContainer();
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	
	public DataAccessor getDataAccessor() {
		return dataAccessor;
	}
	
	public DataContainer getDataContainer() {
		return dataContainer;
	}
	
	
	
	
	
	
}
