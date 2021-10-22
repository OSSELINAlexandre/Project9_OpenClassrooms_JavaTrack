package osselin.patientmanagementapi.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import osselin.patientmanagementapi.model.Patient;

import java.util.Optional;

/**
 *
 * <p>PatientManagementRepository is the interface that gives access to the local MySQL database.</p>
 *
 * <p>It transform the types of the database in a Java understandable type, and vice-versa.</p>
 *
 *
 */
@Repository
public interface PatientManagementRepository extends CrudRepository<Patient, Integer> {


    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);


}
