package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalerts.model.Person;

@Tag("PersonDaoTests")
@DisplayName("PersonDao CRUD operations tests")
class PersonDaoTest {

	PersonDao personDao;
	Person person;
	List<Person> persons;
	String identifier;
	
	@BeforeEach
	void setUp() {
		personDao = new PersonDao();
		person = new Person();
		persons = new ArrayList<Person>();
		identifier = "JohnBoyd";
	}

	@Test
	void getAllTest() {
		persons = personDao.getAll();
		assertNotNull(persons);
		assertEquals(23, persons.size());
	}

	@Test
	void getOneTest() {
		Person personToget = personDao.getOne(identifier);
		assertEquals(identifier,personToget.getFirstName()+personToget.getLastName());
		assertEquals(personToget.getAddress(), "1509 Culver St");
	}
	
	@Test
	void insertTest() {
		person.setFirstName("Newbie");
		
		personDao.insert(person);
		persons = personDao.getAll();
		
		assertEquals("Newbie",persons.get(persons.size()-1).getFirstName());
	}
	
	@Test
	void updateTest() {
		person.setFirstName("John");
		person.setLastName("Boyd");
		
		person.setCity("Paris");
		personDao.update(person);
		persons = personDao.getAll();
		
		assertEquals("Paris", persons.get(0).getCity());
	}
	
	@Test
	void deleteTest() {
		person.setFirstName("John");
		person.setLastName("Boyd");
		
		personDao.delete(person);
		persons = personDao.getAll();
	
		assertNotEquals("John", persons.get(0).getFirstName());
	}


	
}
