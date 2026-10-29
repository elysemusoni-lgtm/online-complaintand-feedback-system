package rw.auca.complaint.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate filedDate;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;

    private String subject;

    private String description;

    @ManyToOne
    @JoinColumn(name = "complainant_id")
    private Complainant complainant;

    @ManyToOne
    @JoinColumn(name = "support_agent_id")
    private SupportAgent supportAgent;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getFiledDate() {
        return filedDate;
    }

    public void setFiledDate(LocalDate filedDate) {
        this.filedDate = filedDate;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Complainant getComplainant() {
        return complainant;
    }

    public void setComplainant(Complainant complainant) {
        this.complainant = complainant;
    }

    public SupportAgent getSupportAgent() {
        return supportAgent;
    }

    public void setSupportAgent(SupportAgent supportAgent) {
        this.supportAgent = supportAgent;
    }
}
