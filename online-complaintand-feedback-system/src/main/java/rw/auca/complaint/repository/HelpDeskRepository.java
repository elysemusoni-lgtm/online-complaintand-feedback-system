package rw.auca.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.auca.complaint.model.HelpDesk;

import java.util.List;
import java.util.UUID;

@Repository
public interface HelpDeskRepository extends JpaRepository<HelpDesk, UUID> {

    // C2: JPQL, help desks that no support agent occupies, sorted by deskNumber
    @Query("SELECT h FROM HelpDesk h "
            + "WHERE NOT EXISTS (SELECT a FROM SupportAgent a WHERE a.helpDesk = h) "
            + "ORDER BY h.deskNumber")
    List<HelpDesk> findFreeHelpDesks();
}
