package osselin.patientmanagementapi;

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
import osselin.patientmanagementapi.model.Patient;
import osselin.patientmanagementapi.repository.PatientManagementRepository;
import osselin.patientmanagementapi.service.PatientManagementService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PatientServiceTests {

	@Autowired
	PatientManagementService patientService;

	@Autowired
	PatientManagementRepository patientRepo;

	Patient testingPatient;

	private MockMvc mockMvc;

	private MvcResult mvcResult;

	@Autowired
	private WebApplicationContext webApplicationContext;


	@BeforeEach
	public void init(){

		Date dating26Yo = new Date(815616000000L);
		testingPatient = new Patient("Claude", "OSSELIN", dating26Yo, 'M', "32 rue du Moulin", "0160693539");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}


	@Test
	void addingAPatientShouldReturnTrue() throws Exception {

		Date dating26Yo = new Date(815616000000L);
		Patient testingPatientThree = new Patient("Jean", "Valjean", dating26Yo, 'M', "Rue du petit picpus", "0160693539");

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/patient/add").content(asJsonString(testingPatientThree))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();


		String theResponse = mvcResult.getResponse().getContentAsString();

		patientRepo.deleteById(getTheIdFromTheReponses(theResponse));

		assertTrue(theResponse.contains("Jean") && theResponse.contains("Valjean") && theResponse.contains("M") && theResponse.contains("Rue du petit picpus") &&  theResponse.contains("0160693539"));

	}

	@Test
	void addingAPatientAlreadyExistingShouldReturnFalse() throws Exception {

		MvcResult toDeleteAnswer = mockMvc.perform(MockMvcRequestBuilders.post("/patient/add").content(asJsonString(testingPatient))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();


		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/patient/add").content(asJsonString(testingPatient))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();


		String theResponse = mvcResult.getResponse().getContentAsString();

		assertTrue(theResponse.contains("You couldn't add the Patient to the Db"));

		patientRepo.deleteById(getTheIdFromTheReponses(toDeleteAnswer.getResponse().getContentAsString()));

	}

	@Test
	void updatingAnExistingPatientShouldReturnTrue() throws Exception {

		MvcResult toDeleteAnswer = mockMvc.perform(MockMvcRequestBuilders.post("/patient/add").content(asJsonString(testingPatient))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		testingPatient.setFirstName("John");
		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/patient/update?id=" + getTheIdFromTheReponses(toDeleteAnswer.getResponse().getContentAsString())).content(asJsonString(testingPatient))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();


		String theResponse = mvcResult.getResponse().getContentAsString();


		assertTrue(theResponse.contains("John") && theResponse.contains("OSSELIN") && theResponse.contains("M"));

		patientRepo.deleteById(getTheIdFromTheReponses(toDeleteAnswer.getResponse().getContentAsString()));


	}

	@Test
	void updatingANoneExistingPatientShouldReturnFalse() throws Exception {

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/patient/update?id=" + Integer.MAX_VALUE).content(asJsonString(testingPatient))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();


		assertTrue(mvcResult.getResponse().getContentAsString().contains("You couldn't update the Patient to the Db"));

	}

	@Test
	void deletingAnExistingPatientShouldReturnTrue() throws Exception {

		MvcResult toDeleteAnswer = mockMvc.perform(MockMvcRequestBuilders.post("/patient/add").content(asJsonString(testingPatient))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/patient/delete?id=" + getTheIdFromTheReponses(toDeleteAnswer.getResponse().getContentAsString())).content(asJsonString(testingPatient))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		assertTrue(mvcResult.getResponse().getContentAsString().contains("true"));


	}

	@Test
	void deletingAnNoneExistingPatientShouldReturnFalse() throws Exception {

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/patient/delete?id=" + Integer.MAX_VALUE).content(asJsonString(testingPatient))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();


		Boolean result = patientService.deleteAGivenPatient(Integer.MAX_VALUE);
		assertTrue(mvcResult.getResponse().getContentAsString().contains("The user couldn't be deleted"));

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Integer getTheIdFromTheReponses(String theResponse){
		System.out.println(theResponse);
		String[] toDelete = theResponse.split(",");
		System.out.println(toDelete.toString());
		String[] resultWanted = toDelete[0].split(":");
		String id = resultWanted[1];
		return Integer.parseInt(id);
	}

}
