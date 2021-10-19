package osselin.doctorinterface.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osselin.doctorinterface.model.Patient;

import java.util.List;
import java.util.Optional;

@FeignClient(name ="patientmanagementapi", url = "localhost:8081")
public interface PatientManagementProxy {

    @PostMapping("/patient/add")
    ResponseEntity addThePatient(@RequestBody Patient thePatient);

    @PostMapping("/patient/update")
    ResponseEntity updateThePatient(@RequestParam("id") Integer patientId, @RequestBody Patient thePatient);

    @PostMapping("/patient/delete")
    ResponseEntity deleteAPatient(@RequestParam("id") int theId);

    @GetMapping("/patient")
    List<Patient> getAllTheListOfPatient();

    @GetMapping("/patient/{id}")
    Optional<Patient> getASpecificPatient(@PathVariable("id") int theId);

}