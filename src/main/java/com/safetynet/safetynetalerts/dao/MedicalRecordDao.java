package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;


@Component
public class MedicalRecordDao extends DataProvider implements IDao<MedicalRecord> {

	@Override
	public List<MedicalRecord> getAll() {
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		ArrayNode medicalRecordsNode = getDataContainer().getMedicalRecordsData();
		Iterator<JsonNode> elements = medicalRecordsNode.elements();
		
		while (elements.hasNext()) {
			JsonNode medicalRecordNode = elements.next();
			MedicalRecord medicalRecord = getObjectMapper().convertValue(medicalRecordNode, MedicalRecord.class);
			medicalRecords.add(medicalRecord);
		}
		
		return medicalRecords;
	}

	@Override
	public MedicalRecord getOne(String identifier) {
		ArrayNode medicalRecordsData = getDataContainer().getMedicalRecordsData();
		MedicalRecord medicalRecordToGet = new MedicalRecord();
		Iterator<JsonNode> elements = medicalRecordsData.elements();

		while (elements.hasNext()) {
			JsonNode medicalRecordNode = elements.next();
			String name = medicalRecordNode.findValue("firstName").asText() 
					+ medicalRecordNode.findValue("lastName").asText();
			if(name.equals(identifier))
				medicalRecordToGet = getObjectMapper().convertValue(medicalRecordNode, MedicalRecord.class);
				break;
		}
		return medicalRecordToGet;
	}
	
	@Override
	public boolean insert(MedicalRecord medicalRecord) {
		boolean isSaved = false;
		JsonNode medicalRecordNode = getObjectMapper().convertValue(medicalRecord, JsonNode.class);
		ArrayNode medicalRecordsData = getDataContainer().getMedicalRecordsData();
		int size = medicalRecordsData.size();
		
		medicalRecordsData.add(medicalRecordNode);
		getDataContainer().setMedicalRecordsData(medicalRecordsData);	
		
		if(medicalRecordsData.size() == (size+1))
			isSaved = true;
		
		return isSaved;
	}

	@Override
	public boolean update(MedicalRecord medicalRecord) {
		boolean isUpdated = false;
		String identifier = medicalRecord.getFirstName() + medicalRecord.getLastName();
		
		List<MedicalRecord> medicalrecords = this.getAll();
		MedicalRecord medicalRecordToUpdate = this.getOne(identifier);
		int index = medicalrecords.indexOf(medicalRecordToUpdate);
		medicalrecords.set(index, medicalRecord);
		
		if(medicalrecords.get(index) != medicalRecordToUpdate)
			isUpdated = true;

		ArrayNode newMedicalRecordsData = getObjectMapper().valueToTree(medicalrecords);
		getDataContainer().setMedicalRecordsData(newMedicalRecordsData);
		
		return isUpdated;
	}

	@Override
	public boolean delete(MedicalRecord medicalRecord) {
		boolean isDeleted = false;
		String identifier = medicalRecord.getFirstName() + medicalRecord.getLastName();
		
		List<MedicalRecord> medicalRecords = this.getAll();
		int size = medicalRecords.size();
		MedicalRecord medicalRecordToDelete = this.getOne(identifier);
		int index = medicalRecords.indexOf(medicalRecordToDelete);
		medicalRecords.remove(index);
		
		if(medicalRecords.size() == (size-1))
			isDeleted = true;
		
		ArrayNode newMedicalRecordsData = getObjectMapper().valueToTree(medicalRecords);
		getDataContainer().setMedicalRecordsData(newMedicalRecordsData);
		
		return isDeleted;
	}

	

}
