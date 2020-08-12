package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedLinkedFireStationException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationNotFoundException;
import com.safetynet.safetynetalerts.exceptions.LinkedFireStationsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.LinkedFireStation;

@Service
public class LinkedFireStationService {

	@Autowired
	private LinkedFireStationDao linkedFireStationDao;

	public List<LinkedFireStation> getAllLinkedFireStations() throws LinkedFireStationsDataNotFoundException {

		List<LinkedFireStation> linkedFireStations = new ArrayList<LinkedFireStation>();

		try {
			linkedFireStations = linkedFireStationDao.getAll();
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new LinkedFireStationsDataNotFoundException("A problem occured while retrieving fire station mappings data");
		}

		return linkedFireStations;
	}

	public LinkedFireStation getOneLinkedFireStation(String address) throws LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException  {

		LinkedFireStation linkedFireStationToGet = new LinkedFireStation();

		try {
			linkedFireStationToGet = linkedFireStationDao.getOne(address);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new LinkedFireStationsDataNotFoundException("A problem occured while retrieving fire station mappings data");
		} catch (ItemNotFoundException e) {
			throw new LinkedFireStationNotFoundException("Fire station mapping identified by " + address + " has not been found");
		}
		
		return linkedFireStationToGet;
	}

	public LinkedFireStation insertLinkedFireStation(LinkedFireStation linkedFireStation) throws LinkedFireStationsDataNotFoundException, DuplicatedLinkedFireStationException  {

		try {
			linkedFireStationDao.insert(linkedFireStation);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new LinkedFireStationsDataNotFoundException("A problem occured while retrieving fire station mappings data");
		} catch (DuplicatedItemException e) {
			throw new DuplicatedLinkedFireStationException("Warning : a fire station mapping identified by " + linkedFireStation.getAddress()
					+ linkedFireStation.getStation() + " already exists");
		}
		
		return linkedFireStation;
	}

	public LinkedFireStation updateLinkedFireStation(LinkedFireStation linkedFireStation) throws LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException {

		try {
			linkedFireStationDao.update(linkedFireStation);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new LinkedFireStationsDataNotFoundException("A problem occured while retrieving fire station mappings data");
		} catch (ItemNotFoundException e) {
			throw new LinkedFireStationNotFoundException("Fire station mapping " + linkedFireStation.toString() + " has not been found");
		}
		
		return linkedFireStation;
	}

	public LinkedFireStation deleteLinkedFireStation(LinkedFireStation linkedFireStation) throws LinkedFireStationsDataNotFoundException, LinkedFireStationNotFoundException {

		try {
			linkedFireStationDao.delete(linkedFireStation);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new LinkedFireStationsDataNotFoundException("A problem occured while retrieving fire station mappings data");
		} catch (ItemNotFoundException e) {
			throw new LinkedFireStationNotFoundException("Fire station mapping " + linkedFireStation.toString() + " has not been found");
		}

		return linkedFireStation;
	}

}
