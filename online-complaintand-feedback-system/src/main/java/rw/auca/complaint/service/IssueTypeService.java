package rw.auca.complaint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.complaint.model.IssueType;
import rw.auca.complaint.repository.IssueTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IssueTypeService {

    @Autowired
    private IssueTypeRepository issueTypeRepository;

    public String save(IssueType issueType) {
        issueTypeRepository.save(issueType);
        return "Issue type saved successfully";
    }

    public List<IssueType> getAll() {
        return issueTypeRepository.findAll();
    }

    public Optional<IssueType> getById(UUID id) {
        return issueTypeRepository.findById(id);
    }

    public String update(UUID id, IssueType newData) {
        Optional<IssueType> found = issueTypeRepository.findById(id);
        if (found.isEmpty()) {
            return "The issue type with that id does not exist";
        }
        IssueType issueType = found.get();
        issueType.setName(newData.getName());
        issueTypeRepository.save(issueType);
        return "Issue type updated successfully";
    }

    public String delete(UUID id) {
        if (!issueTypeRepository.existsById(id)) {
            return "The issue type with that id does not exist";
        }
        issueTypeRepository.deleteById(id);
        return "Issue type deleted successfully";
    }
}
