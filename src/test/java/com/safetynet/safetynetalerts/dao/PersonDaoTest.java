package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.Person;

@Tag("PersonDaoTests")
@DisplayName("PersonDao CRUD operations tests")
class PersonDaoTest {

	private PersonDao personDao;
	private Person personForTests;
	private List<Person> persons;

	@BeforeEach
	void setUp() {
		personDao = new PersonDao();
		personForTests = new Person();
		persons = new ArrayList<Person>();
		personForTests.setFirstName("John");
		personForTests.setLastName("Boyd");
	}

	@Test
	void getAllTest() throws  UnavailableDataException, EmptyDataException, DataImportFailedException {
		
		persons = personDao.getAll();
		
		assertNotNull(persons);
		assertEquals(23, persons.size());
	}

	@Test
	void getOneTest() throws  DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {
		
		Person personToget = personDao.getOne("JohnBoyd");
		
		assertEquals("JohnBoyd",personToget.getFirstName()+personToget.getLastName());
		assertEquals(personToget.getAddress(), "1509 Culver St");
	}
	
	@Test
	void insertTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException, DuplicatedItemException {
		
		Person personToInsert =new Person();
		personToInsert.setFirstName("Newbie");
		personToInsert.setLastName("Noob");
	
		personDao.insert(personToInsert);
		persons = personDao.getAll();
		
		assertEquals(persons.get(persons.size()-1).getFirstName(),"Newbie");
	}
	
	@Test
	void updateTest() throws  DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {
		
		personForTests.setCity("Paris");
		
		personDao.update(personForTests);
		persons = personDao.getAll();
		
		assertEquals("Paris", persons.get(0).getCity());
	}
	
	@Test
	void deleteTest() throws UnavailableDataException, EmptyDataException, DataImportFailedException, ItemNotFoundException {
		
		personDao.delete(personForTests);
		persons = personDao.getAll();
	
		assertNotEquals("John", persons.get(0).getFirstName());
	}
	
	@Test
	void isThrowingExceptionWhenInsertingDuplicatedIdentifierPersonTest() {
		
		Exception exception = assertThrows(DuplicatedItemException.class, ()->personDao.insert(personForTests));
		
		assertEquals(exception.getMessage(),"Warning : a person with the same firstname and lastname already exists in data container");
	}
	
	@Test
	void isThrowingExceptionWhenPersonIsNotFoundTest()  {
	
		Exception exception = assertThrows(ItemNotFoundException.class,()->personDao.getOne("Toto"));
		
		assertEquals(exception.getMessage(),"No person found for identifier Toto");
	}
	
	
}
