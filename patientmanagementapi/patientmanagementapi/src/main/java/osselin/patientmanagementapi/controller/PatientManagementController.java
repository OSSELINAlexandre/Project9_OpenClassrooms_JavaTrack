package osselin.patientmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import osselin.patientmanagementapi.model.Patient;
import osselin.patientmanagementapi.service.PatientManagementService;

import java.util.List;

@RestController
public class PatientManagementController {

    @Autowired
    PatientManagementService patientService;

    @PostMapping("/patient/add")
    public void addAPatientToTheDatabase(@RequestBody Patient newPatient){

        Boolean result = patientService.addANewPatientToTheDatabase(newPatient);


    }

    @PostMapping("/patient/update")
    public void updateAPatientInTheDatabase(@RequestParam("id") Integer patientId, @RequestBody Patient newAttributesForGivenPatient){

        Boolean result = patientService.updateAGivenPatient(patientId, newAttributesForGivenPatient);

        if(result){
            System.out.println("Good LOL");
        }else{
            System.out.println("BAD lol");
        }

    }

    @PostMapping("/patient/delete")
    public void deleteAPatientInTheDatabase(@RequestParam("id") Integer patientId){

        Boolean result = patientService.deleteAGivenPatient(patientId);

        if(result){
            System.out.println("Good LOL");
        }else{
            System.out.println("BAD lol");
        }
    }

    @GetMapping("/patient")
    public List<Patient> getAllThePatient(){

        return patientService.getAllTheList();
    }


}
