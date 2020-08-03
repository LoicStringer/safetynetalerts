package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

	@Mock
	private MedicalRecordDao medicalRecordDao;

	@InjectMocks
	private MedicalRecordService medicalRecordService;

	private static List<MedicalRecord> medicalRecords;

	@BeforeAll
	static void setUp() {
		medicalRecords = new ArrayList<MedicalRecord>();

		medicalRecords
				.add(new MedicalRecord("Jonathan", "Marrack", "01/03/1989", new String[] { "" }, new String[] { "" }));
		medicalRecords.add(new MedicalRecord("Sophia", "Zemicks", "03/06/1988",
				new String[] { "aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg" },
				new String[] { "peanut", "shellfish", "aznol" }));
	}

	@Test
	void getAllTest() {
		when(medicalRecordDao.getAll()).thenReturn(medicalRecords);
		
		List<MedicalRecord> getAllMedicalRecords = medicalRecordService.getAllMedicalRecords();
		
		assertEquals(getAllMedicalRecords.size(),2);
		assertEquals(getAllMedicalRecords.get(0).getFirstName(), "Jonathan");
	}
	
	@Test
	void getOneTest() {
		when(medicalRecordDao.getOne("SophiaZemicks")).thenReturn(medicalRecords.get(1));
		
		MedicalRecord medicalRecordToGet = medicalRecordService.getOneMedicalRecord("SophiaZemicks");
		
		assertEquals(medicalRecordToGet.getLastName(), "Zemicks");
	}
	
	@Test
	void insertTest() {
		MedicalRecord medicalRecordToInsert = new MedicalRecord("Newbie","Noob","04/01/1978",new String[] {""}, new String[] {""});
		when(medicalRecordDao.insert(medicalRecordToInsert)).thenReturn(medicalRecordToInsert);
		assertEquals(medicalRecordService.insertMedicalRecord(medicalRecordToInsert).getFirstName(), "Newbie");
	}
	
	@Test
	void updateTest() {
		MedicalRecord medicalRecordToUpdate = medicalRecords.get(0);
		medicalRecordToUpdate.setBirthdate("04/01/1978");
		when(medicalRecordDao.update(medicalRecordToUpdate)).thenReturn(medicalRecordToUpdate);
		assertEquals(medicalRecordService.updateMedicalRecord(medicalRecordToUpdate).getBirthdate(),"04/01/1978");
	}
	
	@Test
	void deleteTest() {
		MedicalRecord medicalRecordTodelete = medicalRecords.get(0);
		when(medicalRecordDao.delete(medicalRecordTodelete)).thenReturn(medicalRecordTodelete);
		assertEquals(medicalRecordService.deleteMedicalRecord(medicalRecordTodelete).getLastName(),"Marrack");
	}
	
	
}
