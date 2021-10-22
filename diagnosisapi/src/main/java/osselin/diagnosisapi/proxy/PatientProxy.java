package osselin.diagnosisapi.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osselin.diagnosisapi.model.Patient;

import java.util.List;
/**
 * <p>PatientProxy is the Feign proxy that communicates with the Api in charge of the management of the patients.</p>
 *
 * <p>It centralizes all endpoints existing the in API.</p>
 *
 *
 * <p>If you want to launch the application, be sure to use 'host.docker.internal' if you dockerize it or 'localhost' if you launch the app as a classic java app.</p>
 *
 */
@FeignClient(name ="patientmanagementapi", url = "host.docker.internal:8081")
public interface PatientProxy {

    @PostMapping("/patient/add")
    ResponseEntity addThePatient(@RequestBody Patient thePatient);

    @PostMapping("/patient/update")
    ResponseEntity updateThePatient(@RequestParam("id") Integer patientId, @RequestBody Patient thePatient);

    @GetMapping("/patient/delete")
    ResponseEntity deleteAPatient(@RequestParam("id") int theId);

    @GetMapping("/patient/")
    List<Patient> getAllTheListOfPatient();

    @GetMapping("/patient/get/{id}")
    ResponseEntity<Patient> getASpecificPatient(@PathVariable("id") int theId);

    @GetMapping("/patient/get/{firstName}/{lastName}")
    ResponseEntity<Patient> getASpecificPatientBasedOnFamillyAndFirstName(@PathVariable("firstName") String firstName,@PathVariable("lastName") String familyName);
}
