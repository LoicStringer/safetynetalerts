package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.exceptions.DaoException;
import com.safetynet.safetynetalerts.exceptions.ModelException;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class PersonService {

	@Autowired
	private PersonDao personDao;
	
	public List<Person> getAllPersons() throws ModelException  {
		
		List<Person> persons = new ArrayList<Person>();
		
		try {
			persons = personDao.getAll();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while querying the Person list", e);
		} finally {
			if(persons==null||persons.isEmpty())
				throw new ModelException("Warning : data source may be null or empty!");
		}
		
		return persons;
	}
	
	public Person getOnePerson(String identifier) throws ModelException {
		
		Person personToGet = new Person();
		
		try {
			personToGet = personDao.getOne(identifier);
		}catch(DaoException e){
			e.printStackTrace();
			throw new ModelException("A problem occured while querying the specified Person "+identifier,e);
		}finally {
			if(personToGet==null )
				throw new ModelException("Person identified by "+identifier+" has not been found");
		}
		
		return personToGet;
	}
	
	public Person insertPerson(Person person) throws ModelException, DaoException {

		try {
			personDao.insert(person);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while inserting Person "+ person.toString(), e);
		}
		return person;
	}
	
	public Person updatePerson(Person person) throws ModelException{
		
		try {
			personDao.update(person) ;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while updating Person "+person.toString(), e);
		}
		return person;
	}
	
	public Person deletePerson(Person person) throws ModelException {
		
		try {
			personDao.delete(person);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ModelException("A problem occured while deleting Person "+person.toString(), e);
		}
		
		return person;
	}
	
	
	
}
