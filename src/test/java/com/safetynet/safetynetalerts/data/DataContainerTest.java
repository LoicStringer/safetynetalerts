package com.safetynet.safetynetalerts.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.fasterxml.jackson.databind.node.ArrayNode;


class DataContainerTest {

	private ArrayNode arrayNode;
	private DataContainer dataContainer;

	@BeforeEach
	void setUp() {
		arrayNode = null;
		dataContainer = new DataContainer();
	}

	@Test
	void isContainingAllPersonDataTest() {
		arrayNode = dataContainer.getPersonsData();
		assertNotNull(arrayNode);
		assertTrue(arrayNode.isContainerNode());
		assertEquals(23, arrayNode.size());
	}

	@Test
	void isContainingAllMedicalRecordsDataTest() {
		arrayNode = dataContainer.getMedicalRecordsData();
		assertNotNull(arrayNode);
		assertTrue(arrayNode.isContainerNode());
		assertEquals(23, arrayNode.size());
	}
	
	@Test
	void isContainingAllFireStationsDataTest() {
		arrayNode = dataContainer.getLinkedFireStationsData();
		assertNotNull(arrayNode);
		assertTrue(arrayNode.isContainerNode());
		assertEquals(13, arrayNode.size());
	}

}
