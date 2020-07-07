package com.safetynet.safetynetalerts.responseentity;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class EmergencyFireAddressInfos {
	
	private String firstName;
	private String lastname;
	private String phoneNumber;
	private String age;
	private String[] medications;
	private String[] allergies;
	
	
	public EmergencyFireAddressInfos() {
		super();
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
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
		return "EmergencyFireAddressInfos [firstName=" + firstName + ", lastname=" + lastname + ", phoneNumber="
				+ phoneNumber + ", age=" + age + ", medications=" + Arrays.toString(medications) + ", allergies="
				+ Arrays.toString(allergies) + "]";
	}
	
	
	

}
