package com.safetynet.safetynetalerts.data;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class DataContainer {

	private static DataAccessor dataAccessor = new DataAccessor();
	private static ObjectMapper objectMapper = new ObjectMapper();
	public static ArrayNode medicalRecordsData;
	public static ArrayNode linkedFireStationsData;
	public static ArrayNode personsData;

	@PostConstruct
	public static ArrayNode getPersonsData() {
		if (personsData == null)
			try {
				personsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFiletpath()))
						.get(dataAccessor.getPersonsNode());
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return personsData;
	}
	
	public static ArrayNode getMedicalRecordsData() {
		if (medicalRecordsData == null)
			try {
				medicalRecordsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFiletpath()))
						.get(dataAccessor.getMedicalRecordsNode());
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return medicalRecordsData;
	}

	public static ArrayNode getLinkedFireStationsData() {
		if (linkedFireStationsData == null)
			try {
				linkedFireStationsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFiletpath()))
						.get(dataAccessor.getLinkedFireStationsNode());
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return linkedFireStationsData;
	}

	public static void setMedicalRecordsData(ArrayNode newMedicalRecordsData) {
		DataContainer.medicalRecordsData = newMedicalRecordsData;
	}

	public static void setLinkedFireStationsData(ArrayNode newLinkedFireStationsData) {
		DataContainer.linkedFireStationsData = newLinkedFireStationsData;
	}

	public static void setPersonsData(ArrayNode newPersonsData) {
		DataContainer.personsData = newPersonsData;
	}

}