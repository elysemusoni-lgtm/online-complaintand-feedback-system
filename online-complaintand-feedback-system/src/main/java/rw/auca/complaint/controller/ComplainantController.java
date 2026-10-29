package rw.auca.complaint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.complaint.model.Complainant;
import rw.auca.complaint.service.ComplainantService;
import rw.auca.complaint.service.SupportAgentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/complainants")
public class ComplainantController {

    @Autowired
    private ComplainantService complainantService;

    @Autowired
    private SupportAgentService supportAgentService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Complainant complainant) {
        String message = complainantService.save(complainant);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Complainant>> getAll() {
        return new ResponseEntity<>(complainantService.getAll(), HttpStatus.OK);
    }

    // B1
    @GetMapping("/by-organisation")
    public ResponseEntity<List<Complainant>> getByOrganisation(@RequestParam String organisation) {
        return new ResponseEntity<>(complainantService.getByOrganisation(organisation), HttpStatus.OK);
    }

    // C4
    @GetMapping("/of-support-agent/{supportAgentId}")
    public ResponseEntity<Object> getBySupportAgent(@PathVariable UUID supportAgentId) {
        if (supportAgentService.getById(supportAgentId).isEmpty()) {
            return new ResponseEntity<>("The support agent with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(complainantService.getBySupportAgent(supportAgentId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Complainant> found = complainantService.getById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The complainant with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Complainant complainant) {
        String message = complainantService.update(id, complainant);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = complainantService.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
