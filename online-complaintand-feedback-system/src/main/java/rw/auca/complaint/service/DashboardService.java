package rw.auca.complaint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.complaint.model.ComplaintStatus;
import rw.auca.complaint.repository.ComplainantRepository;
import rw.auca.complaint.repository.ComplaintRepository;
import rw.auca.complaint.repository.FeedbackRepository;
import rw.auca.complaint.repository.HelpDeskRepository;
import rw.auca.complaint.repository.SupportAgentRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ComplainantRepository complainantRepository;

    @Autowired
    private SupportAgentRepository supportAgentRepository;

    @Autowired
    private HelpDeskRepository helpDeskRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("complainants", complainantRepository.count());
        summary.put("supportAgents", supportAgentRepository.count());
        // reuse C2: the free help desks
        summary.put("freeHelpDesks", (long) helpDeskRepository.findFreeHelpDesks().size());
        summary.put("complaintsToday", complaintRepository.countByFiledDate(LocalDate.now()));
        summary.put("open", complaintRepository.countByStatus(ComplaintStatus.OPEN));
        summary.put("feedbacks", feedbackRepository.count());
        return summary;
    }
}
