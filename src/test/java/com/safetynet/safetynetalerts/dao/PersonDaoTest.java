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

import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.Person;

@Tag("PersonDaoTests")
@DisplayName("PersonDao CRUD operations tests")
class PersonDaoTest {
	
	private PersonDao personDao;
	private List<Person> persons;

	@BeforeEach
	void setUp() throws UnavailableDataException, EmptyDataException {
		
		personDao = new PersonDao();
		persons = new ArrayList<Person>();
	}

	@Test
	void getAllTest() throws  UnavailableDataException, EmptyDataException{
		
		persons = personDao.getAll();
		
		assertNotNull(persons);
		assertEquals(23, persons.size());
	}

	@Test
	void getOneTest() throws UnavailableDataException, EmptyDataException, ItemNotFoundException {
		
		Person personToget = personDao.getOne("JohnBoyd");
		
		assertEquals(personToget.getFirstName()+personToget.getLastName(),"JohnBoyd");
		assertEquals(personToget.getAddress(), "1509 Culver St");
	}
	
	@Test
	void insertTest() throws UnavailableDataException, EmptyDataException {
		
		Person personToInsert =new Person();
		personToInsert.setFirstName("Newbie");
		personToInsert.setLastName("Noob");
	
		personDao.insert(personToInsert);
		persons = personDao.getAll();
		
		assertEquals(persons.get(persons.size()-1).getFirstName(),"Newbie");
	}
	
	@Test
	void updateTest() throws UnavailableDataException, EmptyDataException, ItemNotFoundException, DuplicatedItemException {
		
		persons = personDao.getAll();
		Person personToUpdate= persons.get(0);
		
		personToUpdate.setCity("Paris");
		personDao.update(personToUpdate);
		persons = personDao.getAll();
		
		assertEquals(persons.get(0).getCity(),"Paris");
	}
	
	@Test
	void deleteTest() throws UnavailableDataException, EmptyDataException, ItemNotFoundException, DuplicatedItemException {
		
		persons = personDao.getAll();
		Person personToDelete = persons.get(0);
		
		personDao.delete(personToDelete);
		persons = personDao.getAll();
	
		assertNotEquals(persons.get(0).getFirstName(),"John");
	}
	
	@Test
	void isThrowingExceptionWhenPersonIsNotFoundTest() throws UnavailableDataException, EmptyDataException  {
	
		Person personToGet = new Person();
		personToGet.setFirstName("Toto");
		personToGet.setLastName("Toto");
		String identifier = personToGet.getFirstName()+" "+personToGet.getLastName();
		
		Exception exception = assertThrows(ItemNotFoundException.class,()->personDao.getOne(identifier));
		
		assertEquals(exception.getMessage(),"No person found for identifier Toto Toto");
	}
	
	
}
