package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.Person;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
	
	@Mock
	private PersonDao PersonDao;

	@InjectMocks
	private PersonService personservice;

	List<Person> persons;

	@BeforeEach
	void setUp() {
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
	void getAllTest() {
		when(PersonDao.getAll()).thenReturn(persons);
		
		List<Person> getAllPersons = personservice.getAllPersons();
		
		assertEquals(getAllPersons.size(),8);
		assertEquals(getAllPersons.get(0).getFirstName(), "John");
	}
	
	@Test
	void getOneTest() {
		when(PersonDao.getOne("SophiaZemicks")).thenReturn(persons.get(5));
		
		Person PersonToGet = personservice.getOnePerson("SophiaZemicks");
		
		assertEquals(PersonToGet.getLastName(), "Zemicks");
	}
	
	@Test
	void insertTest() {
		Person PersonToInsert = new Person("Newbie","Noob","5,Rue Clavel","75020","Paris","06-75-25-51-37","newbienoob@yahoo.com");
		when(PersonDao.insert(PersonToInsert)).thenReturn(PersonToInsert);
		assertEquals(personservice.insertPerson(PersonToInsert).getFirstName(), "Newbie");
	}
	
	@Test
	void updateTest() {
		Person personToUpdate = persons.get(0);
		personToUpdate.setZip("75019");
		when(PersonDao.update(personToUpdate)).thenReturn(personToUpdate);
		assertEquals(personservice.updatePerson(personToUpdate).getZip(),"75019");
	}
	
	@Test
	void deleteTest() {
		Person personTodelete = persons.get(0);
		when(PersonDao.delete(personTodelete)).thenReturn(personTodelete);
		assertEquals(personservice.deletePerson(personTodelete).getLastName(),"Boyd");
	}
	
	


}
