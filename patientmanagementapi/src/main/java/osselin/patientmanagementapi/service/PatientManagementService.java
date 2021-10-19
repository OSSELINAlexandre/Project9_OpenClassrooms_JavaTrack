package osselin.patientmanagementapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osselin.patientmanagementapi.model.Patient;
import osselin.patientmanagementapi.repository.PatientManagementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PatientManagementService {

    @Autowired
    PatientManagementRepository patientRepo;

    public Patient addANewPatientToTheDatabase(Patient newPatient) {


        if(patientRepo.findByFirstNameAndLastName(newPatient.getFirstName(), newPatient.getLastName()).isEmpty() ){


            System.out.println("Is this guy been printed ??? I don't know man ");
            Patient result = patientRepo.save(newPatient);
            return result;

        }

        return null;

    }

    public Patient updateAGivenPatient(Integer patientId, Patient newAttributesForGivenPatient) {

        if(patientRepo.findById(patientId).isPresent()){

            newAttributesForGivenPatient.setId(patientId);
            Patient result = patientRepo.save(newAttributesForGivenPatient);


            return result;
        }

        return null;
    }

    public Boolean deleteAGivenPatient(Integer patientId) {

        if(patientRepo.findById(patientId).isPresent()){

            patientRepo.deleteById(patientId);
            return true;
        }

        return false;
    }

    public PatientManagementRepository getPatientRepo() {
        return patientRepo;
    }

    public void setPatientRepo(PatientManagementRepository patientRepo) {
        this.patientRepo = patientRepo;
    }

    public List<Patient> getAllTheList() {

        List<Patient> result = (List<Patient> ) patientRepo.findAll();
        return result;
    }

    public Optional<Patient> getASpecificPatient(Integer theId) {

        return patientRepo.findById(theId);
    }

    public Optional<Patient> getASpecificPatientBasedOnIdentity(String firstName, String familyName) {


        System.out.println(patientRepo.findByFirstNameAndLastName(firstName, familyName).get().getDateOfBirth().getTime());
        return patientRepo.findByFirstNameAndLastName(firstName, familyName);
    }
}
