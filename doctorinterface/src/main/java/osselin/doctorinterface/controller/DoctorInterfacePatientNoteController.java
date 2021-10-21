package osselin.doctorinterface.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import osselin.doctorinterface.model.Patient;
import osselin.doctorinterface.model.PatientNote;
import osselin.doctorinterface.service.DoctorInterfaceService;

import java.util.List;

@Controller
public class DoctorInterfacePatientNoteController {

    @Autowired
    DoctorInterfaceService doctorService;

    @GetMapping("/seeNotesForUser/{id}")
    public String seeAllNotes(@PathVariable("id") Integer theId, Model model){

        Patient resultPatient = doctorService.getSpecificPatientFromDb(theId);
        List<PatientNote> result = doctorService.getNotesFromUserNOSQL(theId);

        model.addAttribute("givenPatient", resultPatient);
        model.addAttribute("AllNoteFromUser", result);

        return "seeallnotes";
    }

    @GetMapping("/seeSpecificNote/{id}")
    public String seeSpecificNote(@PathVariable("id") String id, Model model){

        PatientNote result = doctorService.getSpecificNoteFromPatient(id);
        Patient thePatient = doctorService.getSpecificPatientFromDb(Integer.parseInt(result.getSqlId()));

        model.addAttribute("theGivenNote", result);
        model.addAttribute("theGivenPatient", thePatient);

        return "seeagivennote";
    }

    @GetMapping("/modifySpecificNote/{id}")
    public String modifySpecificNote(@PathVariable("id") String id,  Model model){

        PatientNote result = doctorService.getSpecificNoteFromPatient(id);
        Patient thePatient = doctorService.getSpecificPatientFromDb(Integer.parseInt(result.getSqlId()));

        model.addAttribute("theNoteToModify", result);
        model.addAttribute("patientId", thePatient.getId());
        model.addAttribute("patientFirstName", thePatient.getFirstName());
        model.addAttribute("patientLastName", thePatient.getLastName());
        model.addAttribute("patientDateOfBirth", thePatient.getDateOfBirth());

        return "modifynewnote";
    }

    //@TODO gestion des exceptions mec !
    @PostMapping("/modifyAGivenNote/{PnId}")
    public String modifyASpecificNote(@PathVariable("PnId") String PnId, PatientNote patientNote, Model model){


        PatientNote oldResult = doctorService.getSpecificNoteFromPatient(PnId);


        patientNote.setId(oldResult.getId());
        patientNote.setFirstName(oldResult.getFirstName());
        patientNote.setLastName(oldResult.getLastName());
        patientNote.setSqlId(oldResult.getSqlId());

        Boolean result = doctorService.modifyingAnExistingNoteIntoTheDb(patientNote);

        if(result){

            return "redirect:/";

        }else{

            return "MDR";
        }
    }


    //TODO create redirection Error if error exists.
    @PostMapping("/saveSpecificNote/{idPatient}")
    public String saveANewPatientNote(@PathVariable("idPatient") String id, PatientNote patientNote, Model model){


        Patient patientToAddNote = doctorService.getSpecificPatientFromDb(Integer.parseInt(id));

        patientNote.setFirstName(patientToAddNote.getFirstName());
        patientNote.setLastName(patientToAddNote.getLastName());
        patientNote.setSqlId(String.valueOf(patientToAddNote.getId()));

        Boolean result = doctorService.addingNewNoteToPatientInDb(patientNote);

        if(result){

            return "redirect:/";

        }else{

            return "MDR";
        }

    }


    @GetMapping("/addNewNote/{id}")
    public String addingNewNoteInterface(@PathVariable("id") Integer id, Model model){

        Patient thePatient = doctorService.getSpecificPatientFromDb(id);

        model.addAttribute("addingNewNote", new PatientNote());
        model.addAttribute("patientId", thePatient.getId());
        model.addAttribute("patientFirstName", thePatient.getFirstName());
        model.addAttribute("patientLastName", thePatient.getLastName());
        model.addAttribute("patientDateOfBirth", thePatient.getDateOfBirth());

        return "addingnewnote";

    }

    @GetMapping("/deleteANote/{id}")
    public String deletingAGivenNote(@PathVariable("id") String id){


        Boolean result = doctorService.deleteSpecificNote(id);

        if(result){

            return "redirect:/";

        }else{

            return "MDR";
        }
    }

}
