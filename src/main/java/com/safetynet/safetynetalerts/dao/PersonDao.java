package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataContainer;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.Person;

@Component
public class PersonDao extends DataProvider implements IDao<Person> {

	
	
	@Override
	public List<Person> getAll() throws DataImportFailedException, UnavailableDataException, EmptyDataException {
		
		List<Person> persons = new ArrayList<Person>();

			try {
				getDataContainer().getPersonsData();
			} catch (DataImportFailedException e) {
				throw new DataImportFailedException(
						"A problem occured while querying persons list from the data container", e);
			} catch (UnavailableDataException e) {
				throw new UnavailableDataException("Persons list is null", e);
			} catch (EmptyDataException e) {
				throw new EmptyDataException("Persons list is empty", e);
			}

		Iterator<JsonNode> elements = DataContainer.personsData.elements();
		while (elements.hasNext()) {
			JsonNode personNode = elements.next();
			Person person = getObjectMapper().convertValue(personNode, Person.class);
			persons.add(person);
		}

		return persons;
	}

	@Override
	public Person getOne(String identifier)
			throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {

		Person personToGet= new Person();
		List<Person> persons  = this.getAll();
		
		personToGet = persons.stream()
				.filter(p -> (p.getFirstName()+p.getLastName()).equalsIgnoreCase(identifier))
				.findAny().orElse(null);
		
		if (personToGet==null)
			throw new ItemNotFoundException("No person found for identifier " + identifier);

		return personToGet;
	}

	@Override
	public Person insert(Person person)
			throws DuplicatedItemException, DataImportFailedException, UnavailableDataException, EmptyDataException {

		this.checkForDuplication(person);
		
		JsonNode personNode = getObjectMapper().convertValue(person, JsonNode.class);
		DataContainer.personsData.add(personNode);
	
		return person;
	}

	@Override
	public Person update(Person personUpdated)
			throws DataImportFailedException, UnavailableDataException, EmptyDataException, ItemNotFoundException {

		Person personToUpdate = this.getOne(personUpdated.getFirstName() + personUpdated.getLastName());
		List<Person> persons = this.getAll();
		
		int index = persons.indexOf(personToUpdate);
		persons.set(index, personUpdated);

		ArrayNode newPersonsData = getObjectMapper().valueToTree(persons);
		DataContainer.personsData = newPersonsData;
	
		return personUpdated;
	}

	@Override
	public Person delete(Person person)
			throws UnavailableDataException, EmptyDataException, DataImportFailedException, ItemNotFoundException {

		List<Person> persons = this.getAll();
		Person personToDelete = this.getOne(person.getFirstName() + person.getLastName());
		
		int index = persons.indexOf(personToDelete);
		persons.remove(index);

		ArrayNode newPersonsData = getObjectMapper().valueToTree(persons);
		DataContainer.personsData= newPersonsData;
		
		return person;
	}

	private void checkForDuplication(Person person)
			throws UnavailableDataException, EmptyDataException, DuplicatedItemException, DataImportFailedException {
		if (this.getAll().stream().anyMatch(p -> (p.getFirstName() + p.getLastName())
				.equalsIgnoreCase(person.getFirstName() + person.getLastName())))
			throw new DuplicatedItemException(
					"Warning : a person with the same firstname and lastname "+person.getFirstName()+" "+person.getLastName()+" already exists in data container");
	}
	
}
