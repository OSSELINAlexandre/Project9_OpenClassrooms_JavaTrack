package osselin.patientnotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osselin.patientnotes.model.PatientNote;
import osselin.patientnotes.repository.PatientNotesRepository;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * <b>PatientNoteService is the class in charge of the business logic for our API</b>
 *
 * <p>it makes the link between the database and the user.</p>
 *
 * @author Alexandre Osselin
 * @version 1.0
 *
 */
@Service
public class PatientNoteService {

    @Autowired
    PatientNotesRepository patientNotesRepo;


    /**
     *
     * <p>getAllNotesFromPatient request a firts name and a last name as parameters, and returns all the notes existing in the database for the given id.</p>
     *
     * @param firstName (is the first name of the patient from whom the doctor wants to retrieve information in the database)
     * @param lastName (is the last name of the patient from whom the doctor wants to retrieve information in the database)
     * @return List<PatientNote>
     */
    public List<PatientNote> getAllNotesFromPatient(String firstName, String lastName) {



        List<PatientNote> result = patientNotesRepo.findByLastNameAndFirstName(lastName, firstName);


        return result;
    }

    /**
     *
     * <p>getAllNotesFromPatientBasedOnSQLId request an id as a parameter, and returns all the notes existing in the database for the given id. </p>
     *
     * @param id (is the id of the patient from whom the doctor wants to retrieve all historical notes)
     * @return List<PatientNote>
     */
    public List<PatientNote> getAllNotesFromPatientBasedOnSQLId(Integer id) {

        List<PatientNote> result = patientNotesRepo.findBySqlId(String.valueOf(id));

        return result;

    }

    /**
     *
     * <p>getSpecificNoteFromPatient request an id as a parameter and returns the related patient note.</p>
     *
     * <p>It catches the error if the id isn't related to any note in the database, and return a null.</p>
     *
     * @param id
     * @return PatientNote
     */
    public PatientNote getSpecificNoteFromPatient(String id) {

        try {

            PatientNote result = patientNotesRepo.findById(id).get();
            return result;

        }catch(NoSuchElementException ne){
            return null;
        }

    }

    /**
     * <p>saveANewNoteToTheDataBase request a patient note as a parameter and add it to the database.</p>
     *
     * <p>The verification for minimal condition was done in the controller.</p>
     *
     * @param pn
     * @return Boolean
     */
    public Boolean saveANewNoteToTheDataBase(PatientNote pn) {

        PatientNote pnresult = patientNotesRepo.save(pn);
        return true;
    }

    /**
     * <p>deleteAGivenNote request an Id as a parameter and delete the related note in the database.</p>
     *
     * <p>It catches the errors coming if the id isn't related to any notes in the database.</p>
     *
     * @param id
     * @return Boolean
     */
    public Boolean deleteAGivenNote(String id) {

        try {
            PatientNote pn = patientNotesRepo.findById(id).get();
            patientNotesRepo.delete(pn);
            return true;
        }catch(NullPointerException E){
            return false;
        }catch(NoSuchElementException LE){
            return false;
        }
    }

    public PatientNotesRepository getPatientNotesRepo() {
        return patientNotesRepo;
    }

    public void setPatientNotesRepo(PatientNotesRepository patientNotesRepo) {
        this.patientNotesRepo = patientNotesRepo;
    }

}
