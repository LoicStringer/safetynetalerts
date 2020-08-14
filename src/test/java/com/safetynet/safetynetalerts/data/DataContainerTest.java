package com.safetynet.safetynetalerts.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
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

	@Test
	void isContainingAllPersonDataTest()
			throws DataImportFailedException, UnavailableDataException, EmptyDataException {

		when(dataAccessor.getFilePath()).thenReturn("src/main/resources/data.json");
		when(dataAccessor.getPersonsNode()).thenReturn("persons");

		dataContainer.getPersonsData();

		assertNotNull(DataContainer.personsData);
		assertTrue(DataContainer.personsData.isContainerNode());
		assertEquals(23, DataContainer.personsData.size());
	}

	@Test
	void isContainingAllMedicalRecordsDataTest()
			throws DataImportFailedException, UnavailableDataException, EmptyDataException {

		when(dataAccessor.getFilePath()).thenReturn("src/main/resources/data.json");
		when(dataAccessor.getMedicalRecordsNode()).thenReturn("medicalrecords");

		dataContainer.getMedicalRecordsData();

		assertNotNull(DataContainer.medicalRecordsData);
		assertTrue(DataContainer.medicalRecordsData.isContainerNode());
		assertEquals(23, DataContainer.medicalRecordsData.size());
	}

	@Test
	void isContainingAllFireStationsDataTest()
			throws DataImportFailedException, UnavailableDataException, EmptyDataException {

		when(dataAccessor.getFilePath()).thenReturn("src/main/resources/data.json");
		when(dataAccessor.getLinkedFireStationsNode()).thenReturn("firestations");

		dataContainer.getLinkedFireStationsData();

		assertNotNull(DataContainer.linkedFireStationsData);
		assertTrue(DataContainer.linkedFireStationsData.isContainerNode());
		assertEquals(13, DataContainer.linkedFireStationsData.size());
	}

	@Test
	void isThrowingExpectedExceptionWhenJsonIsEmpty()
			throws DataImportFailedException, JsonProcessingException, IOException {

		DataContainer.personsData=null;
		
		when(dataAccessor.getFilePath()).thenReturn("src/main/resources/testFile.json");
		when(dataAccessor.getPersonsNode()).thenReturn("persons");
		

		Exception expectedException = assertThrows(EmptyDataException.class, () -> dataContainer.getPersonsData());

		assertEquals(expectedException.getMessage(), "Warning : the data source is empty !");
	}

	@Test
	void isThrowingExpectedExceptionWhenJsonIsInvalid() throws DataImportFailedException {

		DataContainer.personsData=null;
		
		when(dataAccessor.getFilePath()).thenReturn("src/main/resources/testFile.json");
		when(dataAccessor.getPersonsNode()).thenReturn(null);

		Exception expectedException = assertThrows(UnavailableDataException.class,
				() -> dataContainer.getPersonsData());

		assertEquals(expectedException.getMessage(), "Warning : the data source or the file path is invalid !");
	}

}
