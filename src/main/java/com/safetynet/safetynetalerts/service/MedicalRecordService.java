package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.exceptions.DaoException;
import com.safetynet.safetynetalerts.exceptions.ModelException;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	public List<MedicalRecord> getAllMedicalRecords() throws ModelException{
		
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		
		try {
			medicalRecords = medicalRecordDao.getAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while querying the Medical Records list", e);
		}finally {
			if(medicalRecords==null||medicalRecords.isEmpty())
				throw new ModelException("Warning : data source may be null or empty!");
		}
		
		return medicalRecords;
	}
	
	public MedicalRecord getOneMedicalRecord(String identifier) throws ModelException {
		
		MedicalRecord medicalRecortToGet = new MedicalRecord();
		
		try {
			medicalRecortToGet =  medicalRecordDao.getOne(identifier);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while querying the specified Medical Record "+identifier,e);
		}finally {
			if(medicalRecortToGet==null )
				throw new ModelException("Medical Record identified by "+identifier+" has not been found");
		}
		
		return medicalRecortToGet;
	}
	
	public MedicalRecord insertMedicalRecord(MedicalRecord medicalRecord) throws ModelException {
		
		try {
			medicalRecordDao.insert(medicalRecord);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while inserting Medical Record "+ medicalRecord.toString(), e);
		}
		
		return medicalRecord;
	}
	
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws ModelException {
		
		try {
			 medicalRecordDao.update(medicalRecord) ;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while updating Medical Record " + medicalRecord.toString(), e);
		}
		
		return medicalRecord;
	}
	
	public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) throws ModelException {
		
		try {
			 medicalRecordDao.delete(medicalRecord);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while deleting Medical Record " + medicalRecord.toString(), e);
		}
		
		return medicalRecord;
	}
	
}
