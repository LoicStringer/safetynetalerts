package com.safetynet.safetynetalerts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dao.LinkedFireStationDao;
import com.safetynet.safetynetalerts.dao.MedicalRecordDao;
import com.safetynet.safetynetalerts.dao.PersonDao;
import com.safetynet.safetynetalerts.model.LinkedFireStation;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.responseentity.EmergencyChildAlert;
import com.safetynet.safetynetalerts.responseentity.EmergencyFireAddressInfos;
import com.safetynet.safetynetalerts.responseentity.EmergencyFloodInfos;
import com.safetynet.safetynetalerts.responseentity.EmergencyFloodInfos.HomeInfo;
import com.safetynet.safetynetalerts.responseentity.EmergencyFloodInfos.StationInfos;
import com.safetynet.safetynetalerts.responseentity.InhabitantInfos;

@Service
public class EmergencyService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private LinkedFireStationDao linkedFireStationDao;

	@Autowired
	private MedicalRecordDao medicalRecordDao;

	@Autowired
	private CommunityService communityService;

	public EmergencyChildAlert getChildrenThere(String address) {
		EmergencyChildAlert childrenThere = new EmergencyChildAlert();
		
		List<Person> personsThere = getPersonsThere(address);
		
		personsThere.stream()
		.forEach(p -> childrenThere.addPerson(p.getFirstName(), p.getLastName(),getPersonAge(p)));
		return childrenThere;
	}

	public List<String> getCoveredPersonsPhoneNumbers(String stationNumber) {
		List<String> addressesCovered = getAdressesCoveredByFirestation(stationNumber);
		return personDao.getAll().stream()
				.filter(p -> addressesCovered.contains(p.getAddress()))
				.map(Person::getPhone)
				.collect(Collectors.toList());
	}
	
	public EmergencyFireAddressInfos getPersonsThereInfos (String address) {
		EmergencyFireAddressInfos emergencyFireAddressInfos = new EmergencyFireAddressInfos();
		
		emergencyFireAddressInfos.setStationNumber(getStationNumberCoveringAddress(address));
		
		List<Person> personsThere = getPersonsThere(address);
		List<String> identifiers = getPersonIdentifiers(personsThere);
		List<InhabitantInfos> inhabitantsThere = getInhabitantInfos(identifiers);
		
		emergencyFireAddressInfos.setInhabitantsThere(inhabitantsThere);
		
		return emergencyFireAddressInfos;
	}
	
	public EmergencyFloodInfos getCoveredHomesInfos(List<String>stationNumbers) {
		EmergencyFloodInfos emergencyFloodInfos = new EmergencyFloodInfos();
				
		stationNumbers.stream()
		.forEach(sn -> emergencyFloodInfos.addStationInfos(sn,getHomesInfos(getAdressesCoveredByFirestation(sn))));

		return emergencyFloodInfos;
	}
	
	public List<Person> getPersonsThere(String address){
		return personDao.getAll().stream()
				.filter(p -> p.getAddress().equals(address))
				.collect(Collectors.toList());
	}
	
	private int getPersonAge(Person p) {
		String birthDate = medicalRecordDao.getOne(p.getFirstName()+p.getLastName()).getBirthdate();
		return communityService.getAgeFromBirthDate(birthDate);
	}

	private List<String> getAdressesCoveredByFirestation(String stationNumber) {
		return linkedFireStationDao.getAll().stream()
				.filter(ad -> ad.getStation().equals(stationNumber))
				.map(LinkedFireStation::getAddress)
				.collect(Collectors.toList());
	}
		
	public String getStationNumberCoveringAddress(String address) {
		return linkedFireStationDao.getAll().stream()
				.filter(lfs -> lfs.getAddress().equals(address))
				.map(lfs -> lfs.getStation())
				.findAny().get();
	}
	
	public String getPhoneNumber(String identifier) {
		return personDao.getAll().stream()
				.filter(p -> (p.getFirstName()+p.getLastName()).equals(identifier))
				.map(p -> p.getPhone())
				.findAny().get();
	}
	
	public List<String> getPersonIdentifiers(List<Person> personsThere){
		return personsThere.stream()
				.map(p -> p.getFirstName()+p.getLastName())
				.collect(Collectors.toList());
	}
	
	public List<InhabitantInfos> getInhabitantInfos(List<String> identifiers){
		return medicalRecordDao.getAll().stream()
				.filter(mr -> identifiers.contains(mr.getFirstName()+mr.getLastName()))
				.map(temp ->{
					InhabitantInfos inhabitantThere = new InhabitantInfos();
					inhabitantThere.setFirstName(temp.getFirstName());
					inhabitantThere.setLastName(temp.getLastName());
					inhabitantThere.setAge(Integer.valueOf(communityService.getAgeFromBirthDate(temp.getBirthdate())));
					inhabitantThere.setPhoneNumber(getPhoneNumber(temp.getFirstName()+temp.getLastName()));
					inhabitantThere.setMedications(temp.getMedications());
					inhabitantThere.setAllergies(temp.getAllergies());
					return inhabitantThere;
				}).collect(Collectors.toList());
	}
	
	public List<HomeInfo> getHomesInfos (List<String> addresses){
		return addresses.stream()
				.map(ad -> {
					HomeInfo homeInfo = new EmergencyFloodInfos().new HomeInfo();
					homeInfo.setAddress(ad);
					homeInfo.setInhabitantsThere(getInhabitantInfos(getPersonIdentifiers(getPersonsThere(ad))));
					return homeInfo;
				}).collect(Collectors.toList());
	}
	
}
