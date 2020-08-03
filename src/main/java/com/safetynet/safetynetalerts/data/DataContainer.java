package com.safetynet.safetynetalerts.data;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.exceptions.DataContainerException;

public class DataContainer {

	private ObjectMapper objectMapper = new ObjectMapper();
	private DataAccessor dataAccessor = new DataAccessor();
	private ArrayNode medicalRecordsData;
	private ArrayNode linkedFireStationsData;
	private ArrayNode personsData;

	@PostConstruct
	public ArrayNode getPersonsData() throws DataContainerException  {
		if (personsData == null)
			try {
				personsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFiletpath()))
						.get(dataAccessor.getPersonsNode());
			} catch (JsonProcessingException e) {
				System.out.println("A problem occured while Json file being parsed");
				e.printStackTrace();
				throw new DataContainerException("A problem occured while Json being parsed", e);
			} catch (IOException e) {
				System.out.println("A problem occured while reading the Json file");
				e.printStackTrace();
				throw new DataContainerException("A problem occured while reading the Json file", e);
			}finally {
				if (personsData==null) {
					throw new DataContainerException("Warning : the data source or the file path is invalid !");
				}else if (personsData.isEmpty()) {
					throw new DataContainerException("Warning : the data source is empty !");
				}
			}

		return personsData;
	}

	@PostConstruct
	public ArrayNode getMedicalRecordsData() throws DataContainerException {
		if (medicalRecordsData == null)
			try {
				medicalRecordsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFiletpath()))
						.get(dataAccessor.getMedicalRecordsNode());
			} catch (JsonProcessingException e) {
				System.out.println("A problem occured while Json file being parsed");
				e.printStackTrace();
				throw new DataContainerException("A problem occured while Json being parsed", e);
			} catch (IOException e) {
				System.out.println("A problem occured while reading the Json file");
				e.printStackTrace();
				throw new DataContainerException("A problem occured while reading the Json file", e);
			}finally {
				if (medicalRecordsData==null) {
					throw new DataContainerException("Warning : the data source or the file path is invalid !");
				}else if(medicalRecordsData.isEmpty()){
					throw new DataContainerException("Warning : the data source is empty !");
				}
			}

		return medicalRecordsData;
	}

	@PostConstruct
	public ArrayNode getLinkedFireStationsData() throws DataContainerException {
		if (linkedFireStationsData == null)
			try {
				linkedFireStationsData = (ArrayNode) objectMapper.readTree(new File(dataAccessor.getFiletpath()))
						.get(dataAccessor.getLinkedFireStationsNode());
			} catch (JsonProcessingException e) {
				System.out.println("A problem occured while Json being parsed");
				e.printStackTrace();
				throw new DataContainerException("A problem occured while Json file being parsed", e);
			} catch (IOException e) {
				System.out.println("A problem occured while reading the Json file");
				e.printStackTrace();
				throw new DataContainerException("A problem occured while reading the Json file", e);
			} finally {
				if (linkedFireStationsData==null) {
					throw new DataContainerException("Warning : the data source or file path is invalid !");
				}else if (linkedFireStationsData.isEmpty()) {
					throw new DataContainerException("Warning : the data source is empty !");
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