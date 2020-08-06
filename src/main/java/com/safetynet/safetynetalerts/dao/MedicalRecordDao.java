package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@Component
public class MedicalRecordDao extends DataProvider implements IDao<MedicalRecord> {

	@Override
	public List<MedicalRecord> getAll() throws DataImportFailedException, UnavailableDataException, EmptyDataException {

		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		ArrayNode medicalRecordsNode;

		try {
			medicalRecordsNode = getDataContainer().getMedicalRecordsData();
		} catch (DataImportFailedException e) {
			throw new DataImportFailedException(
					"A problem occured while querying medical records list from the data container", e);
		} catch (UnavailableDataException e) {
			throw new UnavailableDataException("Medical records list is null", e);
		} catch (EmptyDataException e) {
			throw new EmptyDataException("Medical records list is empty", e);
		}

		Iterator<JsonNode> elements = medicalRecordsNode.elements();
		while (elements.hasNext()) {
			JsonNode medicalRecordNode = elements.next();
			MedicalRecord medicalRecord = getObjectMapper().convertValue(medicalRecordNode, MedicalRecord.class);
			medicalRecords.add(medicalRecord);
		}

		return medicalRecords;
	}

	@Override
	public MedicalRecord getOne(String identifier) throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {
		
		MedicalRecord medicalRecordToGet = new MedicalRecord();

		ArrayNode medicalRecordsData;
		
		try {
			medicalRecordsData = getDataContainer().getMedicalRecordsData();
		} catch (DataImportFailedException e) {
			throw new DataImportFailedException("A problem occured while querying medical records list from the data container",
					e);
		} catch (UnavailableDataException e) {
			throw new UnavailableDataException("Medical records list is null", e);
		} catch (EmptyDataException e) {
			throw new EmptyDataException("Medical records list is empty", e);
		}

		Iterator<JsonNode> elements = medicalRecordsData.elements();
		while (elements.hasNext()) {
			JsonNode medicalRecordNode = elements.next();
			String identifierToFind = medicalRecordNode.get("firstName").asText()
					+ medicalRecordNode.get("lastName").asText();
			if (identifierToFind.equalsIgnoreCase(identifier)) {
				medicalRecordToGet = getObjectMapper().convertValue(medicalRecordNode, MedicalRecord.class);
				break;
			}
		}
		if (medicalRecordToGet.getFirstName() == null && medicalRecordToGet.getLastName() == null)
			throw new ItemNotFoundException("No medical record found for identifier " + identifier);

		return medicalRecordToGet;
	}

	@Override
	public MedicalRecord insert(MedicalRecord medicalRecord) throws DataImportFailedException, UnavailableDataException, EmptyDataException, DuplicatedItemException {

		this.checkForDuplication(medicalRecord);

		ArrayNode medicalRecordsData;
		
		try {
			medicalRecordsData = getDataContainer().getMedicalRecordsData();
		} catch (DataImportFailedException e) {
			throw new DataImportFailedException("A problem occured while querying medical records list from the data container",
					e);
		} catch (UnavailableDataException e) {
			throw new UnavailableDataException("Medical records list is null", e);
		} catch (EmptyDataException e) {
			throw new EmptyDataException("Medical records list is empty", e);
		}
		
		JsonNode medicalRecordNode = getObjectMapper().convertValue(medicalRecord, JsonNode.class);
		medicalRecordsData.add(medicalRecordNode);
		getDataContainer().setMedicalRecordsData(medicalRecordsData);

		return medicalRecord;
	}

	@Override
	public MedicalRecord update(MedicalRecord medicalRecord) throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {

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
	public MedicalRecord delete(MedicalRecord medicalRecord) throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {

		List<MedicalRecord> medicalRecords = this.getAll();

		MedicalRecord medicalRecordToDelete = this.getOne(medicalRecord.getFirstName() + medicalRecord.getLastName());
		int index = medicalRecords.indexOf(medicalRecordToDelete);
		medicalRecords.remove(index);

		ArrayNode newMedicalRecordsData = getObjectMapper().valueToTree(medicalRecords);
		getDataContainer().setMedicalRecordsData(newMedicalRecordsData);

		return medicalRecord;
	}

	private void checkForDuplication(MedicalRecord medicalRecord) throws DataImportFailedException, UnavailableDataException, EmptyDataException, DuplicatedItemException {
		if (this.getAll().stream().anyMatch(mr -> (mr.getFirstName() + mr.getLastName())
				.equalsIgnoreCase(medicalRecord.getFirstName() + medicalRecord.getLastName())))
			throw new DuplicatedItemException(
					"Warning : a medical record with the same firstname and lastname already exists in data container");
	}

}
