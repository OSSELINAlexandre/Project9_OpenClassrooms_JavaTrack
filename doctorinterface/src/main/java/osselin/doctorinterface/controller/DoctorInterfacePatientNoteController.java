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

/**
 *
 * <p>DoctorInterfacePatientNoteController is the controller that centralizes all the endpoints related to the interaction with patient notes.</p>
 *
 *
 */
@Controller
public class DoctorInterfacePatientNoteController {

    @Autowired
    DoctorInterfaceService doctorService;

    /**
     *
     * <p>seeAllNotes provides the interface to visualize all notes for a given patient.</p>
     *
     * <p>To see the interface, go in the resources/templates/seeallnotes.html</p>
     * @param theId (is the id of the patient to whom the doctor wants to see a note)
     * @param model (is the class used to inject instances into the template)
     * @see 'seeallnotes'
     * @return String
     */
    @GetMapping("/seeNotesForUser/{id}")
    public String seeAllNotes(@PathVariable("id") Integer theId, Model model){

        Patient resultPatient = doctorService.getSpecificPatientFromDb(theId);
        List<PatientNote> result = doctorService.getNotesFromUserNOSQL(theId);

        model.addAttribute("givenPatient", resultPatient);
        model.addAttribute("AllNoteFromUser", result);

        return "seeallnotes";
    }

    /**
     *
     * <p>seeSpecificNote provides the interface to visualize a specific note for a given patient.</p>
     *
     * <p>To see the interface, go in the resources/templates/seeagivennote.html</p>
     *
     * @param id (is the id of the patient to whom the doctor wants to see a note)
     * @param model (is the class used to inject instances into the template)
     * @see 'seeagivennote'
     * @return String
     */
    @GetMapping("/seeSpecificNote/{id}")
    public String seeSpecificNote(@PathVariable("id") String id, Model model){

        PatientNote result = doctorService.getSpecificNoteFromPatient(id);
        Patient thePatient = doctorService.getSpecificPatientFromDb(Integer.parseInt(result.getSqlId()));

        model.addAttribute("theGivenNote", result);
        model.addAttribute("theGivenPatient", thePatient);

        return "seeagivennote";
    }

    /**
     *
     * <p>modifySpecificNote provides the interface to visualize and modify a specific note for a given patient.</p>
     *
     * <p>To see the interface, go in the resources/templates/modifynewnote.html</p>
     *
     * @param id (is the id of the patient to whom the doctor wantswant to modify a note)
     * @param model (is the class used to inject instances into the template)
     * @see 'modifynewnote'
     * @return String
     */
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


    /**
     *
     * <p>modifyASpecificNote is the method call by the form from modify note interface.</p>
     *
     * <p>If the patient note can be modify, it returns to the main page ('home.html').</p>
     * <p>If the patient cannot be registered, it returns an error. (if the doctor can go until this point, the error cannot be based on logical issue, but technical one, such as latency in mongoDb database).</p>
     *
     * @param PnId (is the Id of the note that the doctor wants to modify)
     * @param patientNote (is the new instance of the patient note (the modified note) that the doctor wants to save)
     * @param model (is the class used to inject instances into the template)
     * @return String
     */
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

            return "error";
        }
    }


    /**
     *
     * <p>saveANewPatientNote is the method call by the form from save note interface.</p>
     *
     * <p>If the patient note can be save, it returns to the main page ('home.html').</p>
     * <p>If the patient cannot be registered, it returns an error. (if the doctor can go until this point, the error cannot be based on logical issue, but technical one, such as latency in mongoDb database).</p>
     * @param id (is the id of the patient to whom the doctor wants to add a note)
     * @param patientNote (is the instance of PatientNote that the doctor want to save)
     * @param model (is the class used to inject instances into the template)
     * @return String
     */
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

            return "error";
        }

    }


    /**
     *
     * <p>addingNewNoteInterface provides the interface to visualize and add a new note for a given patient.</p>
     * <p>To see the interface, go in the resources/templates/modifynewnote.html</p>
     *
     * @param id (is the id of the patient to whom the doctor wants to add a note)
     * @param model (is the class used to inject instances into the template)
     * @return String
     */
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

    /**
     * <p>deletingAGivenNote provides the switch to delete and add a new note for a given patient.</p>
     *
     * <p>If the patient note can be save, it returns to the main page ('home.html').</p>
     * <p>If the patient cannot be registered, it returns an error. (if the doctor can go until this point, the error cannot be based on logical issue, but technical one, such as latency in mongoDb database).</p>
     *
     * @param id (is the id of the note the doctor want to delete)
     * @return String
     */
    @GetMapping("/deleteANote/{id}")
    public String deletingAGivenNote(@PathVariable("id") String id){


        Boolean result = doctorService.deleteSpecificNote(id);

        if(result){

            return "redirect:/";

        }else{

            return "error";
        }
    }

}
