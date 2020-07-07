package com.safetynet.safetynetalerts.responseentity;

import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Component;


public class CommunityPersonsCoveredByFireStation {
	
	private int childCount;
	private int adultCount;
	private List<CoveredPersonInfo> personsInfo;
	
	public CommunityPersonsCoveredByFireStation() {
		super();
		this.childCount = 0;
		this.adultCount = 0;
		this.personsInfo = new ArrayList<CoveredPersonInfo>();
	}

	public void addPerson(String firstName, String lastName, String address, String phoneNumber,int age) {
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

}
