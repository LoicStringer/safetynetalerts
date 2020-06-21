package com.safetynet.safetynetalerts.model;

/**
 * Retrieves the link between an address and a fire station by its Id.
 * 
 * @author newbie
 *
 */
public class LinkedFireStation {

	private String address;
	private String station;

	public LinkedFireStation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LinkedFireStation(String address, String station) {
		super();
		this.address = address;
		this.station = station;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStation() {
		return station;
	}

	public void setStationId(String station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "LinkedFireStation [address=" + address + ", station=" + station + "]";
	}
	
	

}