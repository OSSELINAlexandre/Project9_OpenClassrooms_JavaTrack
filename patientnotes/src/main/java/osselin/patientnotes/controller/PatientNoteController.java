package osselin.patientnotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import osselin.patientnotes.model.PatientNote;
import osselin.patientnotes.service.PatientNoteService;

import java.util.List;

@RestController
public class PatientNoteController {

    @Autowired
    PatientNoteService patientNoteService;

    @GetMapping("/getNote")
    public List<PatientNote> getAllNotes(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName){


        return patientNoteService.getAllNotesFromPatient(firstName, lastName);
    }

    @GetMapping("/getAGivenNote")
    public PatientNote getASpecificNote(@RequestParam("id") String id){

        return patientNoteService.getSpecificNoteFromPatient(id);
    }
    @PostMapping("/saveANewNote")
    public Boolean saveANewNoteInTheDb(@RequestBody PatientNote pn){


        return patientNoteService.saveANewNoteToTheDataBase(pn);
    }

    @GetMapping("/deleteANote")
    public Boolean DeleteAGivenNote(@RequestParam("id") String id){

        return patientNoteService.deleteAGivenNote(id);
    }


}
