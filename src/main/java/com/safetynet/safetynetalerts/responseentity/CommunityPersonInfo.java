package com.safetynet.safetynetalerts.responseentity;

import java.util.ArrayList;
import java.util.List;

public class CommunityPersonInfo {

	private List<PersonInfo> personsInfo;
	
	public CommunityPersonInfo() {
		this.personsInfo = new ArrayList<PersonInfo>();
	}
	
	public void addPersonInfo(PersonInfo personInfo) {
		personsInfo.add(personInfo);
	}
	
	@Override
	public String toString() {
		return "CommunityPersonInfo [personsInfo=" + personsInfo + "]";
	}

	public List<PersonInfo> getPersonsInfo() {
		return personsInfo;
	}

	public void setPersonsInfo(List<PersonInfo> personsInfo) {
		this.personsInfo = personsInfo;
	}

	public class PersonInfo{
		private String firstName;
		private String lastName;
		private int age;
		private String address;
		private String email;
		private String[] medications;
		private String[] allergies;

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

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String[] getMedications() {
			return medications;
		}

		public void setMedications(String[] medications) {
			this.medications = medications;
		}

		public String[] getAllergies() {
			return allergies;
		}

		public void setAllergies(String[] allergies) {
			this.allergies = allergies;
		}

		@Override
		public String toString() {
			return "CommunityPersonInfo [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", address="
					+ address + ", email=" + email + ", medications=" + medications + ", allergies=" + allergies + "]";
		}

	}

}
