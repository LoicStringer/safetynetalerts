package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalerts.model.Person;

class PersonDaoTest {

	PersonDao personDao;
	Person person;
	List<Person> persons;

	@BeforeEach
	void setUp() {
		personDao = new PersonDao();
		person = new Person();
		persons = new ArrayList<Person>();
	}

	@Test
	void getAllTest() {
		persons = personDao.getAll();
		assertEquals(23, persons.size());
	}
/*
	@Test
	void insertTest() {
		person.setFirstName("Newbie");
		
		boolean isInserted = personDao.insert(person);
		persons = personDao.getAll();
		
		assertTrue(isInserted);
		assertEquals("Newbie",persons.get(persons.size()-1).getFirstName());
	}
	
	@Test
	void updateTest() {
		person.setFirstName("John");
		person.setLastName("Boyd");
		
		person.setCity("Paris");
		boolean isUpdated = personDao.update(person);
		persons = personDao.getAll();
		
		assertTrue(isUpdated);
		assertEquals("Paris", persons.get(0).getCity());
	}
	
	@Test
	void deleteTest() {
		person.setFirstName("John");
		person.setLastName("Boyd");
		
		boolean isDeleted = personDao.delete(person);
		persons = personDao.getAll();
		
		assertTrue(isDeleted);
		assertNotEquals("John", persons.get(0).getFirstName());
		
	}

*/
	
}
