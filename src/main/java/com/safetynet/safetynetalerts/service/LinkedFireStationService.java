package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.exceptions.DuplicatedLinkedFireStationException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationNotFoundException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.LinkedFireStation;

/**
 * <p>Service layer class that displays CRUD methods, 
 * corresponding to the DAO methods, to handle {@link LinkedFireStation} objects.</p>
 * @author newbie
 * @see LinkedFireStationDao
 */
@Service
public class LinkedFireStationService {

	private Logger log =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LinkedFireStationDao linkedFireStationDao;

	public List<LinkedFireStation> getAllLinkedFireStations() throws LinkedFireStationsDataNotFoundException {

		List<LinkedFireStation> linkedFireStations = new ArrayList<LinkedFireStation>();

		try {
			log.debug(System.lineSeparator()+
					"Linked Fire Station Service call to Linked Fire Station Dao, aiming at retrieving the whole list of fire stations mappings.");
			linkedFireStations = linkedFireStationDao.getAll();
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new LinkedFireStationsDataNotFoundException("A problem occured while retrieving fire station mappings data",e);
		}

		return linkedFireStations;
	}

	public LinkedFireStation getOneLinkedFireStation(String address) throws LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException  {

		LinkedFireStation linkedFireStationToGet = new LinkedFireStation();

		try {
			log.debug(System.lineSeparator()+
					"Linked Fire Station Service call to Linked Fire Station Dao, aiming at retrieving the fire station mapping for address :"+address+" .");
			linkedFireStationToGet = linkedFireStationDao.getOne(address);
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new LinkedFireStationsDataNotFoundException("A problem occured while retrieving fire station mappings data",e);
		} catch (LinkedFireStationNotFoundException e) {
			throw new LinkedFireStationNotFoundException("Fire station mapping for address " + address + " has not been found",e);
		}
		
		return linkedFireStationToGet;
	}
	
	public List<LinkedFireStation> getDuplicatedLinkedFireStations(String address) throws LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException {
		
		log.debug(System.lineSeparator()+
				"Linked Fire Station Service call to Linked Fire Station Dao, aiming at retrieving the fire station mappings list for address :"+address+" .");
		
		List<LinkedFireStation> duplicatedLinkedFireStations = new ArrayList<LinkedFireStation>();
		
		try {
			duplicatedLinkedFireStations = linkedFireStationDao.getDuplicatedLinkedFireStation(address);
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new LinkedFireStationsDataNotFoundException("A problem occured while retrieving fire station mappings data",e);
		} catch (LinkedFireStationNotFoundException e) {
			throw new LinkedFireStationNotFoundException("Fire station mapping for address " + address + " has not been found",e);
		}
		return duplicatedLinkedFireStations;
		
	}

	public LinkedFireStation insertLinkedFireStation(LinkedFireStation linkedFireStation) throws LinkedFireStationsDataNotFoundException  {

		try {
			log.debug(System.lineSeparator()+
					"Linked Fire Station Service call to Linked Fire Station Dao, aiming at inserting a new fire station mapping.");
			linkedFireStationDao.insert(linkedFireStation);
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new LinkedFireStationsDataNotFoundException("A problem occured while retrieving fire station mappings data",e);
		}
		
		return linkedFireStation;
	}

	public LinkedFireStation updateLinkedFireStation(LinkedFireStation linkedFireStation) throws LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException, DuplicatedLinkedFireStationException {

		try {
			log.debug(System.lineSeparator()+
					"Linked Fire Station Service call to Linked Fire Station Dao, aiming at updating fire station mapping "+linkedFireStation.toString()+" .");
			linkedFireStationDao.update(linkedFireStation);
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new LinkedFireStationsDataNotFoundException("A problem occured while retrieving fire station mappings data",e);
		} catch (LinkedFireStationNotFoundException e) {
			throw new LinkedFireStationNotFoundException("Fire station mapping " + linkedFireStation.toString() + " has not been found",e);
		} catch (DuplicatedLinkedFireStationException e) {
			throw new DuplicatedLinkedFireStationException(e.getMessage(),e);
		}
		
		return linkedFireStation;
	}

	public LinkedFireStation deleteLinkedFireStation(LinkedFireStation linkedFireStation) throws LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException, DuplicatedLinkedFireStationException {

		try {
			log.debug(System.lineSeparator()+
					"Linked Fire Station Service call to Linked Fire Station Dao, aiming at deleting fire station mapping "+linkedFireStation.toString()+" .");
			linkedFireStationDao.delete(linkedFireStation);
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new LinkedFireStationsDataNotFoundException("A problem occured while retrieving fire station mappings data",e);
		} catch (LinkedFireStationNotFoundException e) {
			throw new LinkedFireStationNotFoundException("Fire station mapping " + linkedFireStation.toString() + " has not been found",e);
		} catch (DuplicatedLinkedFireStationException e) {
			throw new DuplicatedLinkedFireStationException(e.getMessage(),e);
		}

		return linkedFireStation;
	}

}
