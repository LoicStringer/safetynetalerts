package com.safetynet.safetynetalerts.data;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class DataContainer  {

	private ObjectMapper objectMapper = new ObjectMapper();
	private DataAccessor dataAccessor = new DataAccessor();
	private ArrayNode medicalRecordsData ;
	private ArrayNode linkedFireStationsData;
	private ArrayNode personsData;

	@PostConstruct
	public ArrayNode getPersonsData() {
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
	
	public ArrayNode getMedicalRecordsData() {
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

	public ArrayNode getLinkedFireStationsData() {
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