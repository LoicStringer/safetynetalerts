package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.exceptions.DaoException;
import com.safetynet.safetynetalerts.exceptions.ModelException;
import com.safetynet.safetynetalerts.model.LinkedFireStation;

@Service
public class LinkedFireStationService {


	@Autowired
	private LinkedFireStationDao linkedFireStationDao;
	
	public List<LinkedFireStation> getAllLinkedFireStations() throws ModelException{
		
		List<LinkedFireStation> linkedFireStations = new ArrayList<LinkedFireStation>();
		
		try {
			linkedFireStations = linkedFireStationDao.getAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while querying the fire stations mapping list", e);
		}finally {
			if(linkedFireStations==null||linkedFireStations.isEmpty())
				throw new ModelException("Warning : data source may be null or empty!");
		}
		return linkedFireStations ;
	}
	
	public LinkedFireStation getOneLinkedFireStation(String address) throws ModelException {
		
		LinkedFireStation linkedFireStationToGet = new LinkedFireStation();
		
		try {
			linkedFireStationToGet = linkedFireStationDao.getOne(address);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while querying the specified fire station mapping linked to "+ address,e);
		}finally {
			if(linkedFireStationToGet==null )
				throw new ModelException("Fire station mapping linked to " + address + " has not been found");
		}
		return linkedFireStationToGet;
	}
	
	public LinkedFireStation insertLinkedFireStation(LinkedFireStation linkedFireStation) throws ModelException {

		try {
			linkedFireStationDao.insert(linkedFireStation);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while inserting fire station mapping "+ linkedFireStation.toString(), e);
		}
		return linkedFireStation;
	}
	
	public LinkedFireStation updateLinkedFireStation(LinkedFireStation linkedFireStation) throws ModelException {
		
		try {
			linkedFireStationDao.update(linkedFireStation) ;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while updating fire station mapping " + linkedFireStation.toString(), e);
		}
		return linkedFireStation;
	}
	
	public LinkedFireStation deleteLinkedFireStation(LinkedFireStation linkedFireStation) throws ModelException {
		
		try {
			linkedFireStationDao.delete(linkedFireStation);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while deleting fire station mapping " + linkedFireStation.toString(), e);
		}
		
		return linkedFireStation;
	}
	
}
