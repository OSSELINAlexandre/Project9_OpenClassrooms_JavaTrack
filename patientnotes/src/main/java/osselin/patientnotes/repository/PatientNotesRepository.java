package osselin.patientnotes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import osselin.patientnotes.model.PatientNote;

import java.util.List;


/**
 *
 * <p> PatientNotesRepository is the interface that gives access to the local MongoDb database. </p>
 *
 * <p> It transform the types of the database in a Java understandable type, and vice-versa.</p>
 *
 *
 */

public interface PatientNotesRepository extends MongoRepository<PatientNote, String> {

    List<PatientNote> findByLastNameAndFirstName(String lastName, String firstName);

    List<PatientNote> findBySqlId(String id);

}
