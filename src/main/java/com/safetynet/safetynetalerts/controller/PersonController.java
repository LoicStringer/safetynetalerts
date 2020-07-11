package com.safetynet.safetynetalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping("")
	public ResponseEntity<List<Person>> getAllPersons(){
		return ResponseEntity.ok(personService.getAllPersons());
	}
	
	@GetMapping("/{identifier}")
	public ResponseEntity<Person> getOnePerson(@PathVariable("identifier") String identifier){
		return ResponseEntity.ok(personService.getOnePerson(identifier));
	}
	
	@PostMapping("")
	public ResponseEntity<Person> insertPerson(@RequestBody Person person){
		
		personService.insertPerson(person);
		
		return ResponseEntity.ok(person);
	}
	
	@PutMapping("")
	public ResponseEntity<Person> updatePerson(@RequestBody Person person){
		return ResponseEntity.ok(personService.updatePerson(person));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<Boolean> deletePerson(@PathVariable("identifier") String identifier){
		return ResponseEntity.ok(personService.deletePerson(identifier));
	}

}
