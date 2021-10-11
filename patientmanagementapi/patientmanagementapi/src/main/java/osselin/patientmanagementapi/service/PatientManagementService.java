package osselin.patientmanagementapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osselin.patientmanagementapi.model.Patient;
import osselin.patientmanagementapi.repository.PatientManagementRepository;

import java.util.List;

@Service
public class PatientManagementService {

    @Autowired
    PatientManagementRepository patientRepo;

    public boolean addANewPatientToTheDatabase(Patient newPatient) {

        if(patientRepo.findByFirstNameAndLastName(newPatient.getFirstName(), newPatient.getLastName()) == null ){

            patientRepo.save(newPatient);
            return true;

        }

        return false;

    }

    public Boolean updateAGivenPatient(Integer patientId, Patient newAttributesForGivenPatient) {

        if(patientRepo.findById(patientId).isPresent()){

            newAttributesForGivenPatient.setId(patientId);
            patientRepo.delete(patientRepo.findById(patientId).get());
            patientRepo.save(newAttributesForGivenPatient);
            return true;
        }

        return false;
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
}
