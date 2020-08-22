package com.safetynet.safetynetalerts.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.safetynetalerts.exceptions.DataImportFailedException;
import com.safetynet.safetynetalerts.exceptions.EmptyDataException;
import com.safetynet.safetynetalerts.exceptions.UnavailableDataException;

@Tag("DataContainerTests")
@DisplayName("Import data from Json file")
@ExtendWith(MockitoExtension.class)
class DataContainerTest {

	private DataContainer dataContainer = new DataContainer();
	
	@Test
	void isContainingAllPersonDataTest()
			throws DataImportFailedException, UnavailableDataException, EmptyDataException {

		DataContainer.reloadDataForTests();
		
		assertNotNull(DataContainer.personsData);
		assertTrue(DataContainer.personsData.isContainerNode());
		assertEquals(23, DataContainer.personsData.size());
	}

	@Test
	void isContainingAllMedicalRecordsDataTest()
			throws DataImportFailedException, UnavailableDataException, EmptyDataException {

		DataContainer.reloadDataForTests();
		
		assertNotNull(DataContainer.medicalRecordsData);
		assertTrue(DataContainer.medicalRecordsData.isContainerNode());
		assertEquals(23, DataContainer.medicalRecordsData.size());
	}

	@Test
	void isContainingAllFireStationsDataTest()
			throws DataImportFailedException, UnavailableDataException, EmptyDataException {
		
		DataContainer.reloadDataForTests();
		
		assertNotNull(DataContainer.linkedFireStationsData);
		assertTrue(DataContainer.linkedFireStationsData.isContainerNode());
		assertEquals(13, DataContainer.linkedFireStationsData.size());
	}

	@Test
	void isThrowingExpectedExceptionWhenJsonIsEmpty()
			throws DataImportFailedException, JsonProcessingException, IOException {

		DataContainer.personsData.removeAll();
		
		Exception expectedException = assertThrows(EmptyDataException.class, 
				() -> dataContainer.checkDataIntegrity(DataContainer.personsData));
		assertEquals(expectedException.getMessage(), "Warning : the data source is empty !");
	}
		
	@Test
	void isThrowingExpectedExceptionWhenJsonIsInvalid() {

		DataContainer.personsData=null;
		
		Exception expectedException = assertThrows(UnavailableDataException.class,
				() -> dataContainer.checkDataIntegrity(DataContainer.personsData));
		assertEquals(expectedException.getMessage(), "Warning : the data source or the file path is invalid !");
	}

}
