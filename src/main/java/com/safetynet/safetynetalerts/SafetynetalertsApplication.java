package com.safetynet.safetynetalerts;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootApplication
public class SafetynetalertsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetalertsApplication.class, args);

		PersonDao personDao = new PersonDao();
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Boyd");
		
		personDao.delete(person);
		
		List<Person> persons = personDao.getAll();
		System.out.println(persons.get(0).getFirstName());
		
	}

}
