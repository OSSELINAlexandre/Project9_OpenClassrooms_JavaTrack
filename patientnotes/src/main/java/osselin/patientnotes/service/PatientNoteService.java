package osselin.patientnotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osselin.patientnotes.model.PatientNote;
import osselin.patientnotes.repository.PatientNotesRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PatientNoteService {

    @Autowired
    PatientNotesRepository patientNotesRepo;


    public List<PatientNote> getAllNotesFromPatient(String firstName, String lastName) {



        List<PatientNote> result = patientNotesRepo.findByLastNameAndFirstName(lastName, firstName);


        return result;
    }
    public List<PatientNote> getAllNotesFromPatientBasedOnSQLId(Integer id) {

        List<PatientNote> result = patientNotesRepo.findBySqlId(String.valueOf(id));

        return result;

    }

    public PatientNote getSpecificNoteFromPatient(String id) {

        try {
            PatientNote result = patientNotesRepo.findById(id).get();
            return result;
        }catch(NoSuchElementException ne){
            return null;
        }

    }

    //TODO make that function return a true or false statement in order to know where we are oing.
    public Boolean saveANewNoteToTheDataBase(PatientNote pn) {

        PatientNote pnresult = patientNotesRepo.save(pn);
        return true;
    }

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
