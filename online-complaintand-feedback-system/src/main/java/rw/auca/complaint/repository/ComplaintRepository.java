package rw.auca.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.auca.complaint.model.Complaint;
import rw.auca.complaint.model.ComplaintStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {

    // B2: derived query, earliest filedDate first
    List<Complaint> findByStatusOrderByFiledDateAsc(ComplaintStatus status);

    // B3: derived query, both dates inclusive, ordered by filedDate
    List<Complaint> findByFiledDateBetweenOrderByFiledDateAsc(LocalDate start, LocalDate end);
}
