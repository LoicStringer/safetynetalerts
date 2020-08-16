package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataContainer;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@Component
public class MedicalRecordDao extends DataProvider implements IDao<MedicalRecord> {

	@Override
	public List<MedicalRecord> getAll() throws UnavailableDataException, EmptyDataException  {
		
		try {
			getDataContainer().checkDataIntegrity(DataContainer.medicalRecordsData);
		} catch (UnavailableDataException e) {
			throw new UnavailableDataException("Medical records list is null", e);
		} catch (EmptyDataException e) {
			throw new EmptyDataException("Medical records list is empty", e);
		}
		
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		
		Iterator<JsonNode> elements = DataContainer.medicalRecordsData.elements();
		while (elements.hasNext()) {
			JsonNode medicalRecordNode = elements.next();
			MedicalRecord medicalRecord = getObjectMapper().convertValue(medicalRecordNode, MedicalRecord.class);
			medicalRecords.add(medicalRecord);
		}

		return medicalRecords;
	}

	@Override
	public MedicalRecord getOne(String identifier) throws UnavailableDataException, EmptyDataException, ItemNotFoundException {
		
		MedicalRecord medicalRecordToGet = new MedicalRecord();
		List<MedicalRecord> medicalRecords = this.getAll();
		
		medicalRecordToGet = medicalRecords.stream()
				.filter(mr -> (mr.getFirstName()+mr.getLastName()).equalsIgnoreCase(identifier))
				.findAny().orElse(null);
		
		if (medicalRecordToGet == null)
			throw new ItemNotFoundException("No medical record found for identifier " + identifier);

		return medicalRecordToGet;
	}

	@Override
	public MedicalRecord insert(MedicalRecord medicalRecord) throws UnavailableDataException, EmptyDataException, DuplicatedItemException {

		this.checkForDuplication(medicalRecord);
	
		JsonNode medicalRecordNode = getObjectMapper().convertValue(medicalRecord, JsonNode.class);
		DataContainer.medicalRecordsData.add(medicalRecordNode);
		
		return medicalRecord;
	}

	@Override
	public MedicalRecord update(MedicalRecord medicalRecord) throws  UnavailableDataException, EmptyDataException, ItemNotFoundException {

		List<MedicalRecord> medicalrecords = this.getAll();
		MedicalRecord medicalRecordToUpdate = this.getOne(medicalRecord.getFirstName()+medicalRecord.getLastName());
		
		int index = medicalrecords.indexOf(medicalRecordToUpdate);
		medicalrecords.set(index, medicalRecord);

		ArrayNode newMedicalRecordsData = getObjectMapper().valueToTree(medicalrecords);
		DataContainer.medicalRecordsData = newMedicalRecordsData;

		return medicalRecord;
	}

	@Override
	public MedicalRecord delete(MedicalRecord medicalRecord) throws  UnavailableDataException, EmptyDataException, ItemNotFoundException {

		List<MedicalRecord> medicalRecords = this.getAll();
		MedicalRecord medicalRecordToDelete = this.getOne(medicalRecord.getFirstName() + medicalRecord.getLastName());
		
		int index = medicalRecords.indexOf(medicalRecordToDelete);
		medicalRecords.remove(index);

		ArrayNode newMedicalRecordsData = getObjectMapper().valueToTree(medicalRecords);
		DataContainer.medicalRecordsData = newMedicalRecordsData;

		return medicalRecord;
	}

	private void checkForDuplication(MedicalRecord medicalRecord) throws  UnavailableDataException, EmptyDataException, DuplicatedItemException {
		if (this.getAll().stream().anyMatch(mr -> (mr.getFirstName() + mr.getLastName())
				.equalsIgnoreCase(medicalRecord.getFirstName() + medicalRecord.getLastName())))
			throw new DuplicatedItemException(
					"Warning : a medical record with the same firstname and lastname "+medicalRecord.getFirstName()+" "+medicalRecord.getLastName()+" already exists in data container");
	}

}
