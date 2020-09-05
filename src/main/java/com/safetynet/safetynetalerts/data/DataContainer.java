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

/**
 * <p>A class that aims at reproducing a kind of database 
 * in order to separate data from applicative methods in a static way.</p>
 * @author newbie
 *
 */
@Component
public class DataContainer {

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static DataAccessor dataAccessor = new DataAccessor();

	public static ArrayNode medicalRecordsData;
	public static ArrayNode linkedFireStationsData;
	public static ArrayNode personsData;

	/**
	 * Loads the JSON data file at the application start-up. 
	 */
	static {
		try {
			personsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFilePath()))
					.get(dataAccessor.getPersonsNode());
			medicalRecordsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFilePath()))
					.get(dataAccessor.getMedicalRecordsNode());
			linkedFireStationsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFilePath()))
					.get(dataAccessor.getLinkedFireStationsNode());
		} catch (JsonProcessingException e) {
			throw new DataImportFailedException("A problem occured while Json being parsed", e);
		} catch (IOException e) {
			throw new DataImportFailedException("A problem occured while reading the Json file", e);
		}
	}

	public void checkDataIntegrity(ArrayNode arraynode) throws UnavailableDataException, EmptyDataException {

		if (arraynode == null) {
			throw new UnavailableDataException("Warning : the data source or the file path is invalid !");
		} else if (arraynode.isEmpty()) {
			throw new EmptyDataException("Warning : the data source is empty !");
		}

	}

	/**
	 *<p>Method written to make tests easier, 
	 * reloading the JSON data file as it was at the application start-up.
	 * Only used in JUnit tests classes.</p>
	 */
	public static void reloadDataForTests() {

		try {
			personsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFilePath()))
					.get(dataAccessor.getPersonsNode());
			medicalRecordsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFilePath()))
					.get(dataAccessor.getMedicalRecordsNode());
			linkedFireStationsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFilePath()))
					.get(dataAccessor.getLinkedFireStationsNode());
		} catch (JsonProcessingException e) {
			throw new DataImportFailedException("A problem occured while Json being parsed", e);
		} catch (IOException e) {
			throw new DataImportFailedException("A problem occured while reading the Json file", e);
		}
	}

}