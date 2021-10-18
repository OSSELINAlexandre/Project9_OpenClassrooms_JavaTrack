package osselin.doctorinterface.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import osselin.doctorinterface.model.Patient;
import osselin.doctorinterface.model.PatientNote;
import osselin.doctorinterface.service.DoctorInterfaceService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class DoctorInterfaceController {

    @Autowired
    DoctorInterfaceService doctorService;

    @GetMapping("/")
    public String welcomeHome(Model model, @RequestParam("alreadyAddedPatient") Optional<Boolean> errorFindingBuddy,
    @RequestParam("notFoundById") Optional<Boolean> notFoundById, @RequestParam("notDeleted") Optional<Boolean> notDeleted){

        model.addAttribute("patientList", doctorService.getAllTheListOfPatient());

        if(errorFindingBuddy.isPresent()){
            model.addAttribute("addedPatient", true);
        }

        if(notFoundById.isPresent()){
            model.addAttribute("notFoundById", true);
        }

        if(notDeleted.isPresent()){
            model.addAttribute("notDeleted", true);
        }


        return "home";
    }

    @GetMapping("/addPatient")
    public String addingANewPatient(Model model){

        model.addAttribute("patient", new Patient());
        return "adding";
    }

    @GetMapping("/modifyPatient/{id}")
    public String modifyAPatient(Model model, @PathVariable("id") int theId){

        Optional<Patient> result = doctorService.getSpecificPatientFromDb(theId);


        if(result.isPresent()){
            model.addAttribute("thePatientToModify", result.get());
            return "modify";

        }else{

            return "redirect:/?notFoundById=true";
        }

    }

    @GetMapping("/deletePatient/{id}")
    public String deleteAGivenUser(Model model,  @PathVariable("id") int theId){

        Optional<Patient> result = doctorService.getSpecificPatientFromDb(theId);

        if(result.isPresent()){

            model.addAttribute("thePatientToDelete", result.get());
            return "delete";

        }else{

            return "redirect:/?notFoundById=true";
        }
    }

    @PostMapping("/registerNewPatient")
    public String registeringANewPatient(@Valid Patient thePatient, BindingResult result){


        System.out.println(thePatient.getDateOfBirth().toString());
        if(result.hasErrors()){

            return"adding";

        }else{

            Boolean theResult = doctorService.addingNewPatientToTheDb(thePatient);
            if(theResult) {
                return "redirect:/";
            }else{
                return "redirect:/?alreadyAddedPatient=true";
            }
        }

    }


    @PostMapping("/updateAGivenPatient/{id}")
    public String updateANewPatient(@PathVariable("id") Integer id,Patient theNewPatientRecords, BindingResult result){

        if(result.hasErrors()){

            return "modify";

        }else{

            Boolean theResult = doctorService.updatingAPatientToTheDatabase(id, theNewPatientRecords);

            if(theResult) {

                return "redirect:/";

            }else{

                return "redirect:/?didNotChangeAnything=true";
            }

        }


    }

    @PostMapping("/deletingAGivenPatient/{id}")
    public String deletingAGivenPatient(@PathVariable("id") Integer id){

        Boolean theResult = doctorService.deleteSpecificPatient(id);
        return "redirect:/";
    }

    @GetMapping("/seeNotesForUser/{id}/{firstName}/{lastName}")
    public String seeAllNotes(@PathVariable("id") Integer theId, @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model){

        Patient resultPatient = doctorService.getSpecificPatientFromDb(theId).get();
        List<PatientNote> result = doctorService.getNotesFromUserNOSQL(firstName, lastName);

        model.addAttribute("givenPatient", resultPatient);
        model.addAttribute("AllNoteFromUser", result);

        return "seeallnotes";
    }

    @GetMapping("/seeSpecificNote/{id}/{idPatient}")
    public String seeSpecificNote(@PathVariable("id") String id, @PathVariable("idPatient") Integer idPatient, Model model){

        PatientNote result = doctorService.getSpecificNoteFromPatient(id);
        Patient thePatient = doctorService.getSpecificPatientFromDb(idPatient).get();

        model.addAttribute("theGivenNote", result);
        model.addAttribute("theGivenPatient", thePatient);

        return "seeagivennote";
    }

    @GetMapping("/modifySpecificNote/{id}/{idPatient}")
    public String modifySpecificNote(@PathVariable("id") String id, @PathVariable("idPatient") String idPatient, Model model){

        Patient thePatient = doctorService.getSpecificPatientFromDb(Integer.parseInt(idPatient)).get();
        PatientNote result = doctorService.getSpecificNoteFromPatient(id);

        model.addAttribute("theNoteToModify", result);
        model.addAttribute("patientId", thePatient.getId());
        model.addAttribute("patientFirstName", thePatient.getFirstName());
        model.addAttribute("patientLastName", thePatient.getLastName());
        model.addAttribute("patientDateOfBirth", thePatient.getDateOfBirth());

        return "modifynewnote";
    }

    @PostMapping("/modifyAGivenNote/{PnId}/{id}/{firstName}/{lastName}")
    public String modifyASpecificNote(@PathVariable("PnId") String PnId, @PathVariable("id") String id, @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, PatientNote patientNote, Model model){

        patientNote.setId(PnId);
        patientNote.setFirstName(firstName);
        patientNote.setLastName(lastName);
        patientNote.setSqlId(id);

        Boolean result = doctorService.modifyingAnExistingNoteIntoTheDb(patientNote);

        if(result){

            return "redirect:/";

        }else{

            return "MDR";
        }
    }


    @PostMapping("/saveSpecificNote/{idPatient}/{firstNamePatient}/{lastNamePatient}")
    public String saveANewPatientNote(@PathVariable("idPatient") String id, @PathVariable("firstNamePatient") String firstName, @PathVariable("lastNamePatient") String lastName, PatientNote patientNote, Model model){

        patientNote.setFirstName(firstName);
        patientNote.setLastName(lastName);
        patientNote.setSqlId(id);

        Boolean result = doctorService.addingNewNoteToPatientInDb(patientNote);

        if(result){

            return "redirect:/";

        }else{

            return "MDR";
        }

    }


    @GetMapping("/addNewNote/{id}")
    public String addingNewNoteInterface(@PathVariable("id") Integer id, Model model){

        Patient thePatient = doctorService.getSpecificPatientFromDb(id).get();
        System.out.println(thePatient.getFirstName() + " heee yyyy close to sickness but not in it");

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

