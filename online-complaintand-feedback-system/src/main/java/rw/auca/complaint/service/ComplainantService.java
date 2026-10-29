package rw.auca.complaint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.complaint.model.Complainant;
import rw.auca.complaint.repository.ComplainantRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComplainantService {

    @Autowired
    private ComplainantRepository complainantRepository;

    public String save(Complainant complainant) {
        complainantRepository.save(complainant);
        return "Complainant saved successfully";
    }

    public List<Complainant> getAll() {
        return complainantRepository.findAll();
    }

    public Optional<Complainant> getById(UUID id) {
        return complainantRepository.findById(id);
    }

    public String update(UUID id, Complainant newData) {
        Optional<Complainant> found = complainantRepository.findById(id);
        if (found.isEmpty()) {
            return "The complainant with that id does not exist";
        }
        Complainant complainant = found.get();
        complainant.setFirstName(newData.getFirstName());
        complainant.setLastName(newData.getLastName());
        complainant.setEmail(newData.getEmail());
        complainant.setPhone(newData.getPhone());
        complainant.setOrganisation(newData.getOrganisation());
        complainantRepository.save(complainant);
        return "Complainant updated successfully";
    }

    public String delete(UUID id) {
        if (!complainantRepository.existsById(id)) {
            return "The complainant with that id does not exist";
        }
        complainantRepository.deleteById(id);
        return "Complainant deleted successfully";
    }
}
