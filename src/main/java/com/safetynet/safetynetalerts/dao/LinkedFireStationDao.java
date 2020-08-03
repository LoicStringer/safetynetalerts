package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.exceptions.DaoException;
import com.safetynet.safetynetalerts.exceptions.DataContainerException;
import com.safetynet.safetynetalerts.model.LinkedFireStation;


@Component
public class LinkedFireStationDao  extends DataProvider implements IDao<LinkedFireStation>{

	@Override
	public List<LinkedFireStation> getAll() throws DaoException {
		
		List<LinkedFireStation> linkedFireStations = new ArrayList<LinkedFireStation>();
		
		try {
			ArrayNode linkedFireStationsData = getDataContainer().getLinkedFireStationsData();
			Iterator<JsonNode> elements = linkedFireStationsData.elements();
			
			while (elements.hasNext()) {
				JsonNode linkedFireStationNode = elements.next();
				LinkedFireStation linkFireStation = getObjectMapper().convertValue(linkedFireStationNode, LinkedFireStation.class);
				linkedFireStations.add(linkFireStation);
			}
		} catch (DataContainerException e) {
			e.printStackTrace();
			throw new DaoException("A problem occured while querying Fire Stations from the data container",e);
		}
		
		return linkedFireStations;
	}
	
	@Override
	public LinkedFireStation getOne(String address) throws DaoException {
		return getByValue("address", address);
	}

	public LinkedFireStation getOneByStationNumber(String stationNumber) throws DaoException {
		return getByValue("station", stationNumber);
	}
	
	@Override
	public LinkedFireStation insert(LinkedFireStation  linkedFireStation) throws DaoException {
		
		JsonNode linkedFireStationNode = getObjectMapper().convertValue(linkedFireStation, JsonNode.class);
		
		try {
			ArrayNode linkedFireStationsData = getDataContainer().getLinkedFireStationsData();
			linkedFireStationsData.add(linkedFireStationNode);
			getDataContainer().setLinkedFireStationsData(linkedFireStationsData);	
		} catch (DataContainerException e) {
			e.printStackTrace();
			throw new DaoException("A problem occured while inserting Fire Station "+ linkedFireStation.toString(), e);
		}
		
		return linkedFireStation;
	}

	@Override
	public LinkedFireStation update(LinkedFireStation  linkedFireStation) throws DaoException {
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
	public LinkedFireStation delete(LinkedFireStation linkedFireStation) throws DaoException {
		List<LinkedFireStation> linkedFireStations = this.getAll();
		
		LinkedFireStation linkedStationToUpdate = this.getOne(linkedFireStation.getAddress());
		int index = linkedFireStations.indexOf(linkedStationToUpdate);
		linkedFireStations.remove(index);
		
		ArrayNode newLinkedFireStationsData = getObjectMapper().valueToTree(linkedFireStations);
		getDataContainer().setLinkedFireStationsData(newLinkedFireStationsData);
		
		return linkedFireStation;
	}
	
	private LinkedFireStation getByValue(String key, String value) throws DaoException {
		
		LinkedFireStation linkedFireStationToGet = new LinkedFireStation();
		
		try {
			ArrayNode linkedFireStationsData = getDataContainer().getLinkedFireStationsData();
			Iterator<JsonNode> elements = linkedFireStationsData.elements();

			while (elements.hasNext()) {
				JsonNode linkedFireStationNode = elements.next();
				String adressToGet = linkedFireStationNode.findValue(key).asText();
				if(adressToGet.equalsIgnoreCase(value)) {
					linkedFireStationToGet = getObjectMapper().convertValue(linkedFireStationNode, LinkedFireStation.class);
					break;
				}
			}
		} catch (DataContainerException e) {
			e.printStackTrace();
			throw new DaoException("A problem occured while querying the specified "+value+" Fire Station from the data container",e);
		}
		
		return linkedFireStationToGet;
	}
	
}
