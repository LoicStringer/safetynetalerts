package com.safetynet.safetynetalerts.data;

import com.fasterxml.jackson.databind.ObjectMapper;

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
