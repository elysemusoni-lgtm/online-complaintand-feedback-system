package rw.auca.complaint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.complaint.model.Complaint;
import rw.auca.complaint.model.ComplaintStatus;
import rw.auca.complaint.repository.ComplaintRepository;
import rw.auca.complaint.repository.FeedbackRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    // D1: safe saving
    public String save(Complaint complaint) {
        if (complaint.getFiledDate().isBefore(LocalDate.now())) {
            return "Complaint date cannot be in the past";
        }
        if (complaintRepository.existsBySupportAgentIdAndFiledDateAndStatusNot(
                complaint.getSupportAgent().getId(), complaint.getFiledDate(), ComplaintStatus.WITHDRAWN)) {
            return "Support agent already has a complaint assigned on that date";
        }
        complaint.setStatus(ComplaintStatus.OPEN);
        complaintRepository.save(complaint);
        return "Complaint saved successfully";
    }

    public List<Complaint> getAll() {
        return complaintRepository.findAll();
    }

    // B2
    public List<Complaint> getByStatus(ComplaintStatus status) {
        return complaintRepository.findByStatusOrderByFiledDateAsc(status);
    }

    // B3
    public List<Complaint> getBetween(LocalDate start, LocalDate end) {
        return complaintRepository.findByFiledDateBetweenOrderByFiledDateAsc(start, end);
    }

    public Optional<Complaint> getById(UUID id) {
        return complaintRepository.findById(id);
    }

    public String update(UUID id, Complaint newData) {
        Optional<Complaint> found = complaintRepository.findById(id);
        if (found.isEmpty()) {
            return "The complaint with that id does not exist";
        }
        Complaint complaint = found.get();
        complaint.setFiledDate(newData.getFiledDate());
        complaint.setSubject(newData.getSubject());
        complaint.setDescription(newData.getDescription());
        complaint.setComplainant(newData.getComplainant());
        complaint.setSupportAgent(newData.getSupportAgent());
        complaintRepository.save(complaint);
        return "Complaint updated successfully";
    }

    // D2: status flow
    public String changeStatus(UUID id, ComplaintStatus newStatus) {
        Optional<Complaint> found = complaintRepository.findById(id);
        if (found.isEmpty()) {
            return "The complaint with that id does not exist";
        }
        Complaint complaint = found.get();
        ComplaintStatus current = complaint.getStatus();

        boolean allowed = false;
        if (current == ComplaintStatus.OPEN
                && (newStatus == ComplaintStatus.IN_PROGRESS || newStatus == ComplaintStatus.WITHDRAWN)) {
            allowed = true;
        }
        if (current == ComplaintStatus.IN_PROGRESS
                && (newStatus == ComplaintStatus.RESOLVED || newStatus == ComplaintStatus.WITHDRAWN)) {
            allowed = true;
        }
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }

        complaint.setStatus(newStatus);
        complaintRepository.save(complaint);
        return "Complaint status updated successfully";
    }

    // Bonus: complaints page by page
    public Page<Complaint> getPage(Pageable pageable) {
        return complaintRepository.findAll(pageable);
    }

    // F1
    public List<Object[]> getStatsByStatus() {
        return complaintRepository.countPerStatus();
    }

    // F2: support agent is absent
    @Transactional
    public String cancelDay(UUID supportAgentId, LocalDate date) {
        int count = complaintRepository.cancelDay(
                supportAgentId, date, ComplaintStatus.WITHDRAWN, ComplaintStatus.RESOLVED);
        return count + " complaints cancelled";
    }

    public String delete(UUID id) {
        Optional<Complaint> found = complaintRepository.findById(id);
        if (found.isEmpty()) {
            return "The complaint with that id does not exist";
        }
        // E3: a complaint that has feedbacks cannot be deleted
        if (feedbackRepository.existsByComplaint(found.get())) {
            return "That complaint has feedbacks, delete them first";
        }
        complaintRepository.deleteById(id);
        return "Complaint deleted successfully";
    }
}
