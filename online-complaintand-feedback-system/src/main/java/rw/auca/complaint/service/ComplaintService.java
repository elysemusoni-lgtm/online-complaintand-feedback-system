package rw.auca.complaint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.complaint.model.Complaint;
import rw.auca.complaint.model.ComplaintStatus;
import rw.auca.complaint.repository.ComplaintRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

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

    public String delete(UUID id) {
        if (!complaintRepository.existsById(id)) {
            return "The complaint with that id does not exist";
        }
        complaintRepository.deleteById(id);
        return "Complaint deleted successfully";
    }
}
