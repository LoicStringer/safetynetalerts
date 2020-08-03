package com.safetynet.safetynetalerts.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;



import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.exceptions.DaoException;
import com.safetynet.safetynetalerts.exceptions.ModelException;
import com.safetynet.safetynetalerts.model.Person;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
	
	
	
	@Mock
	public PersonDao personDao;

	@InjectMocks
	public PersonService personService;

	private static List<Person> persons;

	@BeforeAll
	static void setUp() {
		
		persons = new ArrayList<Person>();

		persons.add(
				new Person("John", "Boyd", "1509 Culver St", "Culver", "97541", "841-874-6512", "jaboyd@email.com"));
		persons.add(new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97541", "841-874-6513", "drk@email.com"));
		persons.add(
				new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97541", "841-874-6512", "tenz@email.com"));
		persons.add(
				new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97541", "841-874-6512", "jaboyd@email.com"));
		persons.add(
				new Person("Felicia", "Boyd", "1509 Culver St", "Culver", "97541", "841-874-6544", "jaboyd@email.com"));
		persons.add(
				new Person("Sophia", "Zemicks", "892 Downing Ct", "Culver", "97541", "841-874-7878", "soph@email.com"));
		persons.add(
				new Person("Warren", "Zemicks", "892 Downing Ct", "Culver", "97541", "841-874-7512", "ward@email.com"));
		persons.add(
				new Person("Zach", "Zemicks", "892 Downing Ct", "Culver", "97541", "841-874-7512", "zarc@email.com"));
	}
	
	@Test
	void getAllTest() throws DaoException, ModelException {
		
		when(personDao.getAll()).thenReturn(persons);
		
		List<Person> getAllPersons = personService.getAllPersons();
		
		assertEquals(getAllPersons.size(),8);
		assertEquals(getAllPersons.get(0).getFirstName(), "John");
	}
	
	@Test
	void getOneTest() throws DaoException, ModelException {
		
		when(personDao.getOne("SophiaZemicks")).thenReturn(persons.get(5));
		
		Person personToGet = personService.getOnePerson("SophiaZemicks");
		
		assertEquals(personToGet.getLastName(), "Zemicks");
	}
	
	@Test
	void insertTest() throws DaoException, ModelException {
		
		Person personToInsert = new Person("Newbie","Noob","5,Rue Clavel","75020","Paris","06-75-25-51-37","newbienoob@yahoo.com");
		
		when(personDao.insert(personToInsert)).thenReturn(personToInsert);
		
		assertEquals(
				personService.insertPerson(personToInsert).getFirstName(), "Newbie");
	}
	
	@Test
	void updateTest() throws DaoException, ModelException {
		
		Person personToUpdate = persons.get(0);
		personToUpdate.setZip("75019");
		
		when(personDao.update(personToUpdate)).thenReturn(personToUpdate);
		
		assertEquals(personService.updatePerson(personToUpdate).getZip(),"75019");
	}
	
	@Test
	void deleteTest() throws DaoException, ModelException {
		
		Person personTodelete = persons.get(0);
		
		when(personDao.delete(personTodelete)).thenReturn(personTodelete);
		
		assertEquals(personService.deletePerson(personTodelete).getLastName(),"Boyd");
	}
	
	@Test
	void isThrowingExceptionTryingToGetAllFromNullSource() throws ModelException, DaoException  {
		
		when(personDao.getAll()).thenReturn(null);
	
		assertThrows(ModelException.class, ()->personService.getAllPersons());
	}
	
	@Test
	void isThrowingExceptionTryingToGetAllFromEmptySource() throws DaoException {
		
		List<Person> persons = new ArrayList<Person>();
		
		when(personDao.getAll()).thenReturn(persons);
		
		assertTrue(persons.isEmpty());
		assertThrows(ModelException.class, ()->personService.getAllPersons());
	}
	
	@Test
	void isThrowingExceptionTryingToGetUnknownPerson() throws ModelException {
		
		assertThrows(ModelException.class, ()-> personService.getOnePerson("Toto"));
	}
	
	
}
