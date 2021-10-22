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

/**
 *
 * <p>DoctorInterfacePatientManagementController is the controller that centralizes all the endpoints related to the interaction with patient.</p>
 *
 *
 */
@Controller
public class DoctorInterfacePatientManagementController {

    @Autowired
    DoctorInterfaceService doctorService;



    /**
     * <p>welcomeHome is the default endpoints for the doctor interface.</p>
     *
     * <p>It post all the patient registered in the database in an web interface</p>
     *
     * <p>It also post all errors that could occur during the process. </p>
     *
     * <p>To see the interface, go in the resources/templates/home.html</p>
     *
     * @param model (is the class used to inject instances into the template)
     * @param errorFindingBuddy (is present if a patient with same name already exists in the database)
     * @param notFoundById (is present if the id provided by url isn't related to any user in database)
     * @param notDeleted (is present if the user provided by id in the delete interface to deleted couldn't be deleted)
     * @see 'home.html'
     * @return String
     */
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

    /**
     * <p>addingANewPatient provides the interface to add a new patient. </p>
     *
     * <p>To see the interface, go in the resources/templates/adding.html</p>
     * @param model (is the class used to inject instances into the template)
     * @see 'adding'
     * @return String
     */
    @GetMapping("/addPatient")
    public String addingANewPatient(Model model){

        model.addAttribute("patient", new Patient());
        return "adding";
    }

    /**
     * <p>modifyAPatient provides the interface to modify an existing patient. </p>
     *
     * <p>To see the interface, go in the resources/templates/adding.html</p>
     *
     * @param model (is the class used to inject instances into the template)
     * @param theId (is the patient id)
     * @see 'modify'
     * @return String
     */
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

    /**
     * <p>deleteAGivenUser provides the interface to delete an existing patient. </p>
     *
     * <p>To see the interface, go in the resources/templates/delete.html</p>
     *
     * @param model (is the class used to inject instances into the template)
     * @param theId (is the patient id)
     * @see 'delete'
     * @return String
     */
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

    /**
     * <p>registeringANewPatient is the method call by the form from add interface.</p>
     *
     * <p>If the patient can be registered, it returns to the main page.</p>
     * <p>If the patient cannot be registered, it returns the page for registration (the initial page).</p>
     * @param thePatient (is the instance of type Patient that we want to save)
     * @see 'adding'
     * @return String
     */
    @PostMapping("/registerNewPatient")
    public String registeringANewPatient(@Valid Patient thePatient, BindingResult result){


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


    /**
     * <p>updateANewPatient is the method call by the form from modify interface.</p>
     *
     * <p>If the patient can be registered, it returns to the main page.</p>
     * <p>If the patient cannot be registered, it returns the page for modification (the initial page).</p>
     * @param id (is the id of the patient we want to update)
     * @param theNewPatientRecords (is the new instance of patient that we want to save)
     * @param result (is the class that verifies if any errors is present in the logic of the template)
     * @return
     */
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

    /**
     * <p>deletingAGivenPatient is the method call by the form from deleting interface.</p>
     *
     * <p>It returns to the main page if the patient could be deleted.</p>
     *
     * @param id (is the id of the patient we want to delete)
     * @see 'home'
     * @return String
     */
    @PostMapping("/deletingAGivenPatient/{id}")
    public String deletingAGivenPatient(@PathVariable("id") Integer id){

        Boolean theResult = doctorService.deleteSpecificPatient(id);
        return "redirect:/";
    }

}

