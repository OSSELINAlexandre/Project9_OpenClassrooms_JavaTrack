package osselin.patientmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osselin.patientmanagementapi.model.Patient;
import osselin.patientmanagementapi.service.PatientManagementService;

import java.util.List;

/**
 * <p>PatientManagementController is the controller for our application</p>
 *
 * <p>The controller centralize all needed endpoints for the manipulation of Patients.</p>
 *
 *
 */
@RestController
@RequestMapping("patient")
public class PatientManagementController {


    @Autowired
    PatientManagementService patientService;


    /**
     *
     * <p>addAPatientToTheDatabase request a patient and add it to the database.</p>
     * <p>There is verification done before adding the patient.</p>
     * <p>First, it verifies that the body sent is composed of the minimum elements for the MySQL database.</p>
     * <p>Second, it prevent users from adding an ID (which can lead to potential futures errors.</p>
     * <p>Finally, it either send the newly saved user, or send a message stipulating the impossibility of creating a new user.</p>
     *
     * @param newPatient (is the new patient the doctor wants to save in the database)
     * @return ResponseEntity<Patient>
     */
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

    /**
     * <p>updateAPatientInTheDatabase request an Id and a Patient in order to update an existing patient in the database.</p>
     *
     * <p>It first check if the user doesn't put another ID in the body of the request.</p>
     * <p>It then tries to save the user into the database, leading to either two results.</p>
     * <p>Either the user could be save and a new ResponseEntity is sent with, as a body, the newly saved patient.</p>
     * <p>Or, it send a message indicating the impossibility of such update.</p>
     *
     * @param patientId (is the id related to a patient from whom the doctor wants to update the information in the database)
     * @param newAttributesForGivenPatient (is the class centralizing the new attributes that the doctor wants to update the patient with)
     * @return ResponseEntity<Patient>
     */
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

    /**
     *
     * <p>deleteAPatientInTheDatabase delete users. It request an Id as a parameter.</p>
     * <p>It checks if the user exist in the database, and either supress it and send 'true' to the user.</p>
     * <p>Or, the method couldn't delete the user given the Id and informs the user of such case.</p>
     *
     *
     * @param patientId (is the id related to the patient that the doctor wants to delete from the database)
     * @return ResponseEntity<Boolean>
     */
    @GetMapping("/delete")
    public ResponseEntity<Boolean> deleteAPatientInTheDatabase(@RequestParam("id") Integer patientId){

        Boolean result = patientService.deleteAGivenPatient(patientId);

        if(result){

            return new ResponseEntity<Boolean>(result, HttpStatus.OK);

        }else{

            return new ResponseEntity("The user couldn't be deleted", HttpStatus.BAD_REQUEST);

        }

    }

    /**
     * <p>getAllThePatient returns all the patients currently saved in the database, with all their information.</p>
     *
     * @return List<Patient>
     */
    @GetMapping("/")
    public List<Patient> getAllThePatient(){

        return patientService.getAllTheList();
    }

    /**
     * <p>getASpecificPatient return a patient. It requires an Id as path variable.</p>
     *
     * <p>it tries to get the patient. If it can't, the method send a message to the user indicating the non existence of any links between the id and an user.</p>
     *
     * <p>If a patient is found for the given id, the method returns a response entity with, as a body, the wished patient.</p>
     *
     * @param id (is the id related to a patient that the doctor wants to get)
     * @return ResponseEntity<Patient>
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Patient> getASpecificPatient(@PathVariable("id") Integer id){

        try {
            Patient result = patientService.getASpecificPatient(id).get();
            return new ResponseEntity<Patient>(result, HttpStatus.OK);

        }catch(Exception e){

            return new ResponseEntity("Could not get this user form the Database with this id." , HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * <p>getASpecificPatient return a patient. It requires a firstname and a family name as path variable.</p>
     *
     * <p>it tries to get the patient. If it can't, the method send a message to the user indicating the non existence of any user with this combination of names.</p>
     *
     * <p>If a patient is found for the given combination, the method returns a response entity with, as a body, the wished patient.</p>
     *
     * @param firstName (is the first name from which the doctor wants to retrieve a patient in the database)
     * @param familyName (is the last name from which the doctor wants to retrieve a patient in the database)
     * @return ResponseEntity<Patient>
     */
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
