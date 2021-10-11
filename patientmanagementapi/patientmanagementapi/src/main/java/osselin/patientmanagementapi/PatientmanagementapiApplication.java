package osselin.patientmanagementapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import osselin.patientmanagementapi.repository.PatientManagementRepository;


@SpringBootApplication
public class PatientmanagementapiApplication implements CommandLineRunner {

	@Autowired
	PatientManagementRepository patientRepo;

	public static void main(String[] args) {
		SpringApplication.run(PatientmanagementapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {






	}
}
