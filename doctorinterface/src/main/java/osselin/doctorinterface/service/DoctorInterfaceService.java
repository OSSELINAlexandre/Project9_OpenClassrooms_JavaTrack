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

    public Patient getSpecificPatientFromDb(int theId) {

        try {


            ResponseEntity<Patient> result = patientManagementProxy.getASpecificPatient(theId);
            return result.getBody();

        }catch (FeignException FE){

            return null;

        }

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

    public List<PatientNote> getNotesFromUserNOSQL(Integer id) {


        try{

            ResponseEntity<List<PatientNote>> result = patientNoteProxy.getAllNotesForSpecificUserSQLID(id);
            return result.getBody();

        }catch(FeignException FE){

            return null;
        }


    }

    public PatientNote getSpecificNoteFromPatient(String id) {

        try{

            ResponseEntity<PatientNote> result = patientNoteProxy.getSpecificNoteFromUser(id);
            return result.getBody();

        }catch(FeignException FE){

            return null;
        }

    }

    public Boolean addingNewNoteToPatientInDb(PatientNote patientNote) {

        try{

            ResponseEntity<Boolean> result = patientNoteProxy.addOrSaveNoteToTheDb(patientNote);
            return result.getBody();

        }catch(FeignException FE){

            return false;
        }



    }

    public Boolean modifyingAnExistingNoteIntoTheDb(PatientNote patientNote) {

        try{

            ResponseEntity<Boolean> result = patientNoteProxy.addOrSaveNoteToTheDb(patientNote);
            return result.getBody();

        }catch(FeignException FE){

            return false;
        }

    }

    public Boolean deleteSpecificNote(String id) {


        try{

            ResponseEntity<Boolean> result = patientNoteProxy.deleteAGivenNote(id);
            return result.getBody();

        }catch(FeignException FE){

            return false;
        }

    }
}
