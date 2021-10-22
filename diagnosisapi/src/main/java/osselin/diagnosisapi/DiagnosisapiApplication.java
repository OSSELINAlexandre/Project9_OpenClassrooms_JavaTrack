package osselin.diagnosisapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <b>DiagnosisapiApplication is the main class that launch the application.</b>
 *
 * <p>
 * DiagnosisapiApplication correspond to the third sprint of the project. The purpose of this API is to create diagnostic for the docter based on patient notes.
 * </p>
 *<p>It communicates with the APIs in charge of the management of patient and the one in charge of the management of the notes. </p>
 *
 * @author Alexandre Osselin
 * @version 1.0
 */
@SpringBootApplication
@EnableFeignClients
public class DiagnosisapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiagnosisapiApplication.class, args);
	}

}
