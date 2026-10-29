package rw.auca.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // D1: derived query, does this support agent already have a complaint on that date that is not WITHDRAWN
    boolean existsBySupportAgentIdAndFiledDateAndStatusNot(UUID supportAgentId, LocalDate filedDate, ComplaintStatus status);

    // F1: JPQL, one row per status with its count, a status with zero complaints does not appear
    @Query("SELECT c.status, COUNT(c) FROM Complaint c GROUP BY c.status")
    List<Object[]> countByStatus();

    // F2: JPQL UPDATE, withdraw every complaint of a support agent on a date except RESOLVED ones
    @Modifying
    @Query("UPDATE Complaint c SET c.status = :newStatus "
            + "WHERE c.supportAgent.id = :supportAgentId AND c.filedDate = :date AND c.status <> :resolved")
    int cancelDay(@Param("supportAgentId") UUID supportAgentId,
                  @Param("date") LocalDate date,
                  @Param("newStatus") ComplaintStatus newStatus,
                  @Param("resolved") ComplaintStatus resolved);
}
