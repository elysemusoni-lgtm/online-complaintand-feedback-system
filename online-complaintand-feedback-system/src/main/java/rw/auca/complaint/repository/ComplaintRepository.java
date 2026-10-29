package rw.auca.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.auca.complaint.model.Complaint;

import java.util.UUID;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {
}
