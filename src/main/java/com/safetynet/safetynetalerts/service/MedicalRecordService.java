package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class MedicalRecordService {

	private Logger log =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	public List<MedicalRecord> getAllMedicalRecords() throws MedicalRecordsDataNotFoundException{
		
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		
		try {
			log.debug(System.lineSeparator()+
					"MedicalRecord Service call to MedicalRecord Dao, aiming at retrieving the whole list of medical records.");
			medicalRecords = medicalRecordDao.getAll();
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new MedicalRecordsDataNotFoundException("A problem occured while retrieving medical records data");
		}
		
		return medicalRecords;
	}
	
	public MedicalRecord getOneMedicalRecord(String identifier) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException {
		
		MedicalRecord medicalRecordToGet = new MedicalRecord();
		
		try {
			log.debug(System.lineSeparator()+
					"MedicalRecord Service call to MedicalRecord Dao, aiming at retrieving the medical record identified by the parameter \"identifier\": "+identifier);
			medicalRecordToGet = medicalRecordDao.getOne(identifier);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new MedicalRecordsDataNotFoundException("A problem occured when retrieving medical records data");
		} catch (ItemNotFoundException e) {
			throw new MedicalRecordNotFoundException("Medical record identified by " + identifier + " has not been found");
		}

		return medicalRecordToGet;
	}
	
	public List<MedicalRecord> getHomonymousMedicalRecords(String identifier) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException {
		
		log.debug(System.lineSeparator()+
				"MedicalRecord Service call to MedicalRecord Dao, aiming at retrieving a medical records list identified by the parameter \"identifier\" : "+identifier+" .");
		
		List<MedicalRecord> homonymousMedicalRecords = new ArrayList<MedicalRecord>();
		
		try {
			homonymousMedicalRecords = medicalRecordDao.getDuplicatedItems(identifier);
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new MedicalRecordsDataNotFoundException("A problem occured while retrieving medical records data");
		} catch (ItemNotFoundException e) {
			throw new MedicalRecordNotFoundException("Medical record identified by " + identifier + " has not been found");
		}
		return homonymousMedicalRecords;
		
	}
	
	
	
	public MedicalRecord insertMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordsDataNotFoundException, DuplicatedMedicalRecordException  {
		
		try {
			log.debug(System.lineSeparator()+
					"Medical Record Service call to Medical Record Dao, aiming at inserting a new medical record.");
			medicalRecordDao.insert(medicalRecord);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new MedicalRecordsDataNotFoundException("A problem occured when retrieving medical records data");
		} 

		return medicalRecord;
	}
	
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException, DuplicatedMedicalRecordException  {
		
		try {
			log.debug(System.lineSeparator()+
					"Medical Record Service call to Medical Record Dao, aiming at updating "+medicalRecord.getFirstName()+medicalRecord.getLastName()+" medical record.");
			medicalRecordDao.update(medicalRecord);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new MedicalRecordsDataNotFoundException("A problem occured when retrieving medical records data");
		} catch (ItemNotFoundException e) {
			throw new MedicalRecordNotFoundException("Medical record " + medicalRecord.toString() + " has not been found");
		} catch (DuplicatedItemException e) {
			throw new DuplicatedMedicalRecordException(e.getMessage());
		}
		
		return medicalRecord;
	}
	
	public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordsDataNotFoundException, MedicalRecordNotFoundException, DuplicatedMedicalRecordException  {
		
		try {
			log.debug(System.lineSeparator()+
					"Medical Record Service call to Medical Record Dao, aiming at deleting "+medicalRecord.getFirstName()+" "+medicalRecord.getLastName()+" medical record.");
			medicalRecordDao.delete(medicalRecord);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new MedicalRecordsDataNotFoundException("A problem occured when retrieving medical records data");
		} catch (ItemNotFoundException e) {
			throw new MedicalRecordNotFoundException("Medical record " + medicalRecord.toString() + " has not been found");
		} catch (DuplicatedItemException e) {
			throw new DuplicatedMedicalRecordException(e.getMessage());
		}
		
		return medicalRecord;
	}
	
}
