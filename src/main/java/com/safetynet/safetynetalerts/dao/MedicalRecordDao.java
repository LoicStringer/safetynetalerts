package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.exceptions.DaoException;
import com.safetynet.safetynetalerts.exceptions.DataContainerException;
import com.safetynet.safetynetalerts.model.MedicalRecord;


@Component
public class MedicalRecordDao extends DataProvider implements IDao<MedicalRecord> {

	@Override
	public List<MedicalRecord> getAll() throws DaoException {
		
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		
		try {
			ArrayNode medicalRecordsNode = getDataContainer().getMedicalRecordsData();
			Iterator<JsonNode> elements = medicalRecordsNode.elements();
			
			while (elements.hasNext()) {
				JsonNode medicalRecordNode = elements.next();
				MedicalRecord medicalRecord = getObjectMapper().convertValue(medicalRecordNode, MedicalRecord.class);
				medicalRecords.add(medicalRecord);
			}
		} catch (DataContainerException e) {
			e.printStackTrace();
			throw new DaoException("A problem occured while querying Medical Records from the data container",e);
		}
		return medicalRecords;
	}

	@Override
	public MedicalRecord getOne(String identifier) throws DaoException {
		MedicalRecord medicalRecordToGet = new MedicalRecord();
		try {
			ArrayNode medicalRecordsData = getDataContainer().getMedicalRecordsData();

			Iterator<JsonNode> elements = medicalRecordsData.elements();

			while (elements.hasNext()) {
				JsonNode medicalRecordNode = elements.next();
				String identifierToFind = medicalRecordNode.get("firstName").asText()
						+ medicalRecordNode.get("lastName").asText();
				if(identifierToFind.equalsIgnoreCase(identifier)) {
					medicalRecordToGet = getObjectMapper().convertValue(medicalRecordNode, MedicalRecord.class);
					break;
				}
			}
		} catch (DataContainerException e) {
			e.printStackTrace();
			throw new DaoException("A problem occured while querying the specified "+identifier+" Medical Record from the data container",e);
		}
		
		return medicalRecordToGet;
	}
	
	@Override
	public MedicalRecord insert(MedicalRecord medicalRecord) throws DaoException {
		
		JsonNode medicalRecordNode = getObjectMapper().convertValue(medicalRecord, JsonNode.class);
		
		try {
			ArrayNode medicalRecordsData = getDataContainer().getMedicalRecordsData();
			medicalRecordsData.add(medicalRecordNode);
			getDataContainer().setMedicalRecordsData(medicalRecordsData);	
		} catch (DataContainerException e) {
			e.printStackTrace();
			throw new DaoException("A problem occured while inserting Person "+ medicalRecord.toString(), e);
		}
		
		return medicalRecord;
	}

	@Override
	public MedicalRecord update(MedicalRecord medicalRecord) throws DaoException {
		
		String identifier = medicalRecord.getFirstName() + medicalRecord.getLastName();
		
		List<MedicalRecord> medicalrecords = this.getAll();
		MedicalRecord medicalRecordToUpdate = this.getOne(identifier);
		int index = medicalrecords.indexOf(medicalRecordToUpdate);
		medicalrecords.set(index, medicalRecord);
	
		ArrayNode newMedicalRecordsData = getObjectMapper().valueToTree(medicalrecords);
		getDataContainer().setMedicalRecordsData(newMedicalRecordsData);
		
		return medicalRecord;
	}

	@Override
	public MedicalRecord delete(MedicalRecord medicalRecord) throws DaoException {
		
		List<MedicalRecord> medicalRecords = this.getAll();
		
		MedicalRecord medicalRecordToDelete = this.getOne(medicalRecord.getFirstName()+medicalRecord.getLastName());
		int index = medicalRecords.indexOf(medicalRecordToDelete);
		medicalRecords.remove(index);
		
		ArrayNode newMedicalRecordsData = getObjectMapper().valueToTree(medicalRecords);
		getDataContainer().setMedicalRecordsData(newMedicalRecordsData);
		
		return medicalRecord;
	}

	

}
