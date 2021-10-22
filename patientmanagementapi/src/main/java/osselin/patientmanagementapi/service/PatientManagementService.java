package osselin.patientmanagementapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osselin.patientmanagementapi.model.Patient;
import osselin.patientmanagementapi.repository.PatientManagementRepository;

import java.util.List;
import java.util.Optional;

/**
 * <b>PatientManagementService is the class in charge of the business logic for our API</b>
 *
 * <p>it makes the link between the database and the user.</p>
 *
 * @author Alexandre Osselin
 * @version 1.0
 *
 */
@Service
public class PatientManagementService {

    @Autowired
    PatientManagementRepository patientRepo;

    /**
     * <p>addANewPatientToTheDatabase add a new patient to the database.</p>
     *
     * <p>it first check if the user cannot be found with the first name and last name.</p>
     *
     * <p>If it can't, it saves the patient and returns the given patient.</p>
     *
     * <p>If it can, it returns null. The method therefore verifies if a patient already exist with this given first name and last name.</p>
     *
     * @param newPatient (is the instance of Patient the doctor wants to save in the database)
     * @return Patient
     */
    public Patient addANewPatientToTheDatabase(Patient newPatient) {


        if(patientRepo.findByFirstNameAndLastName(newPatient.getFirstName(), newPatient.getLastName()).isEmpty() ){


            Patient result = patientRepo.save(newPatient);
            return result;

        }

        return null;

    }

    /**
     * <p>updateAGivenPatient update a patient to the database.</p>
     * <p>It takes an Id and a Patient instance as parameters.</p>
     *
     * <p>it first check if the user can be found with the given id.</p>
     *
     * <p>If yes, it saves the patient and returns the given patient.</p>
     *
     * <p>If not, it returns null.</p>
     *
     * @param patientId (is the id of the patient from which the doctors wants to update information)
     * @param newAttributesForGivenPatient (is the instance containing new information that the doctor wants to update on the patient)
     * @return Patient
     */
    public Patient updateAGivenPatient(Integer patientId, Patient newAttributesForGivenPatient) {


        if(patientRepo.findById(patientId).isPresent()){

            Patient intermediaryResult = patientRepo.findById(patientId).get();

            try {
                if (patientRepo.findByFirstNameAndLastName(newAttributesForGivenPatient.getFirstName(), newAttributesForGivenPatient.getLastName()).get().getId() != intermediaryResult.getId()) {

                    return null;
                }

            }catch(Exception e){


            }

            Patient result = patientRepo.save(newAttributesForGivenPatient);


            return result;
        }

        return null;
    }

    /**
     *
     * <p>deleteAGivenPatient deletes patient in the database.</p>
     *
     * <p>It first checks if the id is related to a patient.</p>
     *
     * <p>If yes, it delete the returned patient and returns as a result true.</p>
     *
     * <p>If not, the method returns false.</p>
     *
     * @param patientId (is the id from which the doctor expects to draw a Patient)
     * @return Boolean
     */
    public Boolean deleteAGivenPatient(Integer patientId) {

        if(patientRepo.findById(patientId).isPresent()){

            patientRepo.deleteById(patientId);
            return true;
        }

        return false;
    }


    /**
     *
     * <p>getAllTheList returns all the patient available in the database.</p>
     *
     * @return List<Patient>
     */
    public List<Patient> getAllTheList() {

        List<Patient> result = (List<Patient> ) patientRepo.findAll();
        return result;
    }

    /**
     * <p>getASpecificPatient takes an Id as a parameter and it returns an optional (present or empty) of the patient for the related id.</p>
     *
     * <p>It will be present if the id is related to a patient.</p>
     *
     * <p>It will be false if the id isn't related to a patient.</p>
     *
     * @param theId (is the Id from which the doctors wants to draw a patient)
     * @return Optional<Patient>
     */
    public Optional<Patient> getASpecificPatient(Integer theId) {

        return patientRepo.findById(theId);
    }

    /**
     * <p>getASpecificPatientBasedOnIdentity takes a firstName and a familyName as parameters and it returns an optional (present or empty) of the patient for the related id.</p>
     *
     * <p>It will be present if the firstName and the familyName is related to a patient.</p>
     *
     * <p>It will be false if the combination isn't related to a patient.</p>
     * @param firstName (is the first name from which the doctor wants to draw results)
     * @param familyName (is the family name from which the doctor wants to draw results)
     * @return Optional<Patient>
     */
    public Optional<Patient> getASpecificPatientBasedOnIdentity(String firstName, String familyName) {


        return patientRepo.findByFirstNameAndLastName(firstName, familyName);
    }


    public PatientManagementRepository getPatientRepo() {
        return patientRepo;
    }

    public void setPatientRepo(PatientManagementRepository patientRepo) {
        this.patientRepo = patientRepo;
    }
}
