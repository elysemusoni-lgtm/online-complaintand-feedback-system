package rw.auca.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.complaint.model.Complainant;

import java.util.List;
import java.util.UUID;

@Repository
public interface ComplainantRepository extends JpaRepository<Complainant, UUID> {

    // B1: derived query, ignores letter case, sorted by lastName A to Z
    List<Complainant> findByOrganisationIgnoreCaseOrderByLastNameAsc(String organisation);

    // C4: JPQL, each complainant of a support agent listed once, path navigation
    @Query("SELECT DISTINCT c.complainant FROM Complaint c "
            + "WHERE c.supportAgent.id = :supportAgentId")
    List<Complainant> findBySupportAgentId(@Param("supportAgentId") UUID supportAgentId);
}
