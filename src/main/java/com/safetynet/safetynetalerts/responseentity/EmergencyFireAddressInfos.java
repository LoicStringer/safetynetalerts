package com.safetynet.safetynetalerts.responseentity;

import java.util.List;

public class EmergencyFireAddressInfos {

	private String stationNumber;
	private List<InhabitantInfos> inhabitantsThere;
	

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	public List<InhabitantInfos> getInhabitantsThere() {
		return inhabitantsThere;
	}

	public void setInhabitantsThere(List<InhabitantInfos> inhabitantsThere) {
		this.inhabitantsThere = inhabitantsThere;
	}

	@Override
	public String toString() {
		return "EmergencyFireAddressInfos [stationNumber=" + stationNumber + ", inhabitantsThere=" + inhabitantsThere
				+ "]";
	}


}
