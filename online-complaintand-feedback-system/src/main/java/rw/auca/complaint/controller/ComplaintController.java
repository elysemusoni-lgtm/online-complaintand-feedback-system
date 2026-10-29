package rw.auca.complaint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.complaint.model.Complaint;
import rw.auca.complaint.model.ComplaintStatus;
import rw.auca.complaint.service.ComplaintService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Complaint complaint) {
        String message = complaintService.save(complaint);
        if (message.equals("Complaint date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else if (message.equals("Support agent already has a complaint assigned on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Complaint>> getAll() {
        return new ResponseEntity<>(complaintService.getAll(), HttpStatus.OK);
    }

    // B2
    @GetMapping("/by-status")
    public ResponseEntity<List<Complaint>> getByStatus(@RequestParam ComplaintStatus status) {
        return new ResponseEntity<>(complaintService.getByStatus(status), HttpStatus.OK);
    }

    // B3
    @GetMapping("/between")
    public ResponseEntity<List<Complaint>> getBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(complaintService.getBetween(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Complaint> found = complaintService.getById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The complaint with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Complaint complaint) {
        String message = complaintService.update(id, complaint);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    // D2
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(@PathVariable UUID id, @RequestParam ComplaintStatus status) {
        String message = complaintService.changeStatus(id, status);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.startsWith("Cannot change status")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = complaintService.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.contains("has feedbacks")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
