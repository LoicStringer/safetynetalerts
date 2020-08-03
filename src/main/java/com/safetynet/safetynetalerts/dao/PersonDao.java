package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.exceptions.DaoException;
import com.safetynet.safetynetalerts.exceptions.DataContainerException;
import com.safetynet.safetynetalerts.model.Person;


@Component
public class PersonDao extends DataProvider implements IDao<Person> {

	@Override
	public List<Person> getAll() throws DaoException  {
		
		List<Person> persons = new ArrayList<Person>();
		
		try {
			ArrayNode personsData = getDataContainer().getPersonsData();
			Iterator<JsonNode> elements = personsData.elements();
			while (elements.hasNext()) {
				JsonNode personNode = elements.next();
				Person person = getObjectMapper().convertValue(personNode, Person.class);
				persons.add(person);
			}
		} catch (DataContainerException e) {
			e.printStackTrace();
			throw new DaoException("A problem occured while querying Persons from the data container",e);
		} 
	
		return persons;
	}

	@Override
	public Person getOne(String identifier) throws DaoException  {
		Person personToGet = new Person();
		try {
			ArrayNode personsData = getDataContainer().getPersonsData();
			Iterator<JsonNode> elements = personsData.elements();
			while (elements.hasNext()) {
				JsonNode personNode = elements.next();
				String identifierToFind = personNode.get("firstName").asText()
						+ personNode.get("lastName").asText();
				if(identifierToFind.equalsIgnoreCase(identifier)) {
					personToGet = getObjectMapper().convertValue(personNode, Person.class);
				}
			}
		} catch (DataContainerException e) {
			e.printStackTrace();
			throw new DaoException("A problem occured while querying the specified "+identifier+" Person from the data container",e);
		}
		return personToGet;
	}
	
	@Override
	public Person insert(Person person) throws DaoException  {
		
		JsonNode personNode = getObjectMapper().convertValue(person, JsonNode.class);
		
		try {
			ArrayNode personsData = getDataContainer().getPersonsData();
			personsData.add(personNode);
			getDataContainer().setPersonsData(personsData);
		} catch (DataContainerException e) {
			e.printStackTrace();
			throw new DaoException("A problem occured while inserting Person "+ person.toString(), e);
		}
		return person;
	}

	@Override
	public Person update(Person personUpdated) throws DaoException {
		
			Person personToUpdate = this.getOne( personUpdated.getFirstName() + personUpdated.getLastName());
			
			List<Person> persons = this.getAll();
			int index = persons.indexOf(personToUpdate);
			persons.set(index, personUpdated);
			
			ArrayNode newPersonsData = getObjectMapper().valueToTree(persons);
			getDataContainer().setPersonsData(newPersonsData);
			
		return personUpdated;
	}

	@Override
	public Person delete(Person person) throws DaoException {		
		
		List<Person> persons = this.getAll();
	
		Person personToDelete = this.getOne(person.getFirstName()+person.getLastName());
		int index = persons.indexOf(personToDelete);
		persons.remove(index);

		ArrayNode newPersonsData = getObjectMapper().valueToTree(persons);
		getDataContainer().setPersonsData(newPersonsData);

		return person;
	}

	

}
