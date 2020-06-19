package com.safetynet.safetynetalerts.model;



/**
 * Retrieves the link between an address and a fire station by its Id.
 * @author newbie
 *
 */
public class LinkedFirestation {
	
	private String address;
	private String stationId;
	
	
	public LinkedFirestation(String address, String stationId) {
		super();
		this.address = address;
		this.stationId = stationId;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getStationId() {
		return stationId;
	}


	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
	

}