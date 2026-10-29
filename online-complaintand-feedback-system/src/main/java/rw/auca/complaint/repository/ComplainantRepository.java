package rw.auca.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.auca.complaint.model.Complainant;

import java.util.UUID;

@Repository
public interface ComplainantRepository extends JpaRepository<Complainant, UUID> {
}
