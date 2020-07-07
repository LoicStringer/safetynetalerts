package com.safetynet.safetynetalerts.responseentity;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class EmergencyChildAlert {

	private String firstName;
	private String lastName;
	private String age;

	public EmergencyChildAlert() {
		super();
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
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "EmergencyChildAlert [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + "]";
	}

	

	
	
	
	
	
}
