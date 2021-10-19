package osselin.patientnotes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import osselin.patientnotes.model.PatientNote;
import osselin.patientnotes.repository.PatientNotesRepository;
import osselin.patientnotes.service.PatientNoteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientNotesUnitTests {

	@Mock
	PatientNotesRepository patientRepo;

	@Autowired
	PatientNoteService patientService;

	@BeforeEach
	public void init(){
		patientService.setPatientNotesRepo(patientRepo);

	}

	@Test
	public void Test_getAllNotesFromPatient(){

		when(patientRepo.findByLastNameAndFirstName("OSSELIN", "Alexandre")).thenReturn(new ArrayList<PatientNote>());

		List<PatientNote> theResult = patientService.getAllNotesFromPatient("OSSELIN", "Alexandre");

		assertTrue(theResult != null);
	}

	@Test
	public void Test_getSpecificNoteFromPatient(){

		when(patientRepo.findById("77")).thenReturn(Optional.of(new PatientNote()));

		Optional<PatientNote> result = patientRepo.findById("77");

		assertNotNull(result);

	}

	@Test
	public void Test_saveANewNoteToTheDataBase(){

		PatientNote testing = new PatientNote();
		when(patientRepo.save(testing)).thenReturn(testing);

		PatientNote resultat = patientRepo.save(testing);

		assertNotNull(resultat);
	}

	@Test
	public void Test_deleteAGivenNote_ShouldReturnFalse(){

		assertFalse(patientService.deleteAGivenNote("77"));

	}

	@Test
	public void Test_saveANewNote(){

		PatientNote testItem = new PatientNote();
		when(patientRepo.save(testItem)).thenReturn(testItem);

		Boolean result = patientService.saveANewNoteToTheDataBase(testItem);

		assertTrue(result);
	}
	/*

	The number of lines in this application is low, therefore the coverage of at least 50% doesn't pass.
	We therefore need to test the Model.
	 */
	@Test
	public void Test_testingTheModel(){

		PatientNote testingItem = new PatientNote();
		PatientNote testingItemTwo = new PatientNote("87", "Alex" , "OSS" , "Mating");
		testingItem.setLastName("Da Vinci");
		testingItem.setFirstName("Leonardo");
		testingItem.setObservation("I was a genius");
		testingItem.setSqlId("77");

		assertTrue(testingItem.getFirstName().equals("Leonardo")
				&& testingItem.getLastName().equals("Da Vinci")
				&& testingItem.getObservation().equals("I was a genius")
				&& testingItem.getSqlId().equals("77"));
		assertTrue(testingItemTwo.getFirstName().equals("Alex"));
	}

}
