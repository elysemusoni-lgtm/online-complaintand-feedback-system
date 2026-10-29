package rw.auca.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.complaint.model.HelpDesk;
import rw.auca.complaint.model.SupportAgent;

import java.util.List;
import java.util.UUID;

@Repository
public interface SupportAgentRepository extends JpaRepository<SupportAgent, UUID> {

    // C1: JPQL, join through the issueTypes list, letter case does not matter
    @Query("SELECT DISTINCT a FROM SupportAgent a JOIN a.issueTypes t "
            + "WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<SupportAgent> findByIssueTypeName(@Param("name") String name);

    // D4: derived query, is this help desk already occupied by a support agent
    boolean existsByHelpDesk(HelpDesk helpDesk);
}
