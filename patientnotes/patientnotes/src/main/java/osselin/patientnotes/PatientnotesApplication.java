package osselin.patientnotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import osselin.patientnotes.repository.PatientNotesRepository;

@SpringBootApplication
public class PatientnotesApplication implements CommandLineRunner {

	@Autowired
	private PatientNotesRepository patientRepo;

	public static void main(String[] args) {
		SpringApplication.run(PatientnotesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


	}
}
