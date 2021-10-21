package osselin.patientnotes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import osselin.patientnotes.model.PatientNote;

import java.util.List;

public interface PatientNotesRepository extends MongoRepository<PatientNote, String> {

    List<PatientNote> findByLastNameAndFirstName(String lastName, String firstName);

    List<PatientNote> findBySqlId(String id);

}
