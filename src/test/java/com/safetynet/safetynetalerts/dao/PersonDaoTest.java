package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalerts.exceptions.DaoException;
import com.safetynet.safetynetalerts.model.Person;

@Tag("PersonDaoTests")
@DisplayName("PersonDao CRUD operations tests")
class PersonDaoTest {

	private PersonDao personDao;

	@BeforeEach
	void setUp() {
		personDao = new PersonDao();
	}

	@Test
	void getAllTest() throws DaoException {
		
		List<Person>persons = personDao.getAll();
		
		assertNotNull(persons);
		assertEquals(23, persons.size());
	}

	@Test
	void getOneTest() throws DaoException {
		
		Person personToget = personDao.getOne("JohnBoyd");
		
		assertEquals("JohnBoyd",personToget.getFirstName()+personToget.getLastName());
		assertEquals(personToget.getAddress(), "1509 Culver St");
	}
	
	@Test
	void insertTest() throws DaoException {
		
		Person person =new Person();
		person.setFirstName("Newbie");
		person.setLastName("Noob");
	
		personDao.insert(person);
		List<Person> persons = personDao.getAll();
		
		assertEquals(persons.get(persons.size()-1).getFirstName(),"Newbie");
	}
	
	@Test
	void updateTest() throws DaoException {
		
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Boyd");
		person.setCity("Paris");
		
		personDao.update(person);
		List<Person >persons = personDao.getAll();
		
		assertEquals("Paris", persons.get(0).getCity());
	}
	
	@Test
	void deleteTest() throws DaoException {
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Boyd");
		
		personDao.delete(person);
		List<Person> persons = personDao.getAll();
	
		assertNotEquals("John", persons.get(0).getFirstName());
	}

}
