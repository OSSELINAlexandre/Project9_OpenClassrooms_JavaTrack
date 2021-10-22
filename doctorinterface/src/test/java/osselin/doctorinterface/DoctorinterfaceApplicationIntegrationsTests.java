package osselin.doctorinterface;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import osselin.doctorinterface.model.Patient;
import osselin.doctorinterface.model.PatientNote;
import osselin.doctorinterface.proxy.PatientManagementProxy;
import osselin.doctorinterface.proxy.PatientNoteProxy;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 *
 *
 * <p>The Doctor interface is calling the different components in order to render a visual interface for the doctors</p>
 * <p>Therefore, no business logic in inside this components</p>
 * <p>For instance, the sole purpose of the service is to call the different proxies.</p>
 * <p>This is why we suppose that no integration tests are needed ( what is the purpose testing a class which sole goal is to call a proxy ?) </p>
 * <p>The units tests are done in the API themselves, where a logic is involved and where errors can be found. </p>
 * <p>However, we think that because DoctorInterface coordinate different components, it is useful to do a global integration tests of all components.</p>
 * <p>This class does a global integration test for all the different components.</p>
 *
 *
 * <p>Therefore, if you need to launch this class, be sure to have previously launched all the other services. </p>
 *
 * <p>Also, because we are not testing here an API (which send back JSON) but an interface, we cannot test the Post Method (because the data emanate from a form and not from a RequestBody)</p>
 *	<p>Testing manually the post methods ( by trying the forms in the interface) is more efficient. </p>
 * <p>All the post methods are directly tested in the API themselves. </p>
 */
@SpringBootTest
class DoctorinterfaceApplicationIntegrationsTests {

	private MockMvc mockMvc;

	private MvcResult mvcResult;

	PatientNote testingPatientNote;
	Patient testingPatient;

	private int patientId;

	private String patientNoteId;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	PatientManagementProxy patientProxy;

	@Autowired
	PatientNoteProxy patientNotesProxy;

	@BeforeEach
	public void init(){
		Date dating26Yo = new Date(815616000000L);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		testingPatient = new Patient();
		testingPatient.setFirstName("Maximus");
		testingPatient.setLastName("Gladiator");
		testingPatient.setDateOfBirth(dating26Yo);
		testingPatient.setGender('M');
		testingPatient.setAddress("Rome");
		testingPatient.setPhoneNumber("000");
		patientProxy.addThePatient(testingPatient).getBody();
		Patient registeredPatient = patientProxy.getASpecificPatientBasedOnIdentity("Maximus" , "Gladiator").getBody();
		patientId = registeredPatient.getId();



		testingPatientNote = new PatientNote();
		testingPatientNote.setSqlId(String.valueOf(registeredPatient.getId()));
		testingPatientNote.setFirstName("Maximus");
		testingPatientNote.setLastName("Gladiator");
		testingPatientNote.setObservation("Certainly a righteous man !");
		patientNotesProxy.addOrSaveNoteToTheDb(testingPatientNote);
		List<PatientNote> toBeDeleted = patientNotesProxy.getAllNotesForSpecificUserSQLID(patientId).getBody();
		patientNoteId = toBeDeleted.get(0).getId();
	}

	/*
	* 		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/patient/add").content(asJsonString(testingPatientThree))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
	*
	* */

	@Test
	void Test_WelcomeHome_ShouldReturnAListOfALeastOne() throws Exception {


		mvcResult = mockMvc.perform(get("/")).andReturn();

		String theResponse = mvcResult.getResponse().getContentAsString();
		assertTrue(theResponse.contains("Maximus") && theResponse.contains("Gladiator"));
	}

	@Test
	public void Test_addPatient_ShouldContainsAllInformationForGivenPatient() throws Exception {

		mvcResult = mockMvc.perform(get("/addPatient")).andReturn();
		String theResponse = mvcResult.getResponse().getContentAsString();
		assertTrue(theResponse.contains("First Name") && theResponse.contains("Last Name"));

	}

	@Test
	public void Test_ModifyPatient_ShouldContainsAllInformationForGivenPatient() throws Exception {

		mvcResult = mockMvc.perform(get("/modifyPatient/" + patientId)).andReturn();
		String theResponse = mvcResult.getResponse().getContentAsString();
		assertTrue(theResponse.contains("Maximus") && theResponse.contains("Gladiator"));

	}

	@Test
	public void Test_deletePatient_ShouldContainsAllInformationForGivenPatient() throws Exception {

		mvcResult = mockMvc.perform(get("/deletePatient/" + patientId)).andReturn();
		String theResponse = mvcResult.getResponse().getContentAsString();
		assertTrue(theResponse.contains("Maximus") && theResponse.contains("Gladiator"));

	}

	@Test
	public void Test_seeAllNotes_ShouldContainTheIdOfTheNoteRegisteredForAPatient() throws Exception {

		mvcResult = mockMvc.perform(get("/seeNotesForUser/" + patientId)).andReturn();
		String theResponse = mvcResult.getResponse().getContentAsString();
		assertTrue(theResponse.contains(patientNoteId));
	}

	@Test
	public void Test_seeSpecificNote_ShouldContainTheObservationOfTheNote() throws Exception {

		mvcResult = mockMvc.perform(get("/seeSpecificNote/" + patientNoteId)).andReturn();
		String theResponse = mvcResult.getResponse().getContentAsString();
		assertTrue(theResponse.contains("Certainly a righteous man !"));
	}

	@Test
	public void Test_modifySpecificNote_ShouldContainTheObservationOfTheNote() throws Exception {

		mvcResult = mockMvc.perform(get("/modifySpecificNote/" + patientNoteId)).andReturn();
		String theResponse = mvcResult.getResponse().getContentAsString();
		assertTrue(theResponse.contains("Certainly a righteous man !"));
	}

	@Test
	public void Test_addingNewNoteInterface_ShouldContainTheObservationOfTheNote() throws Exception {

		mvcResult = mockMvc.perform(get("/addNewNote/" + patientId)).andReturn();
		String theResponse = mvcResult.getResponse().getContentAsString();
		assertTrue(theResponse.contains("<div class=\"col border shadow-lg LowerWritingFrame\">"));
	}


	@Test
	public void Test_deletingAGivenNote_WhichDoesNotExist_ShouldContainError() throws Exception {

		mvcResult = mockMvc.perform(get("/deleteANote/" + (patientId + 698))).andReturn();
		String theResponse = mvcResult.getResponse().getContentAsString();
		assertTrue(theResponse.contains("Do not hesitate to check the API in charge of the patient notes and your local MongoDB."));
	}



	@AfterEach
	public void finish(){
		patientProxy.deleteAPatient(patientId);
		patientNotesProxy.deleteAGivenNote(patientNoteId);
	}


	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
