package rw.auca.complaint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.complaint.model.HelpDesk;
import rw.auca.complaint.repository.HelpDeskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HelpDeskService {

    @Autowired
    private HelpDeskRepository helpDeskRepository;

    public String save(HelpDesk helpDesk) {
        helpDeskRepository.save(helpDesk);
        return "Help desk saved successfully";
    }

    public List<HelpDesk> getAll() {
        return helpDeskRepository.findAll();
    }

    // C2
    public List<HelpDesk> getFree() {
        return helpDeskRepository.findFreeHelpDesks();
    }

    public Optional<HelpDesk> getById(UUID id) {
        return helpDeskRepository.findById(id);
    }

    public String update(UUID id, HelpDesk newData) {
        Optional<HelpDesk> found = helpDeskRepository.findById(id);
        if (found.isEmpty()) {
            return "The help desk with that id does not exist";
        }
        HelpDesk helpDesk = found.get();
        helpDesk.setDeskNumber(newData.getDeskNumber());
        helpDesk.setName(newData.getName());
        helpDeskRepository.save(helpDesk);
        return "Help desk updated successfully";
    }

    public String delete(UUID id) {
        if (!helpDeskRepository.existsById(id)) {
            return "The help desk with that id does not exist";
        }
        helpDeskRepository.deleteById(id);
        return "Help desk deleted successfully";
    }
}
