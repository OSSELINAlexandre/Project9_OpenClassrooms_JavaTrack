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

/**
 * <b>DoctorInterfaceService is the class in charge of the business logic for our interface</b>
 *
 * <p>it makes the link between the doctor and all the APIs.</p>
 *
 * <p>All the method that we call here have an echo in one of the APIs</p>
 *
 * <p>To summarize the methods, we have the CRUD methods for PatientManagement and PatientNotes</p>
 *
 */
@Service
public class DoctorInterfaceService {

    @Autowired
    PatientManagementProxy patientManagementProxy;

    @Autowired
    PatientNoteProxy patientNoteProxy;


    /**
     *
     * getAllTheListOfPatient call the proxy of PatientManagement and returns a list of all current patients.
     *
     * @return List<Patient>
     */
    public List<Patient> getAllTheListOfPatient() {



        return patientManagementProxy.getAllTheListOfPatient();

    }

    /**
     *
     * addingNewPatientToTheDb requires a patient as an argument and call the proxy of PatientManagement to save the given patients.
     *
     * @return List<Patient>
     */
    public boolean addingNewPatientToTheDb(Patient thePatient) {

        try {

            ResponseEntity result = patientManagementProxy.addThePatient(thePatient);

        }catch(FeignException FE){

            return false;
        }

        return true;
    }

    /**
     *
     * <p>getSpecificPatientFromDb requires an id as a parameter, call the Patient Management proxy in order to return the patient related to the id.</p>
     *
     * <p>If no patient exist for this id, the method returns null.</p>
     *
     * @param theId
     * @return Patient
     */
    public Patient getSpecificPatientFromDb(int theId) {

        try {


            ResponseEntity<Patient> result = patientManagementProxy.getASpecificPatient(theId);
            return result.getBody();

        }catch (FeignException FE){

            return null;

        }

    }

    /**
     *
     * <p>deleteSpecificPatient requires an id as a parameter, call the Patient Management proxy, delete the related patient, and returns true. </p>
     *
     * <p>It no patient is found for this given id, the methods return false.</p>
     * @param theId
     * @return boolean
     */
    public boolean deleteSpecificPatient(int theId) {

        try {

            ResponseEntity result = patientManagementProxy.deleteAPatient(theId);

        }catch(FeignException FE){

            return false;
        }

        return true;
    }

    /**
     * <p>updatingAPatientToTheDatabase requires an id and a patient as parameters, update the given patient and returns true if the operation was done.</p>
     *
     * <p>It returns false otherwise. </p>
     *
     *
     * @param id
     * @param theNewPatientRecords
     * @return Boolean
     */
    public Boolean updatingAPatientToTheDatabase(Integer id, Patient theNewPatientRecords) {

        try{

            ResponseEntity result = patientManagementProxy.updateThePatient(id, theNewPatientRecords);

        }catch(FeignException FE){

            return false;
        }

        return true;
    }


    /**
     *
     * <p>getNotesFromUserNOSQL requires an id as a parameter and returns the list of patient notes that are related to the patient id.</p>
     *
     * <p>It returns null </p>
     *
     * @param id
     * @return List<PatientNote>
     */
    public List<PatientNote> getNotesFromUserNOSQL(Integer id) {


        try{

            ResponseEntity<List<PatientNote>> result = patientNoteProxy.getAllNotesForSpecificUserSQLID(id);
            return result.getBody();

        }catch(FeignException FE){

            return null;
        }


    }

    /**
     * <p>getSpecificNoteFromPatient requires an id as a parameter and returns the patient note with the given id.</p>
     *
     * <p>It returns null if no patient note is known with this id.</p>
     *
     * @param id
     * @return PatientNote
     */
    public PatientNote getSpecificNoteFromPatient(String id) {

        try{

            ResponseEntity<PatientNote> result = patientNoteProxy.getSpecificNoteFromUser(id);
            return result.getBody();

        }catch(FeignException FE){

            return null;
        }

    }

    /**
     * <p>addingNewNoteToPatientInDb requires a patient note as a parameter and save the new note to the database by connecting to the proxy in charge of it</p>
     *
     * <p>It returns false if the operation couldn't be archive.</p>
     *
     * @param patientNote
     * @return Boolean
     */
    public Boolean addingNewNoteToPatientInDb(PatientNote patientNote) {

        try{

            ResponseEntity<Boolean> result = patientNoteProxy.addOrSaveNoteToTheDb(patientNote);
            return result.getBody();

        }catch(FeignException FE){

            return false;
        }



    }

    /**
     * <p>modifyingAnExistingNoteIntoTheDb requires a patient note as a parameter and save the new note to the database by connecting to the proxy in charge of it</p>
     *
     * <p>It returns false if the operation couldn't be archive.</p>
     *
     * @param patientNote
     * @return Boolean
     */
    public Boolean modifyingAnExistingNoteIntoTheDb(PatientNote patientNote) {

        try{

            ResponseEntity<Boolean> result = patientNoteProxy.addOrSaveNoteToTheDb(patientNote);
            return result.getBody();

        }catch(FeignException FE){

            return false;
        }

    }

    /**
     * <p>deleteSpecificNote requires an id as a parameter and delete note with this id  in the database, by connecting to the proxy in charge of it</p>
     *
     * <p>It returns false if the operation couldn't be archive.</p>
     *
     * @param patientNote
     * @return Boolean
     */
    public Boolean deleteSpecificNote(String id) {


        try{

            ResponseEntity<Boolean> result = patientNoteProxy.deleteAGivenNote(id);
            return result.getBody();

        }catch(FeignException FE){

            return false;
        }

    }
}
