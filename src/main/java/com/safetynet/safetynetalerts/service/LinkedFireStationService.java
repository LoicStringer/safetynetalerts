package com.safetynet.safetynetalerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;

@Service
public class LinkedFireStationService {


	@Autowired
	private LinkedFireStationDao linkedFireStationDao;
	
	public List<LinkedFireStation> getAllLinkedFireStations(){
		return linkedFireStationDao.getAll();
	}
	
	public LinkedFireStation getOneLinkedFireStation(String address) {
		return linkedFireStationDao.getOne(address);
	}
	
	public LinkedFireStation insertLinkedFireStation(LinkedFireStation linkedFireStation) {
		return linkedFireStationDao.insert(linkedFireStation);
	}
	
	public LinkedFireStation updateLinkedFireStation(LinkedFireStation linkedFireStation) {
		return linkedFireStationDao.update(linkedFireStation) ;
	}
	
	public LinkedFireStation deleteLinkedFireStation(LinkedFireStation linkedFireStation) {
		return linkedFireStationDao.delete(linkedFireStation);
	}
	
	
	
}
