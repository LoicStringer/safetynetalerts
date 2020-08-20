package com.safetynet.safetynetalerts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.exceptions.DuplicatedPersonException;
import com.safetynet.safetynetalerts.exceptions.PersonNotFoundException;
import com.safetynet.safetynetalerts.exceptions.PersonsDataNotFoundException;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	private Logger log =LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PersonService personService;
	
	@PostMapping("")
	public ResponseEntity<Person> insertPerson(@RequestBody Person person) throws DuplicatedPersonException, PersonsDataNotFoundException{
		
		log.info(System.lineSeparator()+"User has entered \"/person\" endpoint (GET request) to insert a new person: "+person.getFirstName()+" "+person.getLastName()+" ."
				+System.lineSeparator()+ "Request has returned :" +person.toString());
		
		return ResponseEntity.ok(personService.insertPerson(person));
	}
	
	@PutMapping("")
	public ResponseEntity<Person> updatePerson(@RequestBody Person person) throws PersonsDataNotFoundException, PersonNotFoundException, DuplicatedPersonException{
		
		log.info(System.lineSeparator()+"User has entered the PUT \"/person\" endpoint (PUT request) to update "+person.getFirstName()+" "+person.getLastName()+" ."
				+System.lineSeparator()+ "Request has returned :" +person.toString());
		
		return ResponseEntity.ok(personService.updatePerson(person));
	}
	
	@DeleteMapping("")
	public ResponseEntity<Person> deletePerson(@RequestBody Person person) throws PersonsDataNotFoundException, PersonNotFoundException, DuplicatedPersonException{
		
		log.info(System.lineSeparator()+"User has entered the DELETE \"/person\" endpoint (DELETE request) to delete "+person.getFirstName()+" "+person.getLastName()+" ."
				+System.lineSeparator()+ "Request has returned :" +person.toString());
		
		return ResponseEntity.ok(personService.deletePerson(person));
	}

}
