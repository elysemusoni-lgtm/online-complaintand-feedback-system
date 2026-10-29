package rw.auca.complaint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.complaint.model.HelpDesk;
import rw.auca.complaint.model.IssueType;
import rw.auca.complaint.model.SupportAgent;
import rw.auca.complaint.repository.HelpDeskRepository;
import rw.auca.complaint.repository.IssueTypeRepository;
import rw.auca.complaint.repository.SupportAgentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupportAgentService {

    @Autowired
    private SupportAgentRepository supportAgentRepository;

    @Autowired
    private IssueTypeRepository issueTypeRepository;

    @Autowired
    private HelpDeskRepository helpDeskRepository;

    public String save(SupportAgent supportAgent) {
        supportAgentRepository.save(supportAgent);
        return "Support agent saved successfully";
    }

    public List<SupportAgent> getAll() {
        return supportAgentRepository.findAll();
    }

    // C1
    public List<SupportAgent> getByIssueType(String name) {
        return supportAgentRepository.findByIssueTypeName(name);
    }

    public Optional<SupportAgent> getById(UUID id) {
        return supportAgentRepository.findById(id);
    }

    public String update(UUID id, SupportAgent newData) {
        Optional<SupportAgent> found = supportAgentRepository.findById(id);
        if (found.isEmpty()) {
            return "The support agent with that id does not exist";
        }
        SupportAgent supportAgent = found.get();
        supportAgent.setFirstName(newData.getFirstName());
        supportAgent.setLastName(newData.getLastName());
        supportAgent.setEmail(newData.getEmail());
        supportAgent.setHelpDesk(newData.getHelpDesk());
        supportAgentRepository.save(supportAgent);
        return "Support agent updated successfully";
    }

    // D3: add one issue type to a support agent and keep the ones already there
    @Transactional
    public String addIssueType(UUID supportAgentId, UUID issueTypeId) {
        Optional<SupportAgent> foundAgent = supportAgentRepository.findById(supportAgentId);
        if (foundAgent.isEmpty()) {
            return "The support agent with that id does not exist";
        }
        Optional<IssueType> foundType = issueTypeRepository.findById(issueTypeId);
        if (foundType.isEmpty()) {
            return "That issue type does not exist";
        }
        SupportAgent supportAgent = foundAgent.get();
        IssueType issueType = foundType.get();
        if (supportAgent.getIssueTypes().contains(issueType)) {
            return "The support agent already has that issue type";
        }
        supportAgent.getIssueTypes().add(issueType);
        supportAgentRepository.save(supportAgent);
        return "Issue type added to the support agent";
    }

    // D4: move a support agent to another help desk
    public String changeHelpDesk(UUID supportAgentId, int deskNumber) {
        Optional<SupportAgent> foundAgent = supportAgentRepository.findById(supportAgentId);
        if (foundAgent.isEmpty()) {
            return "The support agent with that id does not exist";
        }
        Optional<HelpDesk> foundDesk = helpDeskRepository.findByDeskNumber(deskNumber);
        if (foundDesk.isEmpty()) {
            return "The help desk with that deskNumber does not exist";
        }
        HelpDesk helpDesk = foundDesk.get();
        if (supportAgentRepository.existsByHelpDesk(helpDesk)) {
            return "That help desk is taken by another support agent";
        }
        SupportAgent supportAgent = foundAgent.get();
        supportAgent.setHelpDesk(helpDesk);
        supportAgentRepository.save(supportAgent);
        return "Help desk changed successfully";
    }

    public String delete(UUID id) {
        if (!supportAgentRepository.existsById(id)) {
            return "The support agent with that id does not exist";
        }
        supportAgentRepository.deleteById(id);
        return "Support agent deleted successfully";
    }
}
