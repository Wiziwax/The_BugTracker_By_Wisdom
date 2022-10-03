package com.bugtracker.the_bugtracker.RestControllers;

import com.bugtracker.the_bugtracker.Models.ApprovalQueue;
import com.bugtracker.the_bugtracker.Services.ApprovalQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/approvalqueue")
public class ApprovalQueueRestController {

    @Autowired
    ApprovalQueueService approvalQueueService;

    @GetMapping("")
    public List<ApprovalQueue> displayApprovalQueue(){
        return approvalQueueService.getAllPendingApproval();
    }
}
