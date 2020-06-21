package com.safetynet.safetynetalerts.data;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class DataProvider {

	
	public ObjectMapper objectMapper = new ObjectMapper();
	public DataAccessor dataAccessor = new DataAccessor();
	
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	public DataAccessor getDataAccessor() {
		return dataAccessor;
	}
	
	
	
	
	
	
	
}
