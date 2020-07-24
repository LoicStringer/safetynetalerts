package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
			String identifierToFind = personNode.get("firstName").asText()
					+ personNode.get("lastName").asText();
			if(identifierToFind.equalsIgnoreCase(identifier)) {
				personToGet = getObjectMapper().convertValue(personNode, Person.class);
				break;
			}
		}
		return personToGet;
	}
	
	@Override
	public Person insert(Person person) {
		JsonNode personNode = getObjectMapper().convertValue(person, JsonNode.class);
		ArrayNode personsData = getDataContainer().getPersonsData();
	
		personsData.add(personNode);
		getDataContainer().setPersonsData(personsData);
		
		return person;
	}

	@Override
	public Person update(Person personUpdated) {
		Person personToUpdate = this.getOne( personUpdated.getFirstName() + personUpdated.getLastName());
	
		List<Person> persons = this.getAll();
		int index = persons.indexOf(personToUpdate);
		persons.set(index, personUpdated);
		
		ArrayNode newPersonsData = getObjectMapper().valueToTree(persons);
		getDataContainer().setPersonsData(newPersonsData);

		return personUpdated;
	}

	@Override
	public Person delete(Person person) {		
		List<Person> persons = this.getAll();
	
		Person personToDelete = this.getOne(person.getFirstName()+person.getLastName());
		int index = persons.indexOf(personToDelete);
		persons.remove(index);

		ArrayNode newPersonsData = getObjectMapper().valueToTree(persons);
		getDataContainer().setPersonsData(newPersonsData);

		return person;
	}

	

}
