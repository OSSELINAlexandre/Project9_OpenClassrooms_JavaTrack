package osselin.patientmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osselin.patientmanagementapi.model.Patient;
import osselin.patientmanagementapi.service.PatientManagementService;

import java.util.List;

@RestController
@RequestMapping("patient")
public class PatientManagementController {


    @Autowired
    PatientManagementService patientService;
    


    @PostMapping("/add")
    public ResponseEntity<Patient> addAPatientToTheDatabase(@RequestBody Patient newPatient){

        if(newPatient.getLastName() == null || newPatient.getFirstName() == null || newPatient.getDateOfBirth() == null || newPatient.getGender() == null || newPatient.getId() != 0){

            return new ResponseEntity("You couldn't add the Patient to the Db, you need at least a last name, a first name, a date of birth and a gender to register a new Patient. Also, for integrity purposes, please do not put and ID.", HttpStatus.BAD_REQUEST);

        }else{
            Patient result = patientService.addANewPatientToTheDatabase(newPatient);
            if(result != null ) {
                return new ResponseEntity<Patient>(result, HttpStatus.OK);
            }else{
                return new ResponseEntity("This user already exist in the Database.", HttpStatus.BAD_REQUEST);
            }
        }


    }

    @PostMapping("/update")
    public ResponseEntity<Patient> updateAPatientInTheDatabase(@RequestParam("id") Integer patientId, @RequestBody Patient newAttributesForGivenPatient){


        if(newAttributesForGivenPatient.getId() != patientId ){

            return new ResponseEntity("Do not set a new ID in the body of the request , or set two different ID in the URL and in the Body,it can lead to database management issue. Also, don't forget to add the ID in the body of the request.", HttpStatus.BAD_REQUEST);
        }else {

            Patient result = patientService.updateAGivenPatient(patientId, newAttributesForGivenPatient);

            if (result != null) {

                return new ResponseEntity<Patient>(result, HttpStatus.OK);

            } else {

                return new ResponseEntity("This ID isn't related to any user. If the ID is related to the user, an other patient has already taken this name and family name.", HttpStatus.BAD_REQUEST);
            }
        }

    }

    @GetMapping("/delete")
    public ResponseEntity<Boolean> deleteAPatientInTheDatabase(@RequestParam("id") Integer patientId){

        Boolean result = patientService.deleteAGivenPatient(patientId);

        if(result){

            return new ResponseEntity<Boolean>(result, HttpStatus.OK);

        }else{

            return new ResponseEntity("The user couldn't be deleted", HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("/")
    public List<Patient> getAllThePatient(){

        return patientService.getAllTheList();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Patient> getASpecificPatient(@PathVariable("id") Integer id){

        try {
            Patient result = patientService.getASpecificPatient(id).get();
            return new ResponseEntity<Patient>(result, HttpStatus.OK);

        }catch(Exception e){

            return new ResponseEntity("Could not get this user form the Database with this id." , HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get/{firstName}/{lastName}")
    public ResponseEntity<Patient> getASpecificPatientBasedOnIdentity(@PathVariable("firstName") String firstName,@PathVariable("lastName") String familyName){

        try {
            Patient result = patientService.getASpecificPatientBasedOnIdentity(firstName, familyName).get();
            return new ResponseEntity<Patient>(result, HttpStatus.OK);

        }catch(Exception e){

            return new ResponseEntity("Could not get this user form the Database with this name and username." , HttpStatus.BAD_REQUEST);
        }

    }



}
