package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataContainer;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.exceptions.DuplicatedMedicalRecordException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@Component
public class MedicalRecordDao extends DataProvider implements IDao<MedicalRecord> {

	private Logger log =LoggerFactory.getLogger(this.getClass());
	
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
	public MedicalRecord getOne(String identifier) throws UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException {
		
		MedicalRecord medicalRecordToGet = new MedicalRecord();
		List<MedicalRecord> medicalRecords = this.getAll();
		
		medicalRecordToGet = medicalRecords.stream()
				.filter(mr -> (mr.getFirstName()+mr.getLastName()).equalsIgnoreCase(identifier))
				.findAny().orElse(null);
		
		if (medicalRecordToGet == null)
			throw new MedicalRecordNotFoundException("No medical record found for identifier " + identifier);

		return medicalRecordToGet;
	}

	public List<MedicalRecord> getDuplicatedMedicalRecords(String identifier) 
			throws UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException{
		
		List<MedicalRecord> homonymousMedicalRecords = this.getAll().stream()
				.filter(mr -> (mr.getFirstName()+mr.getLastName()).equalsIgnoreCase(identifier))
				.collect(Collectors.toList());
		
		if(homonymousMedicalRecords.isEmpty())
			throw new MedicalRecordNotFoundException("No medical record found for identifier " + identifier);
		
		return homonymousMedicalRecords;
	}
	
	
	@Override
	public MedicalRecord insert(MedicalRecord medicalRecord) throws UnavailableDataException, EmptyDataException {
		
		long duplicated = checkForDuplication(medicalRecord.getFirstName()+medicalRecord.getLastName());
		
		if(duplicated>=1) 
			log.warn("Warning! "+duplicated+" medical records identified by "+medicalRecord.getFirstName()+" "+medicalRecord.getLastName()+" is/are registered in data container.");
	
		JsonNode medicalRecordNode = getObjectMapper().convertValue(medicalRecord, JsonNode.class);
		DataContainer.medicalRecordsData.add(medicalRecordNode);
		
		return medicalRecord;
	}

	@Override
	public MedicalRecord update(MedicalRecord medicalRecordUpdated) 
			throws  UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException, DuplicatedMedicalRecordException {

		long duplicated = checkForDuplication(medicalRecordUpdated.getFirstName()+medicalRecordUpdated.getLastName());
		
		if(duplicated>1) {
			List<MedicalRecord> homonymousMedicalRecords = this.getDuplicatedMedicalRecords(medicalRecordUpdated.getFirstName()+medicalRecordUpdated.getLastName());
			throw new DuplicatedMedicalRecordException("Warning! "+duplicated+" medical records identified by "+medicalRecordUpdated.getFirstName()+" "+medicalRecordUpdated.getLastName()
			+" is/are registered in data container: "
			+homonymousMedicalRecords+" . Not able to determine which one has to be updated.");
		}
		
		List<MedicalRecord> medicalrecords = this.getAll();
		MedicalRecord medicalRecordToUpdate = this.getOne(medicalRecordUpdated.getFirstName()+medicalRecordUpdated.getLastName());
		
		int index = medicalrecords.indexOf(medicalRecordToUpdate);
		medicalrecords.set(index, medicalRecordUpdated);

		ArrayNode newMedicalRecordsData = getObjectMapper().valueToTree(medicalrecords);
		DataContainer.medicalRecordsData = newMedicalRecordsData;

		return medicalRecordUpdated;
	}

	@Override
	public MedicalRecord delete(MedicalRecord medicalRecord) 
			throws  UnavailableDataException, EmptyDataException, MedicalRecordNotFoundException, DuplicatedMedicalRecordException {

		long duplicated = checkForDuplication(medicalRecord.getFirstName()+medicalRecord.getLastName());
		
		if(duplicated>1) {
			List<MedicalRecord> homonymousMedicalRecords = this.getDuplicatedMedicalRecords(medicalRecord.getFirstName()+medicalRecord.getLastName());
			throw new DuplicatedMedicalRecordException("Warning! "+duplicated+" medical records is/are registered in data container: "
			+homonymousMedicalRecords+" . Not able to determine which one has to be deleted.");
		}
		
		List<MedicalRecord> medicalRecords = this.getAll();
		MedicalRecord medicalRecordToDelete = this.getOne(medicalRecord.getFirstName() + medicalRecord.getLastName());
		
		int index = medicalRecords.indexOf(medicalRecordToDelete);
		medicalRecords.remove(index);

		ArrayNode newMedicalRecordsData = getObjectMapper().valueToTree(medicalRecords);
		DataContainer.medicalRecordsData = newMedicalRecordsData;

		return medicalRecord;
	}

	private long checkForDuplication(String identifier) throws UnavailableDataException, EmptyDataException {
		
		return this.getAll().stream()
				.filter(p -> (p.getFirstName()+p.getLastName()).equalsIgnoreCase(identifier))
				.count();
	}

}
