package com.safetynet.safetynetalerts.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;


@Component
public class DataAccessor extends Properties {

	
	private static final long serialVersionUID = 1L;

	public DataAccessor() {
		super();
		try (InputStream in = getClass().getClassLoader().getResourceAsStream("data.properties")){
			this.load(in);
		} catch (IOException e) {
			throw new DataImportFailedException("A problem occured while loading the data properties file required to acces data.", e);
		}
	}

	public String getFilePath() {
		return getProperty("json.file");
	}

	public String getMedicalRecordsNode() {
		return getProperty("json.medicalRecordsNode");
	}

	public String getPersonsNode() {
		return getProperty("json.personsNode");
	}

	public String getLinkedFireStationsNode() {
		return getProperty("json.linkedFireStationsNode");
	}
	
	
}