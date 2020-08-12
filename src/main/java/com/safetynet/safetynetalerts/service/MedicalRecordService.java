package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedMedicalRecordException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.safetynetalerts.exceptions.MedicalRecordsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	public List<MedicalRecord> getAllMedicalRecords() throws MedicalRecordsDataNotFoundException{
		
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		
		try {
			medicalRecords = medicalRecordDao.getAll();
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new MedicalRecordsDataNotFoundException("A problem occured while retrieving medical records data");
		}
		
		return medicalRecords;
	}
	
	public MedicalRecord getOneMedicalRecord(String identifier) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException {
		
		MedicalRecord medicalRecordToGet = new MedicalRecord();
		
		try {
			medicalRecordToGet = medicalRecordDao.getOne(identifier);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new MedicalRecordsDataNotFoundException("A problem occured when retrieving medical records data");
		} catch (ItemNotFoundException e) {
			throw new MedicalRecordNotFoundException("Medical record identified by " + medicalRecordToGet.getFirstName()+" "+medicalRecordToGet.getLastName() + " has not been found");
		}

		return medicalRecordToGet;
	}
	
	public MedicalRecord insertMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordsDataNotFoundException, DuplicatedMedicalRecordException  {
		
		try {
			medicalRecordDao.insert(medicalRecord);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new MedicalRecordsDataNotFoundException("A problem occured when retrieving medical records data");
		} catch (DuplicatedItemException e) {
			throw new DuplicatedMedicalRecordException("Warning : a medical record identified by " + medicalRecord.getFirstName()+" "
					+ medicalRecord.getLastName() + " already exists");
		}

		return medicalRecord;
	}
	
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException  {
		
		try {
			medicalRecordDao.update(medicalRecord);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new MedicalRecordsDataNotFoundException("A problem occured when retrieving medical records data");
		} catch (ItemNotFoundException e) {
			throw new MedicalRecordNotFoundException("Medical record " + medicalRecord.toString() + " has not been found");
		}
		
		return medicalRecord;
	}
	
	public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException  {
		
		try {
			medicalRecordDao.delete(medicalRecord);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new MedicalRecordsDataNotFoundException("A problem occured when retrieving medical records data");
		} catch (ItemNotFoundException e) {
			throw new MedicalRecordNotFoundException("Medical record " + medicalRecord.toString() + " has not been found");
		}
		
		return medicalRecord;
	}
	
}
