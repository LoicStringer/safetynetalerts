package com.safetynet.safetynetalerts.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;



class DataContainerTest {

	private static DataContainer dataContainer;
	private static JsonNode jsonNode;

	@BeforeEach
	void setUp() {
		dataContainer = new DataContainer();
		jsonNode = null;
	}

	@Test
	void containDatatest() {
		jsonNode = dataContainer.containsData();
		assertNotNull(jsonNode);
		assertTrue(jsonNode.isContainerNode());
		assertEquals(3, jsonNode.size());
	}

	@Test
	void containsPersonDataTest() {
		jsonNode = dataContainer.containsPersonsData();
		assertNotNull(jsonNode);
		assertTrue(jsonNode.isContainerNode());
		assertEquals(23, jsonNode.size());

	}

	@Test
	void containsMedicalRecordsDataTest() {
		jsonNode = dataContainer.containsMedicalRecordsData();
		assertNotNull(jsonNode);
		assertTrue(jsonNode.isContainerNode());
		assertEquals(23, jsonNode.size());

	}
	@Test
	void containsFireStationsDataTest() {
		jsonNode = dataContainer.containsLinkedFireStationsData();
		assertNotNull(jsonNode);
		assertTrue(jsonNode.isContainerNode());
		assertEquals(13, jsonNode.size());
	}

}
