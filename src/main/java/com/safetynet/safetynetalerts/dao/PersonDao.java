package com.safetynet.safetynetalerts.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.data.DataContainer;
import com.safetynet.safetynetalerts.data.DataProvider;
import com.safetynet.safetynetalerts.exceptions.DuplicatedItemException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.ItemNotFoundException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;
import com.safetynet.safetynetalerts.model.Person;

@Component
public class PersonDao extends DataProvider implements IDao<Person> {

	private Logger log =LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<Person> getAll() throws UnavailableDataException, EmptyDataException {
		
		try {
			getDataContainer().checkDataIntegrity(DataContainer.personsData);
		} catch (UnavailableDataException e) {
			throw new UnavailableDataException("Persons list is null", e);
		} catch (EmptyDataException e) {
			throw new EmptyDataException("Persons list is empty", e);
		}
		
		List<Person> persons = new ArrayList<Person>();
		
		Iterator<JsonNode> elements = DataContainer.personsData.elements();
		while (elements.hasNext()) {
			JsonNode personNode = elements.next();
			Person person = getObjectMapper().convertValue(personNode, Person.class);
			persons.add(person);
		}

		return persons;
	}

	@Override
	public  Person getOne(String identifier)
			throws UnavailableDataException, EmptyDataException, ItemNotFoundException {

		Person personToGet= new Person();
		List<Person> persons  = this.getAll();
		
		personToGet = persons.stream()
				.filter(p -> (p.getFirstName()+p.getLastName()).equalsIgnoreCase(identifier))
				.findAny().orElse(null);
		
		if (personToGet==null)
			throw new ItemNotFoundException("No person found for identifier " + identifier);

		return personToGet;
	}

	public List<Person> getDuplicatedItems(String identifier) 
			throws UnavailableDataException, EmptyDataException, ItemNotFoundException{
		
		List<Person> homonymousPersons = this.getAll().stream()
				.filter(p -> (p.getFirstName()+p.getLastName()).equalsIgnoreCase(identifier))
				.collect(Collectors.toList());
		
		if(homonymousPersons.isEmpty())
			throw new ItemNotFoundException("No person found for identifier " + identifier);
		
		return homonymousPersons;
	}
	
	@Override
	public Person insert(Person person)
			throws UnavailableDataException, EmptyDataException {

		long duplicated = checkForDuplication(person.getFirstName()+person.getLastName());
		
		if(duplicated>=1) 
			log.warn("Warning! "+duplicated+" persons identified by "+person.getFirstName()+" "+person.getLastName()+" are registered in data container.");
		
		JsonNode personNode = getObjectMapper().convertValue(person, JsonNode.class);
		DataContainer.personsData.add(personNode);
	
		return person;
	}

	@Override
	public Person update(Person personUpdated)
			throws UnavailableDataException, EmptyDataException, ItemNotFoundException, DuplicatedItemException {
	
		long duplicated = checkForDuplication(personUpdated.getFirstName()+personUpdated.getLastName());
		
		if(duplicated>1) {
			List<Person> homonymousPersons = this.getDuplicatedItems(personUpdated.getFirstName()+personUpdated.getLastName());
			throw new DuplicatedItemException("Warning! "+duplicated+" persons identified by "+personUpdated.getFirstName()+" "+personUpdated.getLastName()+" are registered in data container: "
			+homonymousPersons+" . Not able to determine which one has to be updated.");
		}
		
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
			throws UnavailableDataException, EmptyDataException, ItemNotFoundException, DuplicatedItemException {

		long duplicated = checkForDuplication(person.getFirstName()+person.getLastName());
		
		if(duplicated>1) {
			List<Person> homonymousPersons = this.getDuplicatedItems(person.getFirstName()+person.getLastName());
			throw new DuplicatedItemException("Warning! "+duplicated+" persons are registered in data container: "
			+homonymousPersons+" . Not able to determine which one has to be deleted.");
		}
		
		List<Person> persons = this.getAll();
		Person personToDelete = this.getOne(person.getFirstName() + person.getLastName());
		
		int index = persons.indexOf(personToDelete);
		persons.remove(index);

		ArrayNode newPersonsData = getObjectMapper().valueToTree(persons);
		DataContainer.personsData= newPersonsData;
		
		return person;
	}

	private long checkForDuplication(String identifier) throws UnavailableDataException, EmptyDataException {
		
		return this.getAll().stream()
				.filter(p -> (p.getFirstName()+p.getLastName()).equalsIgnoreCase(identifier))
				.count();
	}
}
