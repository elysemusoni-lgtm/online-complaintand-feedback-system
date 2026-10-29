package rw.auca.complaint.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "support_agent")
public class SupportAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    @ManyToOne
    @JoinColumn(name = "help_desk_id")
    private HelpDesk helpDesk;

    @ManyToMany
    @JoinTable(
            name = "support_agent_issue_type",
            joinColumns = @JoinColumn(name = "support_agent_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_type_id")
    )
    private List<IssueType> issueTypes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HelpDesk getHelpDesk() {
        return helpDesk;
    }

    public void setHelpDesk(HelpDesk helpDesk) {
        this.helpDesk = helpDesk;
    }

    public List<IssueType> getIssueTypes() {
        return issueTypes;
    }

    public void setIssueTypes(List<IssueType> issueTypes) {
        this.issueTypes = issueTypes;
    }
}
