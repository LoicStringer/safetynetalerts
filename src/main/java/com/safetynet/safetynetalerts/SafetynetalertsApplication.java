package com.safetynet.safetynetalerts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootApplication
public class SafetynetalertsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetalertsApplication.class, args);

		LinkedFireStation linkedFireStation = new LinkedFireStation();
		linkedFireStation.setAddress("1509 Culver St");
		linkedFireStation.setStation("3");
		
		LinkedFireStationDao linkedFireStationDao = new LinkedFireStationDao();
		
	}

}
