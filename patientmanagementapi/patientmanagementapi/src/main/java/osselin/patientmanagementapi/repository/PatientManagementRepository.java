package osselin.patientmanagementapi.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import osselin.patientmanagementapi.model.Patient;

@Repository
public interface PatientManagementRepository extends CrudRepository<Patient, Integer> {


    Patient findByFirstNameAndLastName(String firstName, String lastName);


}
