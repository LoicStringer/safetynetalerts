package com.safetynet.safetynetalerts.model;

/**
 * Retrieves the link between an address and a fire station.</br>
 * Constructed of two <code>String</code> attributes, an address and a fire station id,
 * as provided by the data source Json file.
 * 
 * @author newbie
 *
 */
public class LinkedFireStation {

	private String address;
	private String station;

	public LinkedFireStation() {
		super();
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

	public void setStation(String station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "LinkedFireStation [address=" + address + ", station=" + station + "]";
	}
	
	

}