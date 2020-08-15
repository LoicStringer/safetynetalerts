package com.safetynet.safetynetalerts.data;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;

@Component
public class DataContainer {

	private ObjectMapper objectMapper = new ObjectMapper();
	private DataAccessor dataAccessor = new DataAccessor();

	public static ArrayNode medicalRecordsData;
	public static ArrayNode linkedFireStationsData;
	public static ArrayNode personsData;

	public ArrayNode getPersonsData() throws DataImportFailedException, UnavailableDataException, EmptyDataException {

		ArrayNode extractedPersonsData = null;

		if (personsData == null) {
			try {
				extractedPersonsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFilePath()))
						.get(dataAccessor.getPersonsNode());
			} catch (JsonProcessingException e) {
				throw new DataImportFailedException("A problem occured while Json being parsed", e);
			} catch (IOException e) {
				throw new DataImportFailedException("A problem occured while reading the Json file", e);
			} finally {
				if (extractedPersonsData == null) {
					throw new UnavailableDataException("Warning : the data source or the file path is invalid !");
				} else if (extractedPersonsData.isEmpty()) {
					throw new EmptyDataException("Warning : the data source is empty !");
				}
			}

			setPersonsData(extractedPersonsData);
		}

		return personsData;
	}

	public ArrayNode getMedicalRecordsData()
			throws DataImportFailedException, UnavailableDataException, EmptyDataException {

		ArrayNode extractedMedicalRecordsData = null;
		
		if (medicalRecordsData == null) {
			try {
				extractedMedicalRecordsData = (ArrayNode) objectMapper
						.readTree(new File(dataAccessor.getFilePath())).get(dataAccessor.getMedicalRecordsNode());
			} catch (JsonProcessingException e) {
				throw new DataImportFailedException("A problem occured while Json being parsed", e);
			} catch (IOException e) {
				throw new DataImportFailedException("A problem occured while reading the Json file", e);
			} finally {
				if (extractedMedicalRecordsData == null) {
					throw new UnavailableDataException("Warning : the data source or the file path is invalid !");
				} else if (extractedMedicalRecordsData.isEmpty()) {
					throw new EmptyDataException("Warning : the data source is empty !");
				}
			}
			setMedicalRecordsData(extractedMedicalRecordsData);
		}

		return medicalRecordsData;
	}

	public ArrayNode getLinkedFireStationsData()
			throws DataImportFailedException, UnavailableDataException, EmptyDataException {

		ArrayNode extractedLinkedFireStationsData = null;
		
		if(linkedFireStationsData==null) {
			try {
				extractedLinkedFireStationsData = (ArrayNode) objectMapper
						.readTree(new File(dataAccessor.getFilePath())).get(dataAccessor.getLinkedFireStationsNode());
			} catch (JsonProcessingException e) {
				throw new DataImportFailedException("A problem occured while Json file being parsed", e);
			} catch (IOException e) {
				throw new DataImportFailedException("A problem occured while reading the Json file", e);
			} finally {
				if (extractedLinkedFireStationsData == null) {
					throw new UnavailableDataException("Warning : the data source or file path is invalid !");
				} else if (extractedLinkedFireStationsData.isEmpty()) {
					throw new EmptyDataException("Warning : the data source is empty !");
				}
			}
			setLinkedFireStationsData(extractedLinkedFireStationsData);
		}
	
		return linkedFireStationsData;
	}

	public static void setMedicalRecordsData(ArrayNode medicalRecordsData) {
		DataContainer.medicalRecordsData = medicalRecordsData;
	}

	public static void setLinkedFireStationsData(ArrayNode linkedFireStationsData) {
		DataContainer.linkedFireStationsData = linkedFireStationsData;
	}

	public static void setPersonsData(ArrayNode personsData) {
		DataContainer.personsData = personsData;
	}

}