package com.safetynet.safetynetalerts.responseentity;

import org.springframework.stereotype.Component;

@Component
public class CommunityPersonCoveredByFireStation {
	
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	
	
	public CommunityPersonCoveredByFireStation() {
		super();
	}
	
	public CommunityPersonCoveredByFireStation(String firstName, String lastName, String address, String phoneNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}



	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "CommunityPersonCoveredByFireStation [firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + ", phoneNumber=" + phoneNumber + "]";
	}

	

}
