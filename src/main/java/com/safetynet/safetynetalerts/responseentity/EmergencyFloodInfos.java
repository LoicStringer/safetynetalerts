package com.safetynet.safetynetalerts.responseentity;

import java.util.ArrayList;
import java.util.List;

public class EmergencyFloodInfos {

	private List<StationInfos> coveredHomesList;

	public EmergencyFloodInfos() {

		this.coveredHomesList = new ArrayList<StationInfos>();
	}

	public void addStationInfos(String stationNumber, List<HomeInfo> homesInfos) {

		this.coveredHomesList.add(new StationInfos(stationNumber, homesInfos));
	}

	public List<StationInfos> getCoveredHomesList() {
		return coveredHomesList;
	}

	public void setCoveredHomesList(List<StationInfos> coveredHomesList) {
		this.coveredHomesList = coveredHomesList;
	}

	public class StationInfos {
		private String stationNumber;
		private List<HomeInfo> homesInfos;

		public StationInfos(String stationNumber, List<HomeInfo> homesInfos) {
			this.stationNumber = stationNumber;
			this.homesInfos = homesInfos;

		}

		public String getStationNumber() {
			return stationNumber;
		}

		public void setStationNumber(String stationNumber) {
			this.stationNumber = stationNumber;
		}

		public List<HomeInfo> getHomesInfos() {
			return homesInfos;
		}

		public void setHomesInfos(List<HomeInfo> homesInfos) {
			this.homesInfos = homesInfos;
		}

		@Override
		public String toString() {
			return "StationInfos [stationNumber=" + stationNumber + ", homesInfos=" + homesInfos + "]";
		}

		
		
		
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

		@Override
		public String toString() {
			return "HomeInfo [address=" + address + ", inhabitantsThere=" + inhabitantsThere + "]";
		}

		
		
	}

	@Override
	public String toString() {
		return "EmergencyFloodInfos [coveredHomesList=" + coveredHomesList + "]";
	}

	
	
}
