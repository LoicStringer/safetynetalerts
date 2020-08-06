package com.safetynet.safetynetalerts.data;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;

public class DataContainer {

	private ObjectMapper objectMapper = new ObjectMapper();
	private DataAccessor dataAccessor = new DataAccessor();
	private ArrayNode medicalRecordsData;
	private ArrayNode linkedFireStationsData;
	private ArrayNode personsData;

	@PostConstruct
	public ArrayNode getPersonsData() throws DataImportFailedException, UnavailableDataException, EmptyDataException  {
		
		if (personsData == null)
			try {
				personsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFiletpath()))
						.get(dataAccessor.getPersonsNode());
			} catch (JsonProcessingException e) {
				throw new DataImportFailedException("A problem occured while Json being parsed", e);
			} catch (IOException e) {
				throw new DataImportFailedException("A problem occured while reading the Json file", e);
			}finally {
				if (personsData==null) {
					throw new UnavailableDataException("Warning : the data source or the file path is invalid !");
				}else if (personsData.isEmpty()) {
					throw new EmptyDataException("Warning : the data source is empty !");
				}
			}

		return personsData;
	}

	@PostConstruct
	public ArrayNode getMedicalRecordsData() throws DataImportFailedException, UnavailableDataException, EmptyDataException {
		
		if (medicalRecordsData == null)
			try {
				medicalRecordsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFiletpath()))
						.get(dataAccessor.getMedicalRecordsNode());
			} catch (JsonProcessingException e) {
				throw new DataImportFailedException("A problem occured while Json being parsed", e);
			} catch (IOException e) {
				throw new DataImportFailedException("A problem occured while reading the Json file", e);
			}finally {
				if (medicalRecordsData==null) {
					throw new UnavailableDataException("Warning : the data source or the file path is invalid !");
				}else if(medicalRecordsData.isEmpty()){
					throw new EmptyDataException("Warning : the data source is empty !");
				}
			}

		return medicalRecordsData;
	}

	@PostConstruct
	public ArrayNode getLinkedFireStationsData() throws DataImportFailedException, UnavailableDataException, EmptyDataException {
		
		if (linkedFireStationsData == null)
			try {
				linkedFireStationsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFiletpath()))
						.get(dataAccessor.getLinkedFireStationsNode());
			} catch (JsonProcessingException e) {
				throw new DataImportFailedException("A problem occured while Json file being parsed", e);
			} catch (IOException e) {
				throw new DataImportFailedException("A problem occured while reading the Json file", e);
			} finally {
				if (linkedFireStationsData==null) {
					throw new UnavailableDataException("Warning : the data source or file path is invalid !");
				}else if (linkedFireStationsData.isEmpty()) {
					throw new EmptyDataException("Warning : the data source is empty !");
				}
			}
		
		return linkedFireStationsData;
	}

	public void setMedicalRecordsData(ArrayNode newMedicalRecordsData) {
		this.medicalRecordsData = newMedicalRecordsData;
	}

	public void setLinkedFireStationsData(ArrayNode newLinkedFireStationsData) {
		this.linkedFireStationsData = newLinkedFireStationsData;
	}

	public void setPersonsData(ArrayNode newPersonsData) {
		this.personsData = newPersonsData;
	}

}