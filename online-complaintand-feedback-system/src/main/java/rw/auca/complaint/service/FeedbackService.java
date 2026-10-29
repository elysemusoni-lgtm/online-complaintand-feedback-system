package rw.auca.complaint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.complaint.model.Complaint;
import rw.auca.complaint.model.ComplaintStatus;
import rw.auca.complaint.model.Feedback;
import rw.auca.complaint.repository.ComplaintRepository;
import rw.auca.complaint.repository.FeedbackRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    // E1: write a feedback, only for a RESOLVED complaint
    public String save(UUID complaintId, Feedback feedback) {
        Optional<Complaint> found = complaintRepository.findById(complaintId);
        if (found.isEmpty()) {
            return "The complaint with that id does not exist";
        }
        Complaint complaint = found.get();
        if (complaint.getStatus() != ComplaintStatus.RESOLVED) {
            return "Feedbacks are only for RESOLVED complaints";
        }
        feedback.setSubmittedDate(LocalDate.now());
        feedback.setComplaint(complaint);
        feedbackRepository.save(feedback);
        return "Feedback saved successfully";
    }

    public List<Feedback> getAll() {
        return feedbackRepository.findAll();
    }

    // E2
    public List<Feedback> getByComplainantEmail(String email) {
        return feedbackRepository.findByComplainantEmail(email);
    }

    // E3
    public String delete(UUID id) {
        if (!feedbackRepository.existsById(id)) {
            return "The feedback with that id does not exist";
        }
        feedbackRepository.deleteById(id);
        return "Feedback deleted successfully";
    }
}
