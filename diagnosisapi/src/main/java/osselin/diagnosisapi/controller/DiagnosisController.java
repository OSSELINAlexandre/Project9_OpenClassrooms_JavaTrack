package osselin.diagnosisapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import osselin.diagnosisapi.model.PatFamilyDto;
import osselin.diagnosisapi.model.PatIdDto;
import osselin.diagnosisapi.service.DiagnosisService;

@RestController
public class DiagnosisController {

    @Autowired
    DiagnosisService diagnosisService;

    @PostMapping("/assess/id")
    public String assesBasedOnId(@RequestBody PatIdDto theId){

        String result = diagnosisService.assessThePatientBasedOnId(theId.getPatId());

        return result;
    }

    //TODO attention car plusieurs membres de la famille peuvent se faire consulter ! :)
    @PostMapping("/assess/familyName")
    public String assesBasedOnId(@RequestBody PatFamilyDto patFamilyDto){

        String result = diagnosisService.assessThePatientBasedOnFamilyName(patFamilyDto.getFirstName(), patFamilyDto.getFamilyName());

        return result;

    }


}
