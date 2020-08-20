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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + age;
			result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
			result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ChildThere other = (ChildThere) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (age != other.age)
				return false;
			if (firstName == null) {
				if (other.firstName != null)
					return false;
			} else if (!firstName.equals(other.firstName))
				return false;
			if (lastName == null) {
				if (other.lastName != null)
					return false;
			} else if (!lastName.equals(other.lastName))
				return false;
			return true;
		}

		private EmergencyChildAlert getEnclosingInstance() {
			return EmergencyChildAlert.this;
		}

		@Override
		public String toString() {
			return "ChildThere [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + "]";
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + age;
			result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
			result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			OtherPersonThere other = (OtherPersonThere) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (age != other.age)
				return false;
			if (firstName == null) {
				if (other.firstName != null)
					return false;
			} else if (!firstName.equals(other.firstName))
				return false;
			if (lastName == null) {
				if (other.lastName != null)
					return false;
			} else if (!lastName.equals(other.lastName))
				return false;
			return true;
		}

		private EmergencyChildAlert getEnclosingInstance() {
			return EmergencyChildAlert.this;
		}

		@Override
		public String toString() {
			return "OtherPersonThere [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + "]";
		}

	}

	@Override
	public String toString() {
		return "EmergencyChildAlert [childrenThere=" + childrenThere + ", otherPersonsThere=" + otherPersonsThere + "]";
	}

}
