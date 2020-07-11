package com.safetynet.safetynetalerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	public List<MedicalRecord> getAllMedicalRecords(){
		return medicalRecordDao.getAll();
	}
	
	public MedicalRecord getOneMedicalRecord(String identifier) {
		return medicalRecordDao.getOne(identifier);
	}
	
	public MedicalRecord insertMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordDao.insert(medicalRecord);
	}
	
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordDao.update(medicalRecord) ;
	}
	
	public boolean deletePerson(String identifier) {
		return medicalRecordDao.delete(identifier);
	}
	
	
	
	
	
	
}
