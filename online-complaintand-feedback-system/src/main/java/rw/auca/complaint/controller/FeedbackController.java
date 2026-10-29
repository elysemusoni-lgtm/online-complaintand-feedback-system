package rw.auca.complaint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.complaint.model.Feedback;
import rw.auca.complaint.service.FeedbackService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // E1
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID complaintId, @RequestBody Feedback feedback) {
        String message = feedbackService.save(complaintId, feedback);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.startsWith("Feedbacks are only")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAll() {
        return new ResponseEntity<>(feedbackService.getAll(), HttpStatus.OK);
    }

    // E2
    @GetMapping("/complainant/{email}")
    public ResponseEntity<List<Feedback>> getByComplainantEmail(@PathVariable String email) {
        return new ResponseEntity<>(feedbackService.getByComplainantEmail(email), HttpStatus.OK);
    }

    // E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = feedbackService.delete(id);
        if (message.contains("does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
