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

@Service
public class DiagnosisService {

    @Autowired
    PatientNoteProxy patientNoteProxy;

    @Autowired
    PatientProxy patientProxy;


    public String assessThePatientBasedOnId(Integer theId) {

        try {

            Patient thePatient = patientProxy.getASpecificPatient(theId).get();
            List<PatientNote> allNotes = patientNoteProxy.getAllNotesForSpecificUser(thePatient.getFirstName(), thePatient.getLastName());
            return calculationAlgortihm(thePatient, allNotes);

        }catch (FeignException fe){

            return "Could not find the given patient with this ID ! :)";

        }

    }

    public String assessThePatientBasedOnFamilyName(String firstName, String familyName) {

        try {

            Patient thePatient = patientProxy.getASpecificPatientBasedOnFamillyAndFirstName(firstName, familyName).get();
            List<PatientNote> allNotes = patientNoteProxy.getAllNotesForSpecificUser(thePatient.getFirstName(), thePatient.getLastName());
            return calculationAlgortihm(thePatient, allNotes);

        }catch (FeignException fe){

            return "Could not find the given patient with this ID ! :)";

        }

    }


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





    private Integer caclulateTheAgeOfPatient(Date dateOfBirth) {


        String[] resultDates = dateOfBirth.toString().split("-");

        /*
        *
        * Check the date it isn't working properly, I need to change the date in mysql*/
        LocalDate pastDate = LocalDate.of(Integer.parseInt(resultDates[0]), Integer.parseInt(resultDates[1]), Integer.parseInt(resultDates[2]));
        System.out.println(dateOfBirth.toString());

        LocalDate nowDate = LocalDate.now();
        Period period = Period.between(pastDate, nowDate);
        System.out.println("Hey, waht about this method ,  " + period.toString());
        int result = (int) ((period.toTotalMonths()) / 12);
        System.out.println("HEYYYY HOW OLD AM I ! " + result);
        return result;
    }

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
