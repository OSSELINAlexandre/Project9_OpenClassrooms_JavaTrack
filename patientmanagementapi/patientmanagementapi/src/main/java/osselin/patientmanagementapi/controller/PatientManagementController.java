package osselin.patientmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osselin.patientmanagementapi.model.Patient;
import osselin.patientmanagementapi.service.PatientManagementService;

import java.util.List;

@RestController
public class PatientManagementController {

    @Autowired
    PatientManagementService patientService;

    @PostMapping("/patient/add")
    public ResponseEntity<Patient> addAPatientToTheDatabase(@RequestBody Patient newPatient){



        Patient result = patientService.addANewPatientToTheDatabase(newPatient);

        if(result != null){

            return new ResponseEntity<Patient>(result, HttpStatus.OK);

        }else{

            return new ResponseEntity("You couldn't add the Patient to the Db", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/patient/update")
    public ResponseEntity<Patient> updateAPatientInTheDatabase(@RequestParam("id") Integer patientId, @RequestBody Patient newAttributesForGivenPatient){

        Patient result = patientService.updateAGivenPatient(patientId, newAttributesForGivenPatient);

        if(result != null){

            return new ResponseEntity<Patient>(result, HttpStatus.OK);

        }else{

            return new ResponseEntity("You couldn't add the Patient to the Db", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/patient/delete")
    public ResponseEntity<String> deleteAPatientInTheDatabase(@RequestParam("id") Integer patientId){

        Boolean result = patientService.deleteAGivenPatient(patientId);

        if(result){

            return new ResponseEntity<String>("The user given has been deleted", HttpStatus.OK);

        }else{

            return new ResponseEntity<String>("The user couldn't be deleted", HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("/patient")
    public List<Patient> getAllThePatient(){

        return patientService.getAllTheList();
    }


}
