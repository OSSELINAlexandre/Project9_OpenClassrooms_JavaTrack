package osselin.patientmanagementapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import osselin.patientmanagementapi.repository.PatientManagementRepository;

/**
 * <b>PatientmanagementapiApplication is the main class that launch the application.</b>
 *
 * <p>
 * PatientManagementAPI correspond to the first sprint of the project. The purpose of this API is to manage the creation, modification, suppression of patient in the database.
 * </p>
 *<p>It is connected to a MySQL database outside of the environment. </p>
 *
 * @author Alexandre Osselin
 * @version 1.0
 */
@SpringBootApplication
public class PatientmanagementapiApplication {

	@Autowired
	PatientManagementRepository patientRepo;

	public static void main(String[] args) {
		SpringApplication.run(PatientmanagementapiApplication.class, args);
	}

}
