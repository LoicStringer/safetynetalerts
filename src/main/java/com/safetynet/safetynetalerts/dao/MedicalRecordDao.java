package com.safetynet.safetynetalerts.dao;

import java.util.Arrays;
import java.util.List;

import com.safetynet.safetynetalerts.model.MedicalRecord;

public class MedicalRecordDao extends AbstractDataDao implements IDao<MedicalRecord> {

	@Override
	public List<MedicalRecord> getAll() {
		List<MedicalRecord> medicalRecords = Arrays.asList(getObjectMapper().convertValue(getMedicalRecordsData(), MedicalRecord[].class));
		return medicalRecords;
	}

	@Override
	public boolean save(MedicalRecord medicalRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(MedicalRecord medicalRecord) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(MedicalRecord medicalRecord) {
		// TODO Auto-generated method stub
		return false;
	}

}
