package com.safetynet.safetynetalerts.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.safetynetalerts.data.DataContainer;
import com.safetynet.safetynetalerts.exceptions.DuplicatedPersonException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
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
		DataContainer.reloadDataForTests();
	}

	@Nested
	@Tag("OperationsTests")
	@DisplayName("Operations checking")
	class OperationsTests {
		
		@Test
		void getAllTest() throws  UnavailableDataException, EmptyDataException{
			
			persons = personDao.getAll();
			
			assertNotNull(persons);
			assertEquals(DataContainer.personsData.size(), persons.size());
		}

		@Test
		void getOneTest() throws UnavailableDataException, EmptyDataException, PersonNotFoundException {
			
			Person personToget = personDao.getOne("JohnBoyd");
			
			assertEquals(personToget.getFirstName()+personToget.getLastName(),"JohnBoyd");
			assertEquals(personToget.getAddress(), "1509 Culver St");
		}
		
		@Test
		void getDuplicatedPersonsTest() throws UnavailableDataException, EmptyDataException, PersonNotFoundException {
			
			Person personToInsert = new Person();
			personToInsert.setFirstName("John");
			personToInsert.setLastName("Boyd");
			
			personDao.insert(personToInsert);
			List<Person> duplicatedPersons = personDao.getDuplicatedPersons("JohnBoyd");
			
			assertEquals(duplicatedPersons.size(),2);
			assertEquals(duplicatedPersons.get(0).getFirstName(),duplicatedPersons.get(1).getFirstName());
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
		void updateTest() throws UnavailableDataException, EmptyDataException, PersonNotFoundException, DuplicatedPersonException {
			
			persons = personDao.getAll();
			Person personToUpdate= persons.get(0);
			
			personToUpdate.setCity("Paris");
			personDao.update(personToUpdate);
			persons = personDao.getAll();
			
			assertEquals(persons.get(0).getCity(),"Paris");
		}
		
		@Test
		void deleteTest() throws UnavailableDataException, EmptyDataException, PersonNotFoundException, DuplicatedPersonException {
			
			persons = personDao.getAll();
			Person personToDelete = persons.get(0);
			
			personDao.delete(personToDelete);
			persons = personDao.getAll();
		
			assertNotEquals(persons.get(0).getFirstName(),"John");
		}
	}
	
	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {
		
		@Test
		void isThrowingExceptionWhenPersonIsNotFoundTest() throws UnavailableDataException, EmptyDataException  {
		
			Person personToGet = new Person();
			personToGet.setFirstName("Toto");
			personToGet.setLastName("Toto");
			String identifier = personToGet.getFirstName()+" "+personToGet.getLastName();
			
			Exception exception = assertThrows(PersonNotFoundException.class,()->personDao.getOne(identifier));
			assertEquals(exception.getMessage(),"No person found identified as Toto Toto");
		}
		
		@Test
		void isThrowingExceptionWhenTryingToDeleteHomonymousPerson() throws UnavailableDataException, EmptyDataException {
			
			Person homonymousPerson = new Person();
			homonymousPerson.setFirstName("John");
			homonymousPerson.setLastName("Boyd");
			
			personDao.insert(homonymousPerson);
			
			assertThrows(DuplicatedPersonException.class, ()->personDao.delete(homonymousPerson));
		}
		
		@Test
		void isThrowingExceptionWhenTryingToUpdateHomonymousPerson() throws UnavailableDataException, EmptyDataException {
			
			Person personToUpdate = new Person();
			personToUpdate.setFirstName("John");
			personToUpdate.setLastName("Boyd");
			
			personDao.insert(personToUpdate);
			
			assertThrows(DuplicatedPersonException.class, ()->personDao.update(personToUpdate));
		}
	
		@Test
		void isThrowingExceptionWhenDataSourceIsEmpty() {
			
			DataContainer.personsData.removeAll();
			
			Exception exception = assertThrows(EmptyDataException.class,()->personDao.getAll());
			assertEquals(exception.getMessage(),"Persons list is empty");
		}
		
		@Test
		void isThrowingExceptionWhenDataSourceIsNull() {
			
			DataContainer.personsData=null;
			
			Exception exception = assertThrows(UnavailableDataException.class,()->personDao.getAll());
			assertEquals(exception.getMessage(),"Persons list is null");
		}
	}
	
}
