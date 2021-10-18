package osselin.patientnotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osselin.patientnotes.model.PatientNote;
import osselin.patientnotes.repository.PatientNotesRepository;

import java.util.List;

@Service
public class PatientNoteService {

    @Autowired
    PatientNotesRepository patientNotesRepo;


    public List<PatientNote> getAllNotesFromPatient(String firstName, String lastName) {


        System.out.println("IN SERVICE DO WE GET HERE ?" + firstName + "||||| " + lastName);

        List<PatientNote> result = patientNotesRepo.findByLastNameAndFirstName(lastName, firstName);

        System.out.println("IN SERVICE DO WE GET HERE ? SIZE : " + result.size());


        return result;
    }

    public PatientNote getSpecificNoteFromPatient(String id) {

        return patientNotesRepo.findById(id).get();
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
        }catch(NullPointerException E ){
            return false;
        }
    }
}
