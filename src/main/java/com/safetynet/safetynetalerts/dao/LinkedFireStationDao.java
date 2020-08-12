package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.model.LinkedFireStation;

@Component
public class LinkedFireStationDao extends DataProvider implements IDao<LinkedFireStation> {

	@Override
	public List<LinkedFireStation> getAll() throws DataImportFailedException, UnavailableDataException, EmptyDataException {

		List<LinkedFireStation> linkedFireStations = new ArrayList<LinkedFireStation>();

		ArrayNode linkedFireStationsData;

		try {
			linkedFireStationsData = getDataContainer().getLinkedFireStationsData();
		} catch (DataImportFailedException e) {
			throw new DataImportFailedException("A problem occured while querying fire station mappings list from the data container",
					e);
		} catch (UnavailableDataException e) {
			throw new UnavailableDataException("Fire station mappings list is null", e);
		} catch (EmptyDataException e) {
			throw new EmptyDataException("Fire station mappings list is empty", e);
		}

		Iterator<JsonNode> elements = linkedFireStationsData.elements();
		while (elements.hasNext()) {
			JsonNode linkedFireStationNode = elements.next();
			LinkedFireStation linkFireStation = getObjectMapper().convertValue(linkedFireStationNode,
					LinkedFireStation.class);
			linkedFireStations.add(linkFireStation);
		}

		return linkedFireStations;
	}

	@Override
	public LinkedFireStation getOne(String address) throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {

		LinkedFireStation linkedFireStationToGet = new LinkedFireStation();

		ArrayNode linkedFireStationsData;
		
		try {
			linkedFireStationsData = getDataContainer().getLinkedFireStationsData();
		} catch (DataImportFailedException e) {
			throw new DataImportFailedException("A problem occured while querying fire station mappings list from the data container",
					e);
		} catch (UnavailableDataException e) {
			throw new UnavailableDataException("Fire station mappings list is null", e);
		} catch (EmptyDataException e) {
			throw new EmptyDataException("Fire station mappings list is empty", e);
		}

		Iterator<JsonNode> elements = linkedFireStationsData.elements();
		while (elements.hasNext()) {
			JsonNode linkedFireStationNode = elements.next();
			String adressToGet = linkedFireStationNode.get("address").asText();
			if (adressToGet.equalsIgnoreCase(address)) {
				linkedFireStationToGet = getObjectMapper().convertValue(linkedFireStationNode, LinkedFireStation.class);
				break;
			}
		}

		if (linkedFireStationToGet.getAddress() == null)
			throw new ItemNotFoundException("No fire station mapping found for address " + address);

		return linkedFireStationToGet;
	}

	/*
	 * @Override public LinkedFireStation getOne(String address) throws DaoException
	 * { return getByValue("address", address); }
	 * 
	 * public LinkedFireStation getOneByStationNumber(String stationNumber) throws
	 * DaoException { return getByValue("station", stationNumber); }
	 */
	
	@Override
	public LinkedFireStation insert(LinkedFireStation linkedFireStation) throws DuplicatedItemException, DataImportFailedException, UnavailableDataException, EmptyDataException {

		this.checkForDuplication(linkedFireStation);

		ArrayNode linkedFireStationsData;
		
		try {
			linkedFireStationsData = getDataContainer().getLinkedFireStationsData();
		} catch (DataImportFailedException e) {
			throw new DataImportFailedException("A problem occured while querying fire station mappings list from the data container",
					e);
		} catch (UnavailableDataException e) {
			throw new UnavailableDataException("Fire station mappings list is null", e);
		} catch (EmptyDataException e) {
			throw new EmptyDataException("Fire station mappings list is empty", e);
		}
		
		JsonNode linkedFireStationNode = getObjectMapper().convertValue(linkedFireStation, JsonNode.class);
		linkedFireStationsData.add(linkedFireStationNode);
		getDataContainer().setLinkedFireStationsData(linkedFireStationsData);

		return linkedFireStation;
	}

	@Override
	public LinkedFireStation update(LinkedFireStation linkedFireStation) throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {
		
		String address = linkedFireStation.getAddress();

		List<LinkedFireStation> linkedFireStations = this.getAll();
		LinkedFireStation linkedStationToUpdate = this.getOne(address);
		int index = linkedFireStations.indexOf(linkedStationToUpdate);
		linkedFireStations.set(index, linkedFireStation);

		ArrayNode newLinkedFireStatiosData = getObjectMapper().valueToTree(linkedFireStations);
		getDataContainer().setLinkedFireStationsData(newLinkedFireStatiosData);

		return linkedFireStation;
	}

	@Override
	public LinkedFireStation delete(LinkedFireStation linkedFireStation) throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {
		
		List<LinkedFireStation> linkedFireStations = this.getAll();

		LinkedFireStation linkedStationToUpdate = this.getOne(linkedFireStation.getAddress());
		int index = linkedFireStations.indexOf(linkedStationToUpdate);
		linkedFireStations.remove(index);

		ArrayNode newLinkedFireStationsData = getObjectMapper().valueToTree(linkedFireStations);
		getDataContainer().setLinkedFireStationsData(newLinkedFireStationsData);

		return linkedFireStation;
	}

	private void checkForDuplication(LinkedFireStation linkedFireStation) throws DuplicatedItemException, DataImportFailedException, UnavailableDataException, EmptyDataException {
		if (this.getAll().stream().anyMatch(lfs -> lfs.getAddress().equalsIgnoreCase(linkedFireStation.getAddress())))
			throw new DuplicatedItemException(
					"Warning : a fire station mapping with the same address already exists in data container");
	}
	/*
	 * private LinkedFireStation getByValue(String key, String value) throws
	 * DaoException {
	 * 
	 * LinkedFireStation linkedFireStationToGet = new LinkedFireStation();
	 * 
	 * try { ArrayNode linkedFireStationsData =
	 * getDataContainer().getLinkedFireStationsData(); Iterator<JsonNode> elements =
	 * linkedFireStationsData.elements();
	 * 
	 * while (elements.hasNext()) { JsonNode linkedFireStationNode =
	 * elements.next(); String adressToGet =
	 * linkedFireStationNode.findValue(key).asText();
	 * if(adressToGet.equalsIgnoreCase(value)) { linkedFireStationToGet =
	 * getObjectMapper().convertValue(linkedFireStationNode,
	 * LinkedFireStation.class); break; } } } catch (DataContainerException e) {
	 * e.printStackTrace(); throw new
	 * DaoException("A problem occured while querying the specified "
	 * +value+" Fire Station from the data container",e); }
	 * 
	 * return linkedFireStationToGet; }
	 */
}
