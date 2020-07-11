package com.safetynet.safetynetalerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class PersonService {

	@Autowired
	private PersonDao personDao;
	
	public List<Person> getAllPersons(){
		return personDao.getAll();
	}
	
	public Person getOnePerson(String identifier) {
		return personDao.getOne(identifier);
	}
	
	public Person insertPerson(Person person) {
		return personDao.insert(person);
	}
	
	public Person updatePerson(Person person) {
		return personDao.update(person) ;
	}
	
	public boolean deletePerson(String identifier) {
		return personDao.delete(identifier);
	}
	
	
	
}
