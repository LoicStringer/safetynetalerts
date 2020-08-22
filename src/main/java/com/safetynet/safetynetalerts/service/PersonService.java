package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.exceptions.DuplicatedPersonException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class PersonService {

	private Logger log =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PersonDao personDao;

	public List<Person> getAllPersons() throws PersonsDataNotFoundException {

		List<Person> persons = new ArrayList<Person>();

		try {
			log.debug(System.lineSeparator()+
					"Person Service call to Person Dao, aiming at retrieving the whole list of persons.");
			persons = personDao.getAll();
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new PersonsDataNotFoundException("A problem occured while retrieving persons data",e);
		}

		return persons;
	}

	public Person getOnePerson(String identifier) throws PersonNotFoundException, PersonsDataNotFoundException {

		Person personToGet = new Person();

		try {
			log.debug(System.lineSeparator()+
					"Person Service call to Person Dao, aiming at retrieving the person identified by the parameter \"identifier\" : "+identifier+" .");
			personToGet = personDao.getOne(identifier);
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new PersonsDataNotFoundException("A problem occured while retrieving persons data",e);
		} catch (PersonNotFoundException e) {
			throw new PersonNotFoundException("Person identified as " + identifier + " has not been found",e);
		}

		return personToGet;
	}

	public List<Person> getHomonymousPersons(String identifier) throws PersonsDataNotFoundException, PersonNotFoundException {
		
		log.debug(System.lineSeparator()+
				"Person Service call to Person Dao, aiming at retrieving a persons list identified by the parameter \"identifier\" : "+identifier+" .");
		
		List<Person> homonymousPersons = new ArrayList<Person>();
		
		try {
			homonymousPersons = personDao.getDuplicatedPersons(identifier);
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new PersonsDataNotFoundException("A problem occured while retrieving persons data",e);
		} catch (PersonNotFoundException e) {
			throw new PersonNotFoundException("Person identified as " + identifier + " has not been found",e);
		}
		
		return homonymousPersons;
	}
	
	public Person insertPerson(Person person) throws PersonsDataNotFoundException{

		try {
			log.debug(System.lineSeparator()+
					"Person Service call to Person Dao, aiming at inserting a new person: "+person.getFirstName()+" "+person.getLastName()+" .");
			personDao.insert(person);
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new PersonsDataNotFoundException("A problem occured while retrieving persons data",e);
		} 
		
		return person;
	}

	public Person updatePerson(Person person) throws PersonsDataNotFoundException, PersonNotFoundException, DuplicatedPersonException {

		log.debug(System.lineSeparator()+
				"Person Service call to Person Dao, aiming at updating person: "+person.getFirstName()+" "+person.getLastName()+" .");
		
		try {
			personDao.update(person);
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new PersonsDataNotFoundException("A problem occured while retrieving persons data",e);
		} catch (PersonNotFoundException e) {
			throw new PersonNotFoundException("Person " + person.toString() + " has not been found",e);
		} catch (DuplicatedPersonException e) {
			throw new DuplicatedPersonException(e.getMessage(),e);
		}
		
		return person;
	}

	public Person deletePerson(Person person) throws PersonsDataNotFoundException, PersonNotFoundException, DuplicatedPersonException {

		log.debug(System.lineSeparator()+
				"Person Service call to Person Dao, aiming at deleting person: "+person.getFirstName()+" "+person.getLastName()+" .");
				
		try {
			personDao.delete(person);
		} catch (UnavailableDataException | EmptyDataException e) {
			throw new PersonsDataNotFoundException("A problem occured while retrieving persons data",e);
		} catch (PersonNotFoundException e) {
			throw new PersonNotFoundException("Person " + person.toString() + " has not been found",e);
		}catch (DuplicatedPersonException e) {
			throw new DuplicatedPersonException(e.getMessage(),e);
		}
		
		return person;
	}

}
