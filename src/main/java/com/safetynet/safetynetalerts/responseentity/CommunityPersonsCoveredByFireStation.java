package com.safetynet.safetynetalerts.responseentity;

import java.util.ArrayList;
import java.util.List;


public class CommunityPersonsCoveredByFireStation {
	
	private int childCount;
	private int adultCount;
	private List<CoveredPersonInfo> personsInfo;
	
	public CommunityPersonsCoveredByFireStation() {
		this.childCount = 0;
		this.adultCount = 0;
		this.personsInfo = new ArrayList<CoveredPersonInfo>();
	}

	public void addCoveredPerson(String firstName, String lastName, String address, String phoneNumber,int age) {
		personsInfo.add(new CoveredPersonInfo(firstName,lastName,address,phoneNumber));
		if(age<18) {
			childCount ++;
			return;
		}
		adultCount ++ ;
	}

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	public int getAdultCount() {
		return adultCount;
	}

	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	public List<CoveredPersonInfo> getPersonsInfo() {
		return personsInfo;
	}

	public void setPersonsInfo(List<CoveredPersonInfo> personsInfo) {
		this.personsInfo = personsInfo;
	}

	public class CoveredPersonInfo {
		
		private String firstName;
		private String lastName;
		private String address;
		private String phoneNumber;
		
		public CoveredPersonInfo() {
			super();
		}
		
		public CoveredPersonInfo(String firstName, String lastName, String address, String phoneNumber) {
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
			return "CoveredPersonInfo [firstName=" + firstName + ", lastName=" + lastName + ", address="
					+ address + ", phoneNumber=" + phoneNumber + "]";
		}
		
	}

	@Override
	public String toString() {
		return "CommunityPersonsCoveredByFireStation [childCount=" + childCount + ", adultCount=" + adultCount
				+ ", personsInfo=" + personsInfo + "]";
	}
	
	
}
