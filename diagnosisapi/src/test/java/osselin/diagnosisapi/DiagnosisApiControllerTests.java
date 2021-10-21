package osselin.diagnosisapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import osselin.diagnosisapi.model.PatFamilyDto;
import osselin.diagnosisapi.model.PatIdDto;
import osselin.diagnosisapi.model.Patient;
import osselin.diagnosisapi.model.PatientNote;
import osselin.diagnosisapi.proxy.PatientNoteProxy;
import osselin.diagnosisapi.proxy.PatientProxy;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class DiagnosisApiControllerTests {

    /*
    * This tests are integrations tests, they need both databases to be lauched in order to function properly
    *
    * */

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MvcResult mvcResult;


    private MockMvc mockMvc;

    @Autowired
    PatientNoteProxy patientNoteProxy;

    @Autowired
    PatientProxy patientProxy;

    private PatientNote patientNoteTestA;

    private Patient testingPatient;

    @Test
    public void Test_assesBasedOnId() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Date dating26Yo = new Date(815616000000L);

        testingPatient = new Patient("Alexandre", "DaVinci", dating26Yo, 'M', "32 rue du Moulin", "0160693539");
        patientProxy.addThePatient(testingPatient);
        Patient toBeDeleted = patientProxy.getASpecificPatientBasedOnFamillyAndFirstName("Alexandre", "DaVinci").getBody();

        patientNoteTestA = new PatientNote(String.valueOf(toBeDeleted.getId()), "Alexandre", "DaVinci", "Microalbumine, Taille, Cholestérol, Anticorps, Réaction, Rechute, Vertige, Fumeur, Anormal");
        patientNoteProxy.addOrSaveNoteToTheDb(patientNoteTestA);


        PatIdDto idFromUser = new PatIdDto();
        idFromUser.setPatId(toBeDeleted.getId());

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/assess/id").content(asJsonString(idFromUser))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Early Onset"));

    }

    @Test
    public void Test_assesBasedOnFamilyName() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Date dating26Yo = new Date(815616000000L);
        patientNoteTestA = new PatientNote(String.valueOf(Integer.MAX_VALUE), "Alexandre", "DaVinci", "Microalbumine, Taille, Cholestérol, Anticorps, Réaction, Rechute, Vertige, Fumeur, Anormal");
        testingPatient = new Patient("Alexandre", "DaVinci", dating26Yo, 'M', "32 rue du Moulin", "0160693539");
        patientProxy.addThePatient(testingPatient);
        patientNoteProxy.addOrSaveNoteToTheDb(patientNoteTestA);



        PatFamilyDto theId = new PatFamilyDto();
        theId.setFamilyName("DaVinci");
        theId.setFirstName("Alexandre");


        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/assess/familyName").content(asJsonString(theId))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Early Onset"));

    }

    @AfterEach
    public void delete() throws Exception {

        Patient toBeDeleted = patientProxy.getASpecificPatientBasedOnFamillyAndFirstName("Alexandre", "DaVinci").getBody();
        patientProxy.deleteAPatient(toBeDeleted.getId());
        List<PatientNote> results = patientNoteProxy.getAllNotesForSpecificUser("Alexandre","DaVinci").getBody();
        patientNoteProxy.deleteAGivenNote(results.get(0).getId());

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
