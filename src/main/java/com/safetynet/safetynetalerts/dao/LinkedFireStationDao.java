package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.Person;


@Component
public class LinkedFireStationDao  extends DataProvider implements IDao<LinkedFireStation>{

	@Override
	public List<LinkedFireStation> getAll() {
		List<LinkedFireStation> linkedFireStations = new ArrayList<LinkedFireStation>();
		ArrayNode linkedFireStationsData = getDataContainer().getLinkedFireStationsData();
		Iterator<JsonNode> elements = linkedFireStationsData.elements();
		
		while (elements.hasNext()) {
			JsonNode linkedFireStationNode = elements.next();
			LinkedFireStation linkFireStation = getObjectMapper().convertValue(linkedFireStationNode, LinkedFireStation.class);
			linkedFireStations.add(linkFireStation);
		}
		
		return linkedFireStations;
	}
	
	@Override
	public LinkedFireStation getOne(String address) {
		ArrayNode linkedFireStationsData = getDataContainer().getLinkedFireStationsData();
		LinkedFireStation linkedFireStationToGet = new LinkedFireStation();
		Iterator<JsonNode> elements = linkedFireStationsData.elements();

		while (elements.hasNext()) {
			JsonNode linkedFireStationNode = elements.next();
			String adressToGet = linkedFireStationNode.findValue("address").asText();
			if(adressToGet.equals(address))
				linkedFireStationToGet = getObjectMapper().convertValue(linkedFireStationNode, LinkedFireStation.class);
				break;
		}
		return linkedFireStationToGet;
	}

	
	@Override
	public boolean insert(LinkedFireStation  linkedFireStation) {
		boolean isSaved = false;
		JsonNode linkedFireStationNode = getObjectMapper().convertValue(linkedFireStation, JsonNode.class);
		ArrayNode linkedFireStationsData = getDataContainer().getLinkedFireStationsData();
		int size = linkedFireStationsData.size();
		
		linkedFireStationsData.add(linkedFireStationNode);
		getDataContainer().setLinkedFireStationsData(linkedFireStationsData);	
		
		if(linkedFireStationsData.size() == (size+1))
			isSaved = true;
		
		return isSaved;
	}

	@Override
	public boolean update(LinkedFireStation  linkedFireStation) {
		boolean isUpdated = false;
		String address = linkedFireStation.getAddress();
		
		List<LinkedFireStation> linkedFireStations = this.getAll();
		LinkedFireStation linkedStationToUpdate = this.getOne(address);
		int index = linkedFireStations.indexOf(linkedStationToUpdate);
		linkedFireStations.set(index, linkedFireStation);
		if(linkedFireStations.get(index) != linkedStationToUpdate)
			isUpdated = true;

		ArrayNode newLinkedFireStatiosData = getObjectMapper().valueToTree(linkedFireStations);
		getDataContainer().setLinkedFireStationsData(newLinkedFireStatiosData);
		
		return isUpdated;
	}

	@Override
	public boolean delete(LinkedFireStation  linkedFireStation) {
		boolean isDeleted = false;
		String address = linkedFireStation.getAddress() + linkedFireStation.getStation();
		List<LinkedFireStation> linkedFireStations = this.getAll();
		int size = linkedFireStations.size();
		
		LinkedFireStation linkedStationToUpdate = this.getOne(address);
		int index = linkedFireStations.indexOf(linkedStationToUpdate);
		linkedFireStations.remove(index);
		
		if(linkedFireStations.size() == (size-1))
			isDeleted = true;
		
		ArrayNode newLinkedFireStationsData = getObjectMapper().valueToTree(linkedFireStations);
		getDataContainer().setLinkedFireStationsData(newLinkedFireStationsData);
		
		return isDeleted;
	}

	
}
