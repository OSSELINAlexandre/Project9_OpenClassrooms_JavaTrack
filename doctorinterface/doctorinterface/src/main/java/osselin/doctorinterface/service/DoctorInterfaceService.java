package osselin.doctorinterface.service;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import osselin.doctorinterface.model.Patient;
import osselin.doctorinterface.model.PatientNote;
import osselin.doctorinterface.proxy.PatientManagementProxy;
import osselin.doctorinterface.proxy.PatientNoteProxy;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorInterfaceService {

    @Autowired
    PatientManagementProxy patientManagementProxy;

    @Autowired
    PatientNoteProxy patientNoteProxy;


    public List<Patient> getAllTheListOfPatient() {

        return patientManagementProxy.getAllTheListOfPatient();

    }

    public boolean addingNewPatientToTheDb(Patient thePatient) {

        try {
            ResponseEntity result = patientManagementProxy.addThePatient(thePatient);

        }catch(FeignException FE){

            return false;
        }

        return true;
    }

    public Optional<Patient> getSpecificPatientFromDb(int theId) {

        return patientManagementProxy.getASpecificPatient(theId);
    }

    public boolean deleteSpecificPatient(int theId) {

        try {

            ResponseEntity result = patientManagementProxy.deleteAPatient(theId);

        }catch(FeignException FE){

            return false;
        }

        return true;
    }

    public Boolean updatingAPatientToTheDatabase(Integer id, Patient theNewPatientRecords) {

        try{

            ResponseEntity result = patientManagementProxy.updateThePatient(id, theNewPatientRecords);

        }catch(FeignException FE){

            return false;
        }

        return true;
    }

    public List<PatientNote> getNotesFromUserNOSQL(String firstName, String lastName) {

        return patientNoteProxy.getAllNotesForSpecificUser(firstName, lastName);

    }

    public PatientNote getSpecificNoteFromPatient(String id) {

        return patientNoteProxy.getSpecificNoteFromUser(id);
    }

    public Boolean addingNewNoteToPatientInDb(PatientNote patientNote) {

        return patientNoteProxy.addOrSaveNoteToTheDb(patientNote);

    }

    public Boolean modifyingAnExistingNoteIntoTheDb(PatientNote patientNote) {

        return patientNoteProxy.addOrSaveNoteToTheDb(patientNote);
    }

    public Boolean deleteSpecificNote(String id) {

        return patientNoteProxy.deleteAGivenNote(id);
    }
}
