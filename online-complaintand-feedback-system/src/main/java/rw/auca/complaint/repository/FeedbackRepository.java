package rw.auca.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.complaint.model.Complaint;
import rw.auca.complaint.model.Feedback;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    // E2: JPQL, all feedbacks of a complainant, newest submittedDate first
    @Query("SELECT f FROM Feedback f WHERE f.complaint.complainant.email = :email "
            + "ORDER BY f.submittedDate DESC")
    List<Feedback> findByComplainantEmail(@Param("email") String email);

    // E3: derived query, does this complaint have any feedback
    boolean existsByComplaint(Complaint complaint);
}
