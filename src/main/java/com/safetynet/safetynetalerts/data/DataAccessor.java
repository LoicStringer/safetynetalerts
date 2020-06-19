package com.safetynet.safetynetalerts.data;

import java.io.IOException;
import java.util.Properties;

public class DataAccessor extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataAccessor() {
		super();
		try {
			this.load(getClass().getClassLoader().getResourceAsStream("data.properties"));
		} catch (IOException e) {
			System.out.println("Erreur lors du chargement du fichier");
			e.printStackTrace();
		}
	}

	public String getFiletpath() {
		return getProperty("json.file");
	}

	public String getMedicalRecordsNode() {
		return getProperty("json.medicalrecordsNode");
	}

	public String getPersonsNode() {
		return getProperty("json.personsNode");
	}

	public String getFireStationsNode() {
		return getProperty("json.firestationsNode");

	}
}