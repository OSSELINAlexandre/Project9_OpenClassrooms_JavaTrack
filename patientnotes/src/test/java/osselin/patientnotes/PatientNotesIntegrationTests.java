package osselin.patientnotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import osselin.patientnotes.model.PatientNote;
import osselin.patientnotes.repository.PatientNotesRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
public class PatientNotesIntegrationTests {

    @Autowired
    PatientNotesRepository patientRepo;

    private MockMvc mockMvc;

    private MvcResult mvcResult;

    private PatientNote patientNoteTestA;
    private PatientNote patientNoteTestB;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void init(){

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        patientNoteTestA = new PatientNote(String.valueOf(Integer.MAX_VALUE), "Leonard", "DaVinci", "Terribly a genius");
        patientNoteTestB = new PatientNote(String.valueOf(Integer.MAX_VALUE), "Leonard", "DaVinci", "terrible genius");
        patientRepo.save(patientNoteTestA);
        patientRepo.save(patientNoteTestB);
    }

    @Test
    public void Test_getNote_FromExistingPatientNote_ShouldReturnTheObservation() throws Exception {

        mvcResult = mockMvc.perform(get("/patHistory/getName?firstName=" + patientNoteTestA.getFirstName() + "&lastName=" + patientNoteTestB.getLastName())).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Terribly a genius") && mvcResult.getResponse().getContentAsString().contains("terrible genius"));
        String[] responses = mvcResult.getResponse().getContentAsString().split("},");

        patientRepo.deleteById(getTheIdFromTheReponses(responses[0]));
        patientRepo.deleteById(getTheIdFromTheReponses(responses[1]));
    }

    @Test
    public void Test_getAllNotesBasedOnSqlID_FromExistingPatientNote_ShouldReturnTheObservation() throws Exception {



        mvcResult = mockMvc.perform(get("/patHistory/getNotes?id=" + Integer.MAX_VALUE )).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Terribly a genius") && mvcResult.getResponse().getContentAsString().contains("terrible genius"));
        String[] responses = mvcResult.getResponse().getContentAsString().split("},");

        patientRepo.deleteById(getTheIdFromTheReponses(responses[0]));
        patientRepo.deleteById(getTheIdFromTheReponses(responses[1]));
    }

    @Test
    public void Test_getNote_FromNotExistingPatientNote_ShouldNotReturnTheObservation() throws Exception {


        MvcResult toBeDeleted = mockMvc.perform(get("/patHistory/getName?firstName=" + patientNoteTestA.getFirstName() + "&lastName=" + patientNoteTestB.getLastName())).andReturn();
        String[] responses = toBeDeleted.getResponse().getContentAsString().split("},");


        mvcResult = mockMvc.perform(get("/patHistory/getName?firstName=NotExistingPatientNote" )).andReturn();
        System.out.println( "-----" + mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().equals(""));

        patientRepo.deleteById(getTheIdFromTheReponses(responses[0]));
        patientRepo.deleteById(getTheIdFromTheReponses(responses[1]));

    }

    @Test
    public void Test_GetAGivenNote() throws Exception {

        MvcResult deletedResult = mockMvc.perform(get("/patHistory/getName?firstName=" + patientNoteTestA.getFirstName() + "&lastName=" + patientNoteTestB.getLastName())).andReturn();
        List<PatientNote> pa = patientRepo.findByLastNameAndFirstName("DaVinci" , "Leonard");
        PatientNote noteA = pa.get(0);

        mvcResult = mockMvc.perform(get("/patHistory/get?id=" + noteA.getId())).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Terribly a genius") || mvcResult.getResponse().getContentAsString().contains("terrible genius"));

        String[] responses = deletedResult.getResponse().getContentAsString().split("},");
        patientRepo.deleteById(getTheIdFromTheReponses(responses[0]));
        patientRepo.deleteById(getTheIdFromTheReponses(responses[1]));
    }

    @Test
    public void Test_GetAGivenNote_ShouldReturnEmptyWhenDoesNotExist() throws Exception {

        MvcResult deletedResult = mockMvc.perform(get("/patHistory/getName?firstName=" + patientNoteTestA.getFirstName() + "&lastName=" + patientNoteTestB.getLastName())).andReturn();


        mvcResult = mockMvc.perform(get("/patHistory/get?id=" + Integer.MAX_VALUE + 77)).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().equals("Could not get the notes from this user based on this id."));

        String[] responses = deletedResult.getResponse().getContentAsString().split("},");
        patientRepo.deleteById(getTheIdFromTheReponses(responses[0]));
        patientRepo.deleteById(getTheIdFromTheReponses(responses[1]));
    }

    @Test
    public void Test_SaveANewNote() throws Exception {

        PatientNote testPatientNoteC = new PatientNote(String.valueOf(Integer.MAX_VALUE), "Leonard", "DaVinci", "A genius that was terrible");
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/patHistory/add").content(asJsonString(testPatientNoteC))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("true"));

        MvcResult resultsToBeDeleted = mockMvc.perform(get("/patHistory/getName?firstName=" + patientNoteTestA.getFirstName() + "&lastName=" + patientNoteTestB.getLastName())).andReturn();
        String[] responses = resultsToBeDeleted.getResponse().getContentAsString().split("},");
        patientRepo.deleteById(getTheIdFromTheReponses(responses[0]));
        patientRepo.deleteById(getTheIdFromTheReponses(responses[1]));
        patientRepo.deleteById(getTheIdFromTheReponses(responses[2]));

    }

    @Test
    public void Test_DeleteAGivenNote() throws Exception {

        List<PatientNote> pa = patientRepo.findByLastNameAndFirstName("DaVinci" , "Leonard");
        PatientNote noteA = pa.get(0);

        mvcResult = mockMvc.perform(get("/patHistory/delete?id=" + noteA.getId())).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("true"));

        MvcResult resultsToBeDeleted = mockMvc.perform(get("/patHistory/getName?firstName=" + patientNoteTestA.getFirstName() + "&lastName=" + patientNoteTestB.getLastName())).andReturn();
        String[] responses = resultsToBeDeleted.getResponse().getContentAsString().split("},");
        patientRepo.deleteById(getTheIdFromTheReponses(responses[0]));


    }

    @Test
    public void Test_DeleteAGivenNote_ShouldReturnFalseIfDoesNotExist() throws Exception {


        mvcResult = mockMvc.perform(get("/patHistory/delete?id=" + (-5))).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Could not delete a note with this id."));

        MvcResult resultsToBeDeleted = mockMvc.perform(get("/patHistory/getName?firstName=" + patientNoteTestA.getFirstName() + "&lastName=" + patientNoteTestB.getLastName())).andReturn();
        String[] responses = resultsToBeDeleted.getResponse().getContentAsString().split("},");
        patientRepo.deleteById(getTheIdFromTheReponses(responses[0]));
        patientRepo.deleteById(getTheIdFromTheReponses(responses[1]));


    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getTheIdFromTheReponses(String theResponse){
        String[] toDelete = theResponse.split(",");
        String[] resultWanted = toDelete[0].split(":");
        String id = resultWanted[1];
        String[] realId = id.split("\"");
        return realId[1];
    }
}
