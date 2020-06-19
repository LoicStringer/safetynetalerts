package com.safetynet.safetynetalerts.dao;

import java.util.Arrays;
import java.util.List;

import com.safetynet.safetynetalerts.model.Person;

public class PersonDao extends AbstractDataDao implements IDao<Person>{

	
	@Override
	public List<Person> getAll() {
		List<Person> persons = Arrays.asList(getObjectMapper().convertValue(getPersonsData(), Person[].class));
		return persons;
	}

	@Override
	public boolean save(Person person) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Person person) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Person person) {
		// TODO Auto-generated method stub
		return false;
	}

}
