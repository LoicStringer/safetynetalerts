package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.model.LinkedFireStation;

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
		String identifier = linkedFireStation.getAddress();
		List<LinkedFireStation> linkedFireStations = this.getAll();
		
		LinkedFireStation linkedStationToUpdate = linkedFireStations.stream()
				.filter(lfs -> identifier.equals(lfs.getAddress())).findAny().orElse(null);
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
		String identifier = linkedFireStation.getAddress() + linkedFireStation.getStation();
		List<LinkedFireStation> linkedFireStations = this.getAll();
		int size = linkedFireStations.size();
		
		LinkedFireStation linkedStationToUpdate = linkedFireStations.stream()
				.filter(lfs -> identifier.equals(lfs.getAddress() + lfs.getStation())).findAny().orElse(null);
		int index = linkedFireStations.indexOf(linkedStationToUpdate);
		linkedFireStations.remove(index);
		
		if(linkedFireStations.size() == (size-1))
			isDeleted = true;
		
		ArrayNode newLinkedFireStationsData = getObjectMapper().valueToTree(linkedFireStations);
		getDataContainer().setLinkedFireStationsData(newLinkedFireStationsData);
		
		return isDeleted;
	}

}
