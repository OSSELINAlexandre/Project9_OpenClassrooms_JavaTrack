package osselin.patientnotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import osselin.patientnotes.repository.PatientNotesRepository;


/**
 * <b>PatientnotesApplication is the main class that launch the application.</b>
 *
 * <p>
 * PatientnotesApplication correspond to the second sprint of the project. The purpose of this API is to manage the creation, modification, suppression of patient's Notes in the database.
 * </p>
 *	<p>It is connected to a MongoDb database outside of the environment. </p>
 *
 * @author Alexandre Osselin
 * @version 1.0
 */

@SpringBootApplication
public class PatientnotesApplication {

	@Autowired
	private PatientNotesRepository patientRepo;

	public static void main(String[] args) {
		SpringApplication.run(PatientnotesApplication.class, args);
	}

}
