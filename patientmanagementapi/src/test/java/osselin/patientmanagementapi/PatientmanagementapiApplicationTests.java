package osselin.patientmanagementapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import osselin.patientmanagementapi.model.Patient;
import osselin.patientmanagementapi.repository.PatientManagementRepository;
import osselin.patientmanagementapi.service.PatientManagementService;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PatientmanagementapiApplicationTests {

	@Autowired
	PatientManagementService patientService;

	@Autowired
	PatientManagementRepository patientRepo;

	Patient testingPatient;


	@BeforeEach
	public void init(){

		Date testDate = new Date(2020, 12, 25);
		testingPatient = new Patient("Claude", "OSSELIN", testDate, 'M', "32 rue du Moulin", "0160693539");

	}


	@Test
	void addingAPatientShouldReturnTrue() {

		Date testDate = new Date(2020, 12, 25);
		Patient testingPatientThree = new Patient("Robert", "Deniro", testDate, 'M', "32 rue du Moulin", "0160693539");

		System.out.println(patientRepo.findByFirstNameAndLastName(testingPatientThree.getFirstName(), testingPatientThree.getLastName()));

		Patient result = patientService.addANewPatientToTheDatabase(testingPatientThree);
		assertTrue(result != null);
		patientRepo.delete(testingPatientThree);

	}

	@Test
	void addingAPatientAlreadyExistingShouldReturnFalse() {

		patientService.addANewPatientToTheDatabase(testingPatient);
		Patient result = patientService.addANewPatientToTheDatabase(testingPatient);

		assertTrue( result == null);
		patientRepo.delete(testingPatient);

	}

	@Test
	void updatingAnExistingPatientShouldReturnTrue(){

		patientService.addANewPatientToTheDatabase(testingPatient);
		Patient result = patientRepo.findByFirstNameAndLastName(testingPatient.getFirstName(), testingPatient.getLastName()).get();
		Date testDate = new Date(2020, Calendar.DECEMBER, 25);
		Patient testingPatientTwo = new Patient("Peterson", "Friederich", testDate, 'F', "32 rue du Moulin", "0160693539");
		Patient finalResult = patientService.updateAGivenPatient(result.getId(), testingPatientTwo);
		assertTrue(finalResult != null);

		patientRepo.delete(testingPatient);
		patientRepo.delete(finalResult);

	}

	@Test
	void updatingANoneExistingPatientShouldReturnFalse(){
		patientService.addANewPatientToTheDatabase(testingPatient);
		Date testDate = new Date(2020, Calendar.DECEMBER, 25);

		testingPatient = new Patient(Integer.MAX_VALUE, "Peterson", "Friederich", testDate, 'F', "32 rue du Moulin", "0160693539");
		Patient result = patientService.updateAGivenPatient(Integer.MAX_VALUE, testingPatient);
		assertTrue(result == null );

		patientRepo.delete(testingPatient);
	}

	@Test
	void deletingAnExistingPatientShouldReturnTrue(){

		patientService.addANewPatientToTheDatabase(testingPatient);
		Patient result = patientRepo.findByFirstNameAndLastName(testingPatient.getFirstName(), testingPatient.getLastName()).get();
		Boolean finalResult = patientService.deleteAGivenPatient(result.getId());
		assertTrue(finalResult);


	}

	@Test
	void deletingAnNoneExistingPatientShouldReturnFalse(){
		Boolean result = patientService.deleteAGivenPatient(Integer.MAX_VALUE);
		assertFalse(result);

	}



}
