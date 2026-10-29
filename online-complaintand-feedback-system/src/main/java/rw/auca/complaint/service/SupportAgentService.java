package rw.auca.complaint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.complaint.model.SupportAgent;
import rw.auca.complaint.repository.SupportAgentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupportAgentService {

    @Autowired
    private SupportAgentRepository supportAgentRepository;

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

    public String delete(UUID id) {
        if (!supportAgentRepository.existsById(id)) {
            return "The support agent with that id does not exist";
        }
        supportAgentRepository.deleteById(id);
        return "Support agent deleted successfully";
    }
}
