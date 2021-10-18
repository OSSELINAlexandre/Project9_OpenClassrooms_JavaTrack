package osselin.diagnosisapi.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import osselin.diagnosisapi.model.PatientNote;

import java.util.List;

@FeignClient(name ="patientnotesapi", url = "localhost:8082")
public interface PatientNoteProxy {

    @GetMapping("/getNote")
    public List<PatientNote> getAllNotesForSpecificUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName);

    @GetMapping("/getAGivenNote")
    PatientNote getSpecificNoteFromUser(@RequestParam("id") String id);

    @PostMapping("/saveANewNote")
    Boolean addOrSaveNoteToTheDb(@RequestBody PatientNote patientNote);


    @GetMapping("/deleteANote")
    Boolean deleteAGivenNote(@RequestParam("id") String id);

}
