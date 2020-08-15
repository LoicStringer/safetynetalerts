package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedPersonException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class PersonService {

	@Autowired
	private PersonDao personDao;

	public List<Person> getAllPersons() throws PersonsDataNotFoundException {

		List<Person> persons = new ArrayList<Person>();

		try {
			persons = personDao.getAll();
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new PersonsDataNotFoundException("A problem occured while retrieving persons data");
		}

		return persons;
	}

	public Person getOnePerson(String identifier) throws PersonNotFoundException, PersonsDataNotFoundException {

		Person personToGet = new Person();

		try {
			personToGet = personDao.getOne(identifier);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new PersonsDataNotFoundException("A problem occured while retrieving persons data");
		} catch (ItemNotFoundException e) {
			throw new PersonNotFoundException("Person identified by " + identifier + " has not been found");
		}

		return personToGet;
	}

	public Person insertPerson(Person person) throws DuplicatedPersonException, PersonsDataNotFoundException {

		try {
			personDao.insert(person);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new PersonsDataNotFoundException("A problem occured while retrieving persons data");
		} catch (DuplicatedItemException e) {
			throw new DuplicatedPersonException("Warning : a person identified by " + person.getFirstName()+" "
					+ person.getLastName() + " already exists");
		}

		return person;
	}

	public Person updatePerson(Person person) throws PersonsDataNotFoundException, PersonNotFoundException {

		try {
			personDao.update(person);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new PersonsDataNotFoundException("A problem occured while retrieving persons data");
		} catch (ItemNotFoundException e) {
			throw new PersonNotFoundException("Person " + person.toString() + " has not been found");
		}

		return person;
	}

	public Person deletePerson(Person person) throws PersonsDataNotFoundException, PersonNotFoundException {

		try {
			personDao.delete(person);
		} catch (DataImportFailedException | UnavailableDataException | EmptyDataException e) {
			throw new PersonsDataNotFoundException("A problem occured while retrieving persons data");
		} catch (ItemNotFoundException e) {
			throw new PersonNotFoundException("Person " + person.toString() + " has not been found");
		}

		return person;
	}

}
