package osselin.diagnosisapi.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import osselin.diagnosisapi.model.PatientNote;

import java.util.List;

@FeignClient(name ="patientnotesapi", url = "host.docker.internal:8082")
public interface PatientNoteProxy {

    @GetMapping("/patHistory/getName")
    ResponseEntity<List<PatientNote>> getAllNotesForSpecificUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName);

    @GetMapping("/patHistory/get")
    ResponseEntity<PatientNote> getSpecificNoteFromUser(@RequestParam("id") String id);

    @PostMapping("/patHistory/add")
    ResponseEntity<Boolean> addOrSaveNoteToTheDb(@RequestBody PatientNote patientNote);


    @GetMapping("/patHistory/delete")
    ResponseEntity<Boolean> deleteAGivenNote(@RequestParam("id") String id);

    @GetMapping("/patHistory/getNotes")
    ResponseEntity<List<PatientNote>> getAllNotesForUserBasedOnSqlID(@RequestParam("id") Integer id);


}
