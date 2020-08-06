package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedPersonException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
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
		void getAllTest() throws UnavailableDataException, EmptyDataException, DataImportFailedException,
				PersonsDataNotFoundException {

			when(personDao.getAll()).thenReturn(persons);

			List<Person> getAllPersons = personService.getAllPersons();

			assertEquals(getAllPersons.size(), 8);
			assertEquals(getAllPersons.get(0).getFirstName(), "John");
		}

		@Test
		void getOneTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException,
				ItemNotFoundException, PersonNotFoundException, PersonsDataNotFoundException {

			when(personDao.getOne("SophiaZemicks")).thenReturn(persons.get(5));

			Person personToGet = personService.getOnePerson("SophiaZemicks");

			assertEquals(personToGet.getLastName(), "Zemicks");
		}

		@Test
		void insertTest() throws DuplicatedItemException, DataImportFailedException, UnavailableDataException,
				EmptyDataException, DuplicatedPersonException, PersonsDataNotFoundException {

			Person personToInsert = new Person("Newbie", "Noob", "5,Rue Clavel", "75020", "Paris", "06-75-25-51-37",
					"newbienoob@yahoo.com");

			when(personDao.insert(personToInsert)).thenReturn(personToInsert);

			assertEquals(personService.insertPerson(personToInsert).getFirstName(), "Newbie");
		}

		@Test
		void updateTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException,
				ItemNotFoundException, PersonsDataNotFoundException, PersonNotFoundException {

			Person personToUpdate = persons.get(0);
			personToUpdate.setZip("75020");

			when(personDao.update(personToUpdate)).thenReturn(personToUpdate);

			assertEquals(personService.updatePerson(personToUpdate).getZip(), "75020");
		}

		@Test
		void deleteTest() throws UnavailableDataException, EmptyDataException, DataImportFailedException,
				ItemNotFoundException, PersonsDataNotFoundException, PersonNotFoundException {

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
		void isExpectedExceptionThrownWhenInsertingDuplicatedPersonTest() throws DuplicatedItemException,
				DataImportFailedException, UnavailableDataException, EmptyDataException {

			Person personToInsert = new Person();
			when(personDao.insert(personToInsert)).thenThrow(DuplicatedItemException.class);

			Exception exception = assertThrows(DuplicatedPersonException.class,
					() -> personService.insertPerson(personToInsert));

			assertEquals(exception.getMessage(), "Warning : a person identified by " + personToInsert.getFirstName()
					+ personToInsert.getLastName() + " already exists");
		}

		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnknownPersonTest()
				throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {

			when(personDao.getOne("Toto")).thenThrow(ItemNotFoundException.class);

			Exception exception = assertThrows(PersonNotFoundException.class, () -> personService.getOnePerson("Toto"));

			assertEquals(exception.getMessage(), "Person identified by Toto has not been found");

		}

		@Test
		void isExpectedExceptionThrownWhenDataSourceIsCorruptedTest()
				throws UnavailableDataException, EmptyDataException, DataImportFailedException {

			when(personDao.getAll()).thenThrow(UnavailableDataException.class);

			Exception exception = assertThrows(PersonsDataNotFoundException.class, () -> personService.getAllPersons());

			assertEquals(exception.getMessage(), "A problem occured while retrieving persons data");
		}
	}

}
