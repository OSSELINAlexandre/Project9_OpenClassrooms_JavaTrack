package osselin.patientnotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osselin.patientnotes.model.PatientNote;
import osselin.patientnotes.service.PatientNoteService;

import java.util.List;


/**
 * <p>PatientNoteController is the controller for our application</p>
 *
 * <p>The controller centralize all needed endpoints for the manipulation of Patients' notes from the doctor.</p>
 *
 *
 */
@RestController
@RequestMapping("patHistory")
public class PatientNoteController {

    @Autowired
    PatientNoteService patientNoteService;

    /**
     * <p>getAllNotesBasedOnSqlID request an id as a parameter and returns all the notes for the related patient.</p>
     *
     * <p>It first checks if notes exists for the patient.</p>
     *
     * <p>It returns the list of notes if it does exists with a 200 HTTP code.</p>
     *
     * <p>It returns a message indicating the error if no patient exist for the given Id, or if no notes have been registered on the patient, with a 400 HTTP code.</p>
     *
     * @param id (is the id of the patient from whom the doctor wants to retrieve all historical notes)
     * @return ResponseEntity<List<PatientNote>>
     */
    @GetMapping("/getNotes")
    public ResponseEntity<List<PatientNote>> getAllNotesBasedOnSqlID(@RequestParam("id") Integer id){


        List<PatientNote> result = patientNoteService.getAllNotesFromPatientBasedOnSQLId(id);

        if(result.size() > 0 ){

            return new ResponseEntity<List<PatientNote>>(result, HttpStatus.OK);

        }else{

            return new ResponseEntity("Could not get the notes from this user based on this SqlID.", HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * <p>getAllNotes request a first name and a last name as a parameter and returns a list of all notes for the related patient.</p>
     *
     * <p>It first check if notes exist for the combination of names.</p>
     *
     * <p>If it does exist, and the list is at least of one note, it returns the list of all notes and an 200 HTTP code.</p>
     *
     * <p>If it doesn't exist, or if the list of notes is equal to zero, it returns an error message with an 400 HTTP code.</p>
     *
     *
     * @param firstName (is the first name of the patient from whom the doctor wants to retrieve information in the database)
     * @param lastName (is the last name of the patient from whom the doctor wants to retrieve information in the database)
     * @return ResponseEntity<List<PatientNote>>
     */
    @GetMapping("/getName")
    public ResponseEntity<List<PatientNote>> getAllNotes(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){


        List<PatientNote> result = patientNoteService.getAllNotesFromPatient(firstName, lastName);

        if(result.size() > 0 ){

            return new ResponseEntity<List<PatientNote>>(result, HttpStatus.OK);

        }else{

            return new ResponseEntity("Could not get the notes from this user based on this first name and last name.", HttpStatus.BAD_REQUEST);
        }

    }

    /**
     *
     * <p>getASpecificNote request an id as a parameter and return the related patient note.</p>
     *
     * <p>It first check if, in database, it exists a note for the given id.</p>
     *
     * <p>It it does exist, the method returns the notes with a 200 HTTP code.</p>
     *
     * <p>If it doesn't exist, the method returns a message indicating the impossibility of finding the note for the given id, and returns a 400 HTTP code.</p>
     *
     *
     * @param id (is the id of the note the doctor wants to retrieve)
     * @return ResponseEntity<PatientNote>
     */
    @GetMapping("/get")
    public ResponseEntity<PatientNote> getASpecificNote(@RequestParam("id") String id){


        PatientNote result = patientNoteService.getSpecificNoteFromPatient(id);

        if(result != null ){

            return new ResponseEntity<PatientNote>(result, HttpStatus.OK);

        }else{

            return new ResponseEntity("Could not get the notes from this user based on this id.", HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * <p>saveANewNoteInTheDb request a note as a body of the post method and save the new patient note in the database.</p>
     *
     * <p>It first checks if the note is composed of the minimal attributes required by the client.</p>
     *
     * <p>If it doesn't, the method returns an error message indicating to the user of saving the note.</p>
     *
     * <p>If it does, the method returns</p>
     *
     *
     * @param pn (is the note the doctor wants to add to the database)
     * @return ResponseEntity<Boolean>
     */
    @PostMapping("/add")
    public ResponseEntity<Boolean> saveANewNoteInTheDb(@RequestBody PatientNote pn){


        if(pn.getObservation() == null || pn.getLastName() == null || pn.getFirstName() == null ){

            return new ResponseEntity("Could not save this note in the DB, please register at least an observation the type sent and the full name for the Patient", HttpStatus.BAD_REQUEST);

        }else{

        Boolean result = patientNoteService.saveANewNoteToTheDataBase(pn);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
        }

    }

    /**
     *
     * <p>DeleteAGivenNote request an Id as a parameter and delete the related patient note.</p>
     *
     * <p>it first check if the id is related to a patient note in the database.</p>
     *
     * <p>If it is realted to a patient note in the database, it returns the related patient note with a 200 HTTP code.</p>
     *
     * <p>If it isn't related to a patient note in the database, the method returns a message to the user with a 400 HTTP code.</p>
     *
     * @param id (is the id of the note the doctor wants to delete)
     * @return ResponseEntity<Boolean>
     */
    @GetMapping("/delete")
    public ResponseEntity<Boolean> DeleteAGivenNote(@RequestParam("id") String id){

        Boolean result = patientNoteService.deleteAGivenNote(id);

        if(result){

            return new ResponseEntity<Boolean>(result, HttpStatus.OK);

        }else{

            return new ResponseEntity("Could not delete a note with this id.",HttpStatus.BAD_REQUEST);
        }

    }


}
