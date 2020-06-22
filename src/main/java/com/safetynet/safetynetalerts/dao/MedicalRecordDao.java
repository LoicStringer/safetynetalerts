package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.model.MedicalRecord;

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
		
		MedicalRecord medicalRecordToUpdate = medicalrecords.stream().filter(mr -> identifier.equals(mr.getFirstName() + mr.getLastName())).findAny().orElse(null);
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
		
		MedicalRecord medicalRecordToDelete = medicalRecords.stream().filter(mr -> identifier.equals(mr.getFirstName() + mr.getLastName())).findAny().orElse(null);
		int index = medicalRecords.indexOf(medicalRecordToDelete);
		medicalRecords.remove(index);
		
		if(medicalRecords.size() == (size-1))
			isDeleted = true;
		
		ArrayNode newMedicalRecordsData = getObjectMapper().valueToTree(medicalRecords);
		getDataContainer().setMedicalRecordsData(newMedicalRecordsData);
		
		return isDeleted;
	}

}
