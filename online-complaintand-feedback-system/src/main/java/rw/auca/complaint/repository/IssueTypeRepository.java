package rw.auca.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.auca.complaint.model.IssueType;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueTypeRepository extends JpaRepository<IssueType, UUID> {

    // C3: JPQL, issue types that no support agent holds (IS EMPTY, no count)
    @Query("SELECT i FROM IssueType i WHERE i.agents IS EMPTY")
    List<IssueType> findUnusedIssueTypes();
}
