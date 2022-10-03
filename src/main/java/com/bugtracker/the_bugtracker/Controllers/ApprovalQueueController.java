package com.bugtracker.the_bugtracker.Controllers;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Models.ApprovalQueue;
import com.bugtracker.the_bugtracker.Models.Bug;
import com.bugtracker.the_bugtracker.Services.ApprovalQueueService;
import com.bugtracker.the_bugtracker.Services.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("approvalqueue")
public class ApprovalQueueController {

    @Autowired
    ApprovalQueueService approvalQueueService;

    @Autowired
    BugService bugService;


    @GetMapping("")
    public String displayQueue(Model model){
        List<ApprovalQueue> approvalQueueList = approvalQueueService.getAllPendingApproval();
        model.addAttribute("approvals", approvalQueueList);
        return "approvals/approvals";
    }


    @GetMapping("/{id}/approved/{status}")
    public String updateApprovalStatus(@PathVariable("id") Integer id, @ModelAttribute ApprovalQueue approvalQueue,
                                          @PathVariable("status") boolean approved
                                          ) throws UserNotFoundException {

        ApprovalQueue requestBeingApproved = approvalQueueService.get(id);
        Bug bug = bugService.get(requestBeingApproved.getBugApprovalRelationship().getBugId());

        System.out.println(requestBeingApproved.getBugApprovalRelationship().getBugId());
        System.out.println("Bug Approval before toggle: " + bug.getApprovedForReassignment());

        approvalQueueService.updateApprovalStatus(id, approved);
        bug.setApprovedForReassignment(true);
        System.out.println("Bug Approval after toggle: " + bug.getApprovedForReassignment());
        return "redirect:/approvalqueue";
    }
}
