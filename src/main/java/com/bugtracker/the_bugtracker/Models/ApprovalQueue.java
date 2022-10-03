package com.bugtracker.the_bugtracker.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class ApprovalQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer approvalQueueId;

    @Column
    private Date dateOfSubmission;

    @Column
    private String actionToBeApproved;

    @Column
    private String submittedBy;

    @Column
    private Date dateOfApproval;


    private boolean isApproved;

    @Column
    private String reassigningTo;


    @OneToOne
    @JoinColumn(name = "bug_approval")
    private Bug bugApprovalRelationship;


    public ApprovalQueue(Date dateOfSubmission, String actionToBeApproved,
                         String submittedBy, boolean isApproved, String reassigningTo) {

        this.dateOfSubmission = dateOfSubmission;
        this.actionToBeApproved = actionToBeApproved;
        this.submittedBy = submittedBy;
        this.isApproved = isApproved;
        this.reassigningTo = reassigningTo;

    }

    public ApprovalQueue() {

    }


    public Integer getApprovalQueueId() {
        return approvalQueueId;
    }

    public void setApprovalQueueId(Integer approvalQueueId) {
        this.approvalQueueId = approvalQueueId;
    }

    public Date getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void setDateOfSubmission(Date dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

    public String getActionToBeApproved() {
        return actionToBeApproved;
    }

    public void setActionToBeApproved(String actionToBeApproved) {
        this.actionToBeApproved = actionToBeApproved;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Date getDateOfApproval() {
        return dateOfApproval;
    }

    public void setDateOfApproval(Date dateOfApproval) {
        this.dateOfApproval = dateOfApproval;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        this.isApproved = approved;
    }

    public String getReassigningTo() {
        return reassigningTo;
    }

    public void setReassigningTo(String reassigningTo) {
        this.reassigningTo = reassigningTo;
    }


    public Bug getBugApprovalRelationship() {
        return bugApprovalRelationship;
    }

    public void setBugApprovalRelationship(Bug bugApprovalRelationship) {
        this.bugApprovalRelationship = bugApprovalRelationship;
    }
}
