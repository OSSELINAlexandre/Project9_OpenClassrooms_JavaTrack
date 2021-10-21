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
import osselin.doctorinterface.service.DoctorInterfaceService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class DoctorInterfacePatientManagementController {

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

        Patient result = doctorService.getSpecificPatientFromDb(theId);


        if(result != null ){
            model.addAttribute("thePatientToModify", result);
            return "modify";

        }else{

            return "redirect:/?notFoundById=true";
        }

    }

    @GetMapping("/deletePatient/{id}")
    public String deleteAGivenUser(Model model,  @PathVariable("id") int theId){

        Patient result = doctorService.getSpecificPatientFromDb(theId);

        if(result != null){

            model.addAttribute("thePatientToDelete", result);
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

}

