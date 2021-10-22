package osselin.diagnosisapi.service;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osselin.diagnosisapi.model.Patient;
import osselin.diagnosisapi.model.PatientNote;
import osselin.diagnosisapi.proxy.PatientNoteProxy;
import osselin.diagnosisapi.proxy.PatientProxy;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <b>DiagnosisService is the class in charge of the business logic for our API</b>
 *
 * <p>it makes the link between the other API and the user.</p>
 *
 *
 */
@Service
public class DiagnosisService {

    @Autowired
    PatientNoteProxy patientNoteProxy;

    @Autowired
    PatientProxy patientProxy;


    /**
     *
     * <p>assessThePatientBasedOnId request an Id as a parameters and returns the result of the diagnostic.</p>
     *
     * <p>It tries to send request to the other API's.</p>
     * <p>The method tries to recuperate the patient related to the given id.</p>
     * <p>It then tries to recuperate all the notes related to the id.</p>
     * <p>Once all this information is recuperated, it does the diagnostic.</p>
     * <p>The method finally send the result of the diagnostic.</p>
     *
     * <p>If one of these steps cannot be done, it returns a result to the users, indicating the impossibility of the operation.</p>
     *
     * @param theId
     * @return String
     */
    public String assessThePatientBasedOnId(Integer theId) {

        try {

            Patient thePatient = patientProxy.getASpecificPatient(theId).getBody();
            List<PatientNote> allNotes = patientNoteProxy.getAllNotesForUserBasedOnSqlID(theId).getBody();
            return calculationAlgortihm(thePatient, allNotes);

        }catch (FeignException fe){

            return "Could not find the given patient with this ID ! ";

        }

    }
    /**
     *
     * <p>assessThePatientBasedOnFamilyName request a combination of a first name and a last name, and returns the result of the diagnostic.</p>
     *
     * <p>It tries to send request to the other API's.</p>
     * <p>The method tries to recuperate the patient related to the given combination.</p>
     * <p>It then tries to recuperate all the notes related to the id of the user previously taken.</p>
     * <p>Once all this information is recuperated, it does the diagnostic.</p>
     * <p>The method finally send the result of the diagnostic.</p>
     *
     * <p>If one of these steps cannot be done, it returns a result to the users, indicating the impossibility of the operation.</p>
     *
     * @param firstName
     * @param familyName
     * @return String
     */
    public String assessThePatientBasedOnFamilyName(String firstName, String familyName) {

        try {

            Patient thePatient = patientProxy.getASpecificPatientBasedOnFamillyAndFirstName(firstName, familyName).getBody();
            List<PatientNote> allNotes = patientNoteProxy.getAllNotesForUserBasedOnSqlID(thePatient.getId()).getBody();
            return calculationAlgortihm(thePatient, allNotes);

        }catch (FeignException fe){

            return "Could not find the given patient with this name and family name. If the patient exist, then no notes have been written yet.";

        }

    }


    /**
     *
     * <p>calculationAlgortihm requires a patient and his list of notes to return a diagnostic.</p>
     *
     * <p>The method first calculate the age of the patient based on his birthdate available as an attribute.</p>
     * <p>It then call the method in charge of counting the number of occurrences of buzz words given by our client.</p>
     *
     * <p>In function of the age, the number of trigger words, and the gender, it returns a diagnostic.</p>
     *
     * @param thePatient (is the patient to whom we want to emit a diagnostic)
     * @param allNotes (is the list of all notes from the patient on which the service builds the diagnostic)
     * @return String
     */
    public String calculationAlgortihm(Patient thePatient, List<PatientNote> allNotes) {

        Integer theAge = caclulateTheAgeOfPatient(thePatient.getDateOfBirth());
        Character gender = thePatient.getGender();
        Integer triggersWords = calculateTheTriggersWords(allNotes);
        String baseSentence = "Patient : " + thePatient.getLastName() +
                " (age : " + theAge + "), diabetes assessment is :";
        String diagnosis = "";

        if (theAge > 30) {

            if (triggersWords < 2) {

                diagnosis = "None";
            } else if (triggersWords >= 2 && triggersWords < 6) {

                diagnosis = "Borderline";
            } else if (triggersWords >= 6 && triggersWords < 8) {

                diagnosis = "In Danger";
            } else {
                diagnosis =  "Early Onset";
            }

        } else {

            if (gender == 'M') {
                if (triggersWords < 3) {
                    diagnosis = "None";
                } else if (triggersWords >= 3 && triggersWords < 5) {
                    diagnosis = "In Danger";
                } else if (triggersWords >= 5) {
                    diagnosis =  "Early Onset";
                }

            } else {

                if (triggersWords < 4) {
                    diagnosis = "None";
                } else if (triggersWords >= 4 && triggersWords < 6) {
                    diagnosis = "In Danger";
                } else if (triggersWords >= 6) {
                    diagnosis =  "Early Onset";
                }
            }

        }

        return baseSentence + diagnosis ;
    }


    /**
     * <p>caclulateTheAgeOfPatient requires a Date type as a parameter and returns the age of the patient.</p>
     *
     * <p>It provides a method to transform the Data type from SQL into an integer that represents the age of the given patient.</p>
     *
     * @param dateOfBirth (is the date from which we want to calculate the age)
     * @return Integer
     */
    private Integer caclulateTheAgeOfPatient(Date dateOfBirth) {


        String[] resultDates = dateOfBirth.toString().split("-");

        /*
        *
        * Check the date it isn't working properly, I need to change the date in mysql*/
        LocalDate pastDate = LocalDate.of(Integer.parseInt(resultDates[0]), Integer.parseInt(resultDates[1]), Integer.parseInt(resultDates[2]));


        LocalDate nowDate = LocalDate.now();
        Period period = Period.between(pastDate, nowDate);
        int result = (int) ((period.toTotalMonths()) / 12);
        return result;
    }

    /**
     *
     * <p>calculateTheTriggersWords requires a list of notes as a parameter.</p>
     *
     * <p>The method goes through all the given notes, and increment the counter of one if one of the trigger words is present at least one in one given note.</p>
     *
     * <p>The trigger words are defined by the client.</p>
     * <p>No other data treatment where required as part of this project.</p>
     *
     * @param allNotes (is the list of notes from which we want to count the number of occurrences of trigger words)
     * @return Integer
     */
    private Integer calculateTheTriggersWords(List<PatientNote> allNotes) {

        Set<String> buzzWord = new HashSet<>();
        buzzWord.add("Hémoglobine A1C");
        buzzWord.add("Microalbumine");
        buzzWord.add("Taille");
        buzzWord.add("Poids");
        buzzWord.add("Fumeur");
        buzzWord.add("Anormal");
        buzzWord.add("Cholestérol");
        buzzWord.add("Vertige");
        buzzWord.add("Rechute");
        buzzWord.add("Réaction");
        buzzWord.add("Anticorps");

        Integer result = 0;

        for(PatientNote pn : allNotes){
            for(String s : buzzWord){
                if(pn.observation.toLowerCase().contains(s.toLowerCase())){
                    result ++;
                }
            }
        }
        return result;
    }

}
