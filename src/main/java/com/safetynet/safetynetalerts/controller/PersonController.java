package com.safetynet.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.Person;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonDao personDao;
	
	
	@PutMapping("")
	public ResponseEntity<Person> insertPerson(@RequestBody Person person){
		
		personDao.insert(person);
		
		return ResponseEntity.ok(person);
	}
	
	

}
