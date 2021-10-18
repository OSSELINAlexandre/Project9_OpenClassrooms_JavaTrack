package osselin.doctorinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DoctorinterfaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorinterfaceApplication.class, args);
	}

}
