package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.model.Person;


@Component
public class PersonDao extends DataProvider implements IDao<Person> {

	@Override
	public List<Person> getAll() {
		List<Person> persons = new ArrayList<Person>();
		ArrayNode personsData = getDataContainer().getPersonsData();
		Iterator<JsonNode> elements = personsData.elements();

		while (elements.hasNext()) {
			JsonNode personNode = elements.next();
			Person person = getObjectMapper().convertValue(personNode, Person.class);
			persons.add(person);
		}

		return persons;
	}

	@Override
	public Person getOne(String identifier) {
		ArrayNode personsData = getDataContainer().getPersonsData();
		Person personToGet = new Person();
		Iterator<JsonNode> elements = personsData.elements();

		while (elements.hasNext()) {
			JsonNode personNode = elements.next();
			String identifierToGet = personNode.findValue("firstName").asText() 
					+ personNode.findValue("lastName").asText();
			if(identifierToGet.equals(identifier))
				personToGet = getObjectMapper().convertValue(personNode, Person.class);
				break;
		}
		return personToGet;
	}
	
	@Override
	public boolean insert(Person person) {
		boolean isSaved = false;
		JsonNode personNode = getObjectMapper().convertValue(person, JsonNode.class);
		ArrayNode personsData = getDataContainer().getPersonsData();
		int size = personsData.size();

		personsData.add(personNode);
		getDataContainer().setPersonsData(personsData);

		if (personsData.size() == (size + 1))
			isSaved = true;

		return isSaved;
	}

	@Override
	public boolean update(Person person) {
		boolean isUpdated = false;
		String identifier = person.getFirstName() + person.getLastName();
		
		List<Person> persons = this.getAll();
		Person personToUpdate = this.getOne(identifier);
		int index = persons.indexOf(personToUpdate);
		persons.set(index, person);
		if (persons.get(index) != personToUpdate)
			isUpdated = true;
		 
		ArrayNode newPersonsData = getObjectMapper().valueToTree(persons);
		getDataContainer().setPersonsData(newPersonsData);

		return isUpdated;
	}

	@Override
	public boolean delete(Person person) {
		boolean isDeleted = false;
		String identifier = person.getFirstName() + person.getLastName();
		
		List<Person> persons = this.getAll();
		int size = persons.size();
		Person personToUpdate = this.getOne(identifier);
		int index = persons.indexOf(personToUpdate);
		persons.remove(index);

		if (persons.size() == (size - 1))
			isDeleted = true;

		ArrayNode newPersonsData = getObjectMapper().valueToTree(persons);
		getDataContainer().setPersonsData(newPersonsData);

		return isDeleted;
	}

	

}
