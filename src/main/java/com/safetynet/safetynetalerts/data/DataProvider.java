package com.safetynet.safetynetalerts.data;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>In order to respect the SOLID principles, this class stands as an utility class 
 * that provides data required tools instances to the DAO layer.</p>
 * @author newbie
 * @see ObjectMapper
 * @see DataContainer
 */
@Component
public  class DataProvider {

	private ObjectMapper objectMapper = new ObjectMapper();
	private DataContainer dataContainer = new DataContainer();
	
	public ObjectMapper getObjectMapper() {
		
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		
		return objectMapper;
	}
	
	public DataContainer getDataContainer() {
		return dataContainer;
	}
	
	
	
	
	
	
}
