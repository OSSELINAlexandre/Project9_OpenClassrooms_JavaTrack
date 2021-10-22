package osselin.doctorinterface.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osselin.doctorinterface.model.Patient;

import java.util.List;

/**
 * <p>PatientManagementProxy is the Feign proxy that communicates with the Api in charge of the management of the patients.</p>
 *
 * <p>It centralizes all endpoints existing the in API.</p>
 *
 *
 */
@FeignClient(name ="patientmanagementapi", url = "localhost:8081")
public interface PatientManagementProxy {

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

}
