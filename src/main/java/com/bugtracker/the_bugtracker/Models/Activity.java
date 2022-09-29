package com.bugtracker.the_bugtracker.Models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Integer activityId;

    @Column
    public String createdBy;//User who sent the complaint


    @Column
    public LocalDate createdDt;//Date he sent it


    @Column
    public String description;//The platform he sent it on and his complaint


    @Column
    public String approvedBy;

    @Column
    public Date approvedDate;

    @Column
    public String assignedTo;

    @Column
    public String treatmentStage;

    @Column
    public String progressStatus;

    @Column
    public String redirectedTo;

    @Column
    public Date actionDate;



    public Activity() {
    }

    public Activity(String description){
        this.description=description;
    }



//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
//            CascadeType.REFRESH, CascadeType.PERSIST},
//            fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "bug_activity",
//            joinColumns = @JoinColumn(name = "activity_bug_id"),
//            inverseJoinColumns = @JoinColumn(name = "bug_activity_id")
//    )
//    private List<Bug> bugActivity;




    //GETTERS AND SETTERS

    //FOR BUG CREATION
    public Activity(String description, String createdBy, LocalDate createdDt, String approvedBy,
                    Date approvedDate, String assignedTo, String treatmentStage, String progressStatus) {
        this.description=description;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.approvedBy = approvedBy;
        this.approvedDate=approvedDate;
        this.assignedTo=assignedTo;
        this.treatmentStage = treatmentStage;
        this.progressStatus = progressStatus;
    }

    //FOR BUG DELETION
    public Activity(String description, String treatmentStage, Date actionDate, String createdBy) {
        this.description = description;
        this.treatmentStage = treatmentStage;
        this.actionDate = actionDate;
    }

    //FOR BUG UPDATE/ASSIGNMENT


    public Activity(String createdBy, LocalDate createdDt, String description,
                    Date approvedDate, String assignedTo, String treatmentStage,
                    Date actionDate) {
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.description = description;
        this.approvedDate = approvedDate;
        this.assignedTo = assignedTo;
        this.treatmentStage = treatmentStage;
        this.actionDate = actionDate;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(LocalDate createdDt) {
        this.createdDt = createdDt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getTreatmentStage() {
        return treatmentStage;
    }

    public void setTreatmentStage(String treatmentStage) {
        this.treatmentStage = treatmentStage;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getRedirectedTo() {
        return redirectedTo;
    }

    public void setRedirectedTo(String redirectedTo) {
        this.redirectedTo = redirectedTo;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
}
