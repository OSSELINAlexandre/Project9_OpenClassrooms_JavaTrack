package osselin.diagnosisapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import osselin.diagnosisapi.model.Patient;
import osselin.diagnosisapi.model.PatientNote;
import osselin.diagnosisapi.service.DiagnosisService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DiagnosisapiApplicationTests {

	@Autowired
	DiagnosisService diagnosisService;

	@Test
	public void test_calculationAlgortihm_ForMale(){


		//Minus 30 YO : 2 symptoms == None
		Patient theTestBPatient = new Patient("Alexandre" , "OSSELIN", new Date(1995, 06, 11), 'M', "32 rue du moulin" , "0661528075");
		PatientNote pb = new PatientNote("77", "Alexandre" , "OSSELIN" , "Microalbumine");
		List<PatientNote> testListB = new ArrayList<>();
		testListB.add(pb);

		//Minus 30 YO : 4 symptoms == In Danger
		Patient theTestCPatient = new Patient("Alexandre" , "OSSELIN", new Date(1995, 06, 11), 'M', "32 rue du moulin" , "0661528075");
		PatientNote pc = new PatientNote("77", "Alexandre" , "OSSELIN" , "Microalbumine, Taille, Cholestérol, Anticorps,");
		List<PatientNote> testListC = new ArrayList<>();
		testListC.add(pc);


		//Minus 30 YO : 9 symptoms == Early Onset
		Patient theTestPatient = new Patient("Alexandre" , "OSSELIN", new Date(1995, 06, 11), 'M', "32 rue du moulin" , "0661528075");
		PatientNote pa = new PatientNote("77", "Alexandre" , "OSSELIN" , "Microalbumine, Taille, Cholestérol, Anticorps, Réaction, Rechute, Vertige, Fumeur, Anormal");
		List<PatientNote> testList = new ArrayList<>();
		testList.add(pa);



		/*
		*
		* Before this comment is test for minus than 30 years old.
		*
		*/

		//More than 30 YO : 1 symptoms == None
		Patient theTestDPatient = new Patient("Alexandre" , "OSSELIN", new Date(1985, 06, 11), 'M', "32 rue du moulin" , "0661528075");
		PatientNote pd = new PatientNote("77", "Alexandre" , "OSSELIN" , "Microalbumine");
		List<PatientNote> testListD = new ArrayList<>();
		testListD.add(pd);

		//More than 30 YO : 3 symptoms == Borderline
		Patient theTestEPatient = new Patient("Alexandre" , "OSSELIN", new Date(1985, 06, 11), 'M', "32 rue du moulin" , "0661528075");
		PatientNote pe = new PatientNote("77", "Alexandre" , "OSSELIN" , "Microalbumine, Taille, Cholestérol");
		List<PatientNote> testListE = new ArrayList<>();
		testListE.add(pe);


		//More than 30 YO : 7 symptoms == In Danger
		Patient theTestFPatient = new Patient("Alexandre" , "OSSELIN", new Date(1985, 06, 11), 'M', "32 rue du moulin" , "0661528075");
		PatientNote pf = new PatientNote("77", "Alexandre" , "OSSELIN" , "Microalbumine, Taille, Cholestérol, Anticorps, Réaction, Rechute, Vertige");
		List<PatientNote> testListF = new ArrayList<>();
		testListF.add(pf);


		//More than 30 YO : 9 symptoms == Early Onset
		Patient theTestGPatient = new Patient("Alexandre" , "OSSELIN", new Date(1985, 06, 11), 'M', "32 rue du moulin" , "0661528075");
		PatientNote pg = new PatientNote("77", "Alexandre" , "OSSELIN" , "Microalbumine, Taille, Cholestérol, Anticorps, Réaction, Rechute, Vertige, Fumeur, Anormal");
		List<PatientNote> testListG = new ArrayList<>();
		testListG.add(pg);



		String result = diagnosisService.calculationAlgortihm(theTestPatient, testList);
		String resultB = diagnosisService.calculationAlgortihm(theTestBPatient, testListB);
		String resultC = diagnosisService.calculationAlgortihm(theTestCPatient, testListC);
		String resultD = diagnosisService.calculationAlgortihm(theTestDPatient, testListD);
		String resultE = diagnosisService.calculationAlgortihm(theTestEPatient, testListE);
		String resultF = diagnosisService.calculationAlgortihm(theTestFPatient, testListF);
		String resultG = diagnosisService.calculationAlgortihm(theTestGPatient, testListG);

		assertTrue(result.contains("Early Onset"));
		assertTrue(resultB.contains("None"));
		assertTrue(resultC.contains("In Danger"));
		assertTrue(resultD.contains("None"));
		assertTrue(resultE.contains("Borderline"));
		assertTrue(resultF.contains("In Danger"));
		assertTrue(resultG.contains("Early Onset"));

	}

	@Test
	public void test_calculationAlgortihm_ForFemale(){


		//Minus 30 YO : 2 symptoms == None
		Patient theTestBPatient = new Patient("Alexandra" , "OSSELIN", new Date(1995, 06, 11), 'F', "32 rue du moulin" , "0661528075");
		PatientNote pb = new PatientNote("77", "Alexandra" , "OSSELIN" , "Microalbumine, Microalbumine");
		List<PatientNote> testListB = new ArrayList<>();
		testListB.add(pb);

		//Minus 30 YO : 4 symptoms == In Danger
		Patient theTestCPatient = new Patient("Alexandra" , "OSSELIN", new Date(1995, 06, 11), 'F', "32 rue du moulin" , "0661528075");
		PatientNote pc = new PatientNote("77", "Alexandra" , "OSSELIN" , "Microalbumine, Taille, Cholestérol, Anticorps, ");
		List<PatientNote> testListC = new ArrayList<>();
		testListC.add(pc);


		//Minus 30 YO : 9 symptoms == Early Onset
		Patient theTestPatient = new Patient("Alexandra" , "OSSELIN", new Date(1995, 06, 11), 'F', "32 rue du moulin" , "0661528075");
		PatientNote pa = new PatientNote("77", "Alexandra" , "OSSELIN" , "Microalbumine, Taille, Cholestérol, Anticorps, Réaction, Rechute, Vertige, Fumeur, Anormal");
		List<PatientNote> testList = new ArrayList<>();
		testList.add(pa);



		/*
		 *
		 * Before this comment is test for minus than 30 years old.
		 *
		 */

		//More than 30 YO : 1 symptoms == None
		Patient theTestDPatient = new Patient("Alexandra" , "OSSELIN", new Date(1985, 06, 11), 'F', "32 rue du moulin" , "0661528075");
		PatientNote pd = new PatientNote("77", "Alexandra" , "OSSELIN" , "Microalbumine");
		List<PatientNote> testListD = new ArrayList<>();
		testListD.add(pd);

		//More than 30 YO : 3 symptoms == Borderline
		Patient theTestEPatient = new Patient("Alexandra" , "OSSELIN", new Date(1985, 06, 11), 'F', "32 rue du moulin" , "0661528075");
		PatientNote pe = new PatientNote("77", "Alexandre" , "OSSELIN" , "Microalbumine, Taille, Cholestérol");
		List<PatientNote> testListE = new ArrayList<>();
		testListE.add(pe);


		//More than 30 YO : 7 symptoms == In Danger
		Patient theTestFPatient = new Patient("Alexandra" , "OSSELIN", new Date(1985, 06, 11), 'F', "32 rue du moulin" , "0661528075");
		PatientNote pf = new PatientNote("77", "Alexandra" , "OSSELIN" , "Microalbumine, Taille, Cholestérol, Anticorps, Réaction, Rechute, Vertige");
		List<PatientNote> testListF = new ArrayList<>();
		testListF.add(pf);


		//More than 30 YO : 9 symptoms == Early Onset
		Patient theTestGPatient = new Patient("Alexandra" , "OSSELIN", new Date(1985, 06, 11), 'F', "32 rue du moulin" , "0661528075");
		PatientNote pg = new PatientNote("77", "Alexandra" , "OSSELIN" , "Microalbumine, Taille, Cholestérol, Anticorps, Réaction, Rechute, Vertige, Fumeur, Anormal");
		List<PatientNote> testListG = new ArrayList<>();
		testListG.add(pg);



		String result = diagnosisService.calculationAlgortihm(theTestPatient, testList);
		String resultB = diagnosisService.calculationAlgortihm(theTestBPatient, testListB);
		String resultC = diagnosisService.calculationAlgortihm(theTestCPatient, testListC);
		String resultD = diagnosisService.calculationAlgortihm(theTestDPatient, testListD);
		String resultE = diagnosisService.calculationAlgortihm(theTestEPatient, testListE);
		String resultF = diagnosisService.calculationAlgortihm(theTestFPatient, testListF);
		String resultG = diagnosisService.calculationAlgortihm(theTestGPatient, testListG);

		assertTrue(result.contains("Early Onset"));
		assertTrue(resultB.contains("None"));
		assertTrue(resultC.contains("In Danger"));
		assertTrue(resultD.contains("None"));
		assertTrue(resultE.contains("Borderline"));
		assertTrue(resultF.contains("In Danger"));
		assertTrue(resultG.contains("Early Onset"));

	}

}
