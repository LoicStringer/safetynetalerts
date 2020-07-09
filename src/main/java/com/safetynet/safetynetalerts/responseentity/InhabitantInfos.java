package com.safetynet.safetynetalerts.responseentity;

import java.util.Arrays;

public class InhabitantInfos {
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private int age;
	private String[] medications;
	private String[] allergies;
	
	public InhabitantInfos() {
		super();
	}

	public InhabitantInfos(String firstName, String lastName, String phoneNumber, int age, String[] medications,
			String[] allergies) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.medications = medications;
		this.allergies = allergies;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
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
		return "EmergencyFireAddressInfos [firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
				+ phoneNumber + ", age=" + age + ", medications=" + Arrays.toString(medications) + ", allergies="
				+ Arrays.toString(allergies) + "]";
	}


}
