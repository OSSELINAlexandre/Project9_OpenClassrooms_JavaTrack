package osselin.diagnosisapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import osselin.diagnosisapi.model.PatFamilyDto;
import osselin.diagnosisapi.model.PatIdDto;
import osselin.diagnosisapi.service.DiagnosisService;

/**
 * <p>DiagnosisController is the controller for our application</p>
 *
 * <p>The controller centralize all needed endpoints for the creation of a diagnostic.</p>
 *
 *
 */
@RestController
public class DiagnosisController {

    @Autowired
    DiagnosisService diagnosisService;

    /**
     * <p>assesBasedOnId requires as PatIdDTO (which is a simple integer) as a body and returns the result of the diagnostic.</p>
     * <p>The ID is the ID available in the SQL database in the PatientManagement API.</p>
     *
     * <p>It returns a fully diagnostic if the patient exists and notes are related to him.</p>
     *
     * <p>It sends an error message if no patient exists with this id or if notes related to him exists.</p>
     *
     * @param theId (is the id of the patient to whom we want to make a diagnostic)
     * @return String
     */
    @PostMapping("/assess/id")
    public String assesBasedOnId(@RequestBody PatIdDto theId){

        String result = diagnosisService.assessThePatientBasedOnId(theId.getPatId());

        return result;
    }

    /**
     * <p>assesBasedOnFamilyName requires as PatFamilyDto (which is a combination of a first name and a last name) as a body and returns the result of the diagnostic.</p>
     * <p>The combination is the combination of names that are available in the SQL database in the PatientManagement API.</p>
     *
     * <p>It returns a fully diagnostic if the patient exists and notes are related to him.</p>
     *
     * <p>It sends an error message if no patient exists with this combination or if no notes are related to him.</p>
     *
     * @param patFamilyDto (is the combination of names for a patient, to whom we want to make a diagnostic)
     * @return String
     */
    @PostMapping("/assess/familyName")
    public String assesBasedOnFamilyName(@RequestBody PatFamilyDto patFamilyDto){

        String result = diagnosisService.assessThePatientBasedOnFamilyName(patFamilyDto.getFirstName(), patFamilyDto.getFamilyName());

        return result;

    }


}
