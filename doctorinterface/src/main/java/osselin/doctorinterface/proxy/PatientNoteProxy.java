package osselin.doctorinterface.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import osselin.doctorinterface.model.PatientNote;

import java.util.List;

@FeignClient(name ="patientnotesapi", url = "localhost:8082")
public interface PatientNoteProxy {


    @GetMapping("/patHistory/getName")
    public ResponseEntity<List<PatientNote>> getAllNotesForSpecificUserBasedOnName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName);

    @GetMapping("/patHistory/getNotes")
    public ResponseEntity<List<PatientNote>> getAllNotesForSpecificUserSQLID(@RequestParam("id") Integer sqlId);

    @GetMapping("/patHistory/get")
    ResponseEntity<PatientNote> getSpecificNoteFromUser(@RequestParam("id") String id);

    @PostMapping("/patHistory/add")
    ResponseEntity<Boolean> addOrSaveNoteToTheDb(@RequestBody PatientNote patientNote);


    @GetMapping("/patHistory/delete")
    ResponseEntity<Boolean> deleteAGivenNote(@RequestParam("id") String id);
}
