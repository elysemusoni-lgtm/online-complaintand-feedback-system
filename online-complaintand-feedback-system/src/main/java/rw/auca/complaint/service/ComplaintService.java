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

    public String save(Complaint complaint) {
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
        complaint.setStatus(newData.getStatus());
        complaint.setSubject(newData.getSubject());
        complaint.setDescription(newData.getDescription());
        complaint.setComplainant(newData.getComplainant());
        complaint.setSupportAgent(newData.getSupportAgent());
        complaintRepository.save(complaint);
        return "Complaint updated successfully";
    }

    public String delete(UUID id) {
        if (!complaintRepository.existsById(id)) {
            return "The complaint with that id does not exist";
        }
        complaintRepository.deleteById(id);
        return "Complaint deleted successfully";
    }
}
