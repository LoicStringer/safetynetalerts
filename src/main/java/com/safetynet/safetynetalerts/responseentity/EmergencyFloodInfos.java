package com.safetynet.safetynetalerts.responseentity;

import java.util.LinkedHashMap;
import java.util.List;

public class EmergencyFloodInfos {

	private LinkedHashMap<String,List<HomeInfo>>coveredHomesMap;

	public EmergencyFloodInfos() {
		this.coveredHomesMap = new LinkedHashMap<String,List<HomeInfo>>();
	}

	public LinkedHashMap<String, List<HomeInfo>> getCoveredHomesMap() {
		return coveredHomesMap;
	}

	public void setCoveredHomesMap(LinkedHashMap<String, List<HomeInfo>> coveredHomesMap) {
		this.coveredHomesMap = coveredHomesMap;
	}

	public class HomeInfo {
		
		private String address;
		private List<InhabitantInfos> inhabitantsThere;
		
		public HomeInfo() {
		
		}

		public HomeInfo(String address, List<InhabitantInfos> inhabitantsThere) {
			this.address = address;
			this.inhabitantsThere = inhabitantsThere;
		}
		
		public String getAddress() {
			return address;
		}
		
		public void setAddress(String address) {
			this.address = address;
		}
		
		public List<InhabitantInfos> getInhabitantsThere() {
			return inhabitantsThere;
		}
		
		public void setInhabitantsThere(List<InhabitantInfos> inhabitantsThere) {
			this.inhabitantsThere = inhabitantsThere;
		}
		
		
	}
	
	
}
