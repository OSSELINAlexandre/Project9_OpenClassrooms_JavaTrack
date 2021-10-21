package osselin.patientnotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osselin.patientnotes.model.PatientNote;
import osselin.patientnotes.service.PatientNoteService;

import java.util.List;

@RestController
@RequestMapping("patHistory")
public class PatientNoteController {

    @Autowired
    PatientNoteService patientNoteService;


    @GetMapping("/getNotes")
    public ResponseEntity<List<PatientNote>> getAllNotesBasedOnSqlID(@RequestParam("id") Integer id){


        List<PatientNote> result = patientNoteService.getAllNotesFromPatientBasedOnSQLId(id);

        if(result.size() > 0 ){

            return new ResponseEntity<List<PatientNote>>(result, HttpStatus.OK);

        }else{

            return new ResponseEntity("Could not get the notes from this user based on this SqlID.", HttpStatus.BAD_REQUEST);
        }

    }



    @GetMapping("/getName")
    public ResponseEntity<List<PatientNote>> getAllNotes(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){


        List<PatientNote> result = patientNoteService.getAllNotesFromPatient(firstName, lastName);

        if(result.size() > 0 ){

            return new ResponseEntity<List<PatientNote>>(result, HttpStatus.OK);

        }else{

            return new ResponseEntity("Could not get the notes from this user based on this first name and last name.", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get")
    public ResponseEntity<PatientNote> getASpecificNote(@RequestParam("id") String id){


        PatientNote result = patientNoteService.getSpecificNoteFromPatient(id);

        if(result != null ){

            return new ResponseEntity<PatientNote>(result, HttpStatus.OK);

        }else{

            return new ResponseEntity("Could not get the notes from this user based on this id.", HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/add")
    public ResponseEntity<Boolean> saveANewNoteInTheDb(@RequestBody PatientNote pn){


        if(pn.getObservation() == null || pn.getLastName() == null || pn.getFirstName() == null ){

            return new ResponseEntity("Could not save this note in the DB, please register at least an observation the type sent and the full name for the Patient", HttpStatus.BAD_REQUEST);

        }else{

        Boolean result = patientNoteService.saveANewNoteToTheDataBase(pn);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
        }

    }

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
