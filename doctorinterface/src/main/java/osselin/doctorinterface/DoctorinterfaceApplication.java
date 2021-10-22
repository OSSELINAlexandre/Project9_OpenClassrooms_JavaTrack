package osselin.doctorinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <b>DoctorinterfaceApplication is the main class that launch the application.</b>
 *
 * <p>
 * DoctorinterfaceApplication makes the link with all the API in order to provide an interface for the doctor.
 * </p>
 *
 * <p>It was part of the first sprint requirements, and was also a requirement for the second sprint.</p>
 *
 * @author Alexandre Osselin
 * @version 1.0
 */
@SpringBootApplication
@EnableFeignClients
public class DoctorinterfaceApplication {

	public static void main(String[] args) {

		SpringApplication.run(DoctorinterfaceApplication.class, args);
	}

}
