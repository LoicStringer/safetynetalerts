package com.safetynet.safetynetalerts.data;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;


@Component
public abstract class DataProvider {

	private ObjectMapper objectMapper = new ObjectMapper();
	private DataAccessor dataAccessor = new DataAccessor();
	private DataContainer dataContainer = new DataContainer();
	
	public ObjectMapper getObjectMapper() {
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
		return objectMapper;
	}
	
	public DataAccessor getDataAccessor() {
		return dataAccessor;
	}
	
	public DataContainer getDataContainer() {
		return dataContainer;
	}
	
	
	
	
	
	
	
}
