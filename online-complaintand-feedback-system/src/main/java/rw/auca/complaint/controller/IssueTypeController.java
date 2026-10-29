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
import org.springframework.web.bind.annotation.RestController;
import rw.auca.complaint.model.IssueType;
import rw.auca.complaint.service.IssueTypeService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/issue-types")
public class IssueTypeController {

    @Autowired
    private IssueTypeService issueTypeService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody IssueType issueType) {
        String message = issueTypeService.save(issueType);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<IssueType>> getAll() {
        return new ResponseEntity<>(issueTypeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<IssueType> found = issueTypeService.getById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The issue type with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody IssueType issueType) {
        String message = issueTypeService.update(id, issueType);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = issueTypeService.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
