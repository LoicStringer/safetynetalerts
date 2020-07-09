package com.safetynet.safetynetalerts.responseentity;

import java.util.ArrayList;
import java.util.List;


public class EmergencyChildAlert {

	private List<ChildThere> childrenThere;
	private List<OtherPersonThere> otherPersonsThere;
	
	
	public EmergencyChildAlert() {
		this.childrenThere = new ArrayList<ChildThere>();
		this.otherPersonsThere = new ArrayList<OtherPersonThere>();		
	}

	public void addPerson (String firstName, String lastName, int age) {
		
		if(age<18) {
			childrenThere.add(new ChildThere(firstName,lastName,age));
			return;
		}
		otherPersonsThere.add(new OtherPersonThere(firstName,lastName,age));
	}
	
	public List<ChildThere> getChildrenThere() {
		return childrenThere;
	}

	public void setChildrenThere(List<ChildThere> childrenThere) {
		this.childrenThere = childrenThere;
	}

	public List<OtherPersonThere> getOtherPersonsThere() {
		return otherPersonsThere;
	}

	public void setOtherPersonsThere(List<OtherPersonThere> otherPersonsThere) {
		this.otherPersonsThere = otherPersonsThere;
	}
	
	
	public class ChildThere {
		
		private String firstName;
		private String lastName;
		private int age;
		
		public ChildThere() {
			super();
		}

		public ChildThere(String firstName, String lastName, int age) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
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

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
		
	}
	
	
	public class OtherPersonThere{
		
		private String firstName;
		private String lastName;
		private int age;
		
		public OtherPersonThere() {
			super();
		}

		public OtherPersonThere(String firstName, String lastName, int age) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
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

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
		
		
	}
	
	
	
}
