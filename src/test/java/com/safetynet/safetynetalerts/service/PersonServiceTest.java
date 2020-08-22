package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.exceptions.DuplicatedPersonException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.Person;

@DisplayName("PersonService CRUD operations tests")
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	@Mock
	public PersonDao personDao;

	@InjectMocks
	public PersonService personService;

	private static List<Person> persons;

	@BeforeAll
	static void setUpForTests() {

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

	@Nested
	@Tag("OperationsTests")
	@DisplayName("Operations checking")
	class OperationsTests {

		@Test
		void getAllTest() throws UnavailableDataException, EmptyDataException, PersonsDataNotFoundException {

			when(personDao.getAll()).thenReturn(persons);

			List<Person> getAllPersons = personService.getAllPersons();

			assertEquals(getAllPersons.size(), 8);
			assertEquals(getAllPersons.get(0).getFirstName(), "John");
		}

		@Test
		void getOneTest() throws UnavailableDataException, EmptyDataException, PersonNotFoundException,
				PersonsDataNotFoundException {

			when(personDao.getOne("SophiaZemicks")).thenReturn(persons.get(5));

			Person personToGet = personService.getOnePerson("SophiaZemicks");

			assertEquals(personToGet.getLastName(), "Zemicks");
		}

		@Test
		void getHomonymousPersonsTest() throws UnavailableDataException, EmptyDataException, PersonNotFoundException, PersonsDataNotFoundException {
			
			List<Person> homonymousPersonsList = new ArrayList<Person>();
			Person homonymousPerson = new Person();
			
			homonymousPerson.setFirstName("Jacob");
			homonymousPerson.setLastName("Boyd");
			homonymousPersonsList.add(persons.get(1));
			homonymousPersonsList.add(homonymousPerson);
			
			when(personDao.getDuplicatedPersons("JacobBoyd")).thenReturn(homonymousPersonsList);
			
			assertEquals(personService.getHomonymousPersons("JacobBoyd"),homonymousPersonsList);
		}
		
		@Test
		void insertTest() throws UnavailableDataException, EmptyDataException, PersonsDataNotFoundException {

			Person personToInsert = new Person("Newbie", "Noob", "5,Rue Clavel", "75020", "Paris", "06-75-25-51-37",
					"newbienoob@yahoo.com");

			when(personDao.insert(personToInsert)).thenReturn(personToInsert);

			assertEquals(personService.insertPerson(personToInsert).getFirstName(), "Newbie");
		}

		@Test
		void updateTest() throws UnavailableDataException, EmptyDataException, PersonsDataNotFoundException,
				PersonNotFoundException, DuplicatedPersonException {

			Person personToUpdate = persons.get(0);
			personToUpdate.setZip("75020");

			when(personDao.update(personToUpdate)).thenReturn(personToUpdate);

			assertEquals(personService.updatePerson(personToUpdate).getZip(), "75020");
		}

		@Test
		void deleteTest() throws UnavailableDataException, EmptyDataException, PersonsDataNotFoundException,
				PersonNotFoundException, DuplicatedPersonException {

			Person personTodelete = persons.get(0);

			when(personDao.delete(personTodelete)).thenReturn(personTodelete);

			assertEquals(personService.deletePerson(personTodelete).getLastName(), "Boyd");
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrowmWhenTryingToUpdateDuplicatedPerson() throws UnavailableDataException, EmptyDataException, PersonNotFoundException, DuplicatedPersonException {

			Person personToUpdate = new Person();
			
			when(personDao.update(any(Person.class))).thenThrow(DuplicatedPersonException.class);
			
			assertThrows(DuplicatedPersonException.class, ()->personService.updatePerson(personToUpdate));
		}

		@Test
		void isExpectedExceptionThrowmWhenTryingToDeleteDuplicatedPerson() throws UnavailableDataException, EmptyDataException, PersonNotFoundException, DuplicatedPersonException {

			Person personToDelete = new Person();
			
			when(personDao.delete(any(Person.class))).thenThrow(DuplicatedPersonException.class);
			
			assertThrows(DuplicatedPersonException.class, ()->personService.deletePerson(personToDelete));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnknownPersonTest()
				throws UnavailableDataException, EmptyDataException, PersonNotFoundException {

			when(personDao.getOne(any(String.class))).thenThrow(PersonNotFoundException.class);

			assertThrows(PersonNotFoundException.class, () -> personService.getOnePerson("Toto"));
		}

		@Test
		void isExpectedExceptionThrownWhenDataSourceIsCorruptedTest()
				throws UnavailableDataException, EmptyDataException {

			when(personDao.getAll()).thenThrow(UnavailableDataException.class);

			assertThrows(PersonsDataNotFoundException.class, () -> personService.getAllPersons());
		}
	}

}
