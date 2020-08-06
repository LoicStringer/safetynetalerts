package com.safetynet.safetynetalerts.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;

@Tag("DataContainerTests")
@DisplayName("Import data from Json file")
@ExtendWith(MockitoExtension.class)
class DataContainerTest {
	
	@Mock
	private DataAccessor dataAccessor;
	
	@InjectMocks
	private DataContainer dataContainer;
	
	private ArrayNode arrayNode;

	@BeforeEach
	void setUp() {
		
		arrayNode = null;
		when(dataAccessor.getFiletpath()).thenReturn("src/main/resources/data.json");
	}

	@Test
	void isContainingAllPersonDataTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException {
		when(dataAccessor.getPersonsNode()).thenReturn("persons");
		arrayNode = dataContainer.getPersonsData();
		
		assertNotNull(arrayNode);
		assertTrue(arrayNode.isContainerNode());
		assertEquals(23, arrayNode.size());
	}

	@Test
	void isContainingAllMedicalRecordsDataTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException {
		when(dataAccessor.getMedicalRecordsNode()).thenReturn("medicalrecords");
		arrayNode = dataContainer.getMedicalRecordsData();
		
		assertNotNull(arrayNode);
		assertTrue(arrayNode.isContainerNode());
		assertEquals(23, arrayNode.size());
	}

	@Test
	void isContainingAllFireStationsDataTest() throws DataImportFailedException, UnavailableDataException, EmptyDataException {
		when(dataAccessor.getLinkedFireStationsNode()).thenReturn("firestations");
		arrayNode = dataContainer.getLinkedFireStationsData();
		
		assertNotNull(arrayNode);
		assertTrue(arrayNode.isContainerNode());
		assertEquals(13, arrayNode.size());
	}

	@Test
	void isThrowingExpectedExceptionWhenJsonIsEmpty() throws DataImportFailedException {
		
		when(dataAccessor.getFiletpath()).thenReturn("src/main/resources/testFile.json");
		when(dataAccessor.getPersonsNode()).thenReturn("persons");
		
		Exception expectedException = assertThrows(EmptyDataException.class, ()-> dataContainer.getPersonsData());
		
		assertEquals(expectedException.getMessage(),"Warning : the data source is empty !");
	}
	
	@Test
	void isThrowingExpectedExceptionWhenJsonIsInvalid() throws DataImportFailedException {
		
		when(dataAccessor.getFiletpath()).thenReturn("src/main/resources/testFile.json");
		when(dataAccessor.getPersonsNode()).thenReturn(null);
		
		Exception expectedException = assertThrows(UnavailableDataException.class, ()-> dataContainer.getPersonsData());
		
		assertEquals(expectedException.getMessage(),"Warning : the data source or the file path is invalid !");		
	}
	
}
