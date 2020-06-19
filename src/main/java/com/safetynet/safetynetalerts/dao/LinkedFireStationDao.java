package com.safetynet.safetynetalerts.dao;

import java.util.Arrays;
import java.util.List;

import com.safetynet.safetynetalerts.model.LinkedFireStation;

public class LinkedFireStationDao extends AbstractDataDao implements IDao<LinkedFireStation>{

	@Override
	public List<LinkedFireStation> getAll() {
		List<LinkedFireStation> linkedFireStations = Arrays.asList(getObjectMapper().convertValue(getLinkedFirestationData(), LinkedFireStation[].class));
		return linkedFireStations;
	}

	@Override
	public boolean save(LinkedFireStation  linkedFireStation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(LinkedFireStation  linkedFireStation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(LinkedFireStation  linkedFireStation) {
		// TODO Auto-generated method stub
		return false;
	}

}
