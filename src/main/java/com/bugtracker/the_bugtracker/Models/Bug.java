package com.bugtracker.the_bugtracker.Models;

import com.bugtracker.the_bugtracker.Enums.Severity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bug_information")
@EnableJpaAuditing
public class Bug {



    @Column
    @CreatedDate
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    @JsonIgnore
    public final LocalDate reportDate = LocalDate.now(); //DATE OF REPORT
    @Column
    public String assignedTo;
    @Column
    @CreatedDate
    @JsonIgnore
    public LocalDate lastUpdate;
    @OneToOne
    @JoinColumn(name = "user_bug_id")
    @JsonIgnore
    public User userAssignedToBug;
    @Id
    @Column(name = "bug_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bugId; //ID OF BUG
    @Column(name = "bug_name", nullable = false)
    private String label; //BUG NAME
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "approved_by")
    @JsonIgnore
    private String approvedBy;
    @Column
    @JsonIgnore
    private Date approvedDate;
    @Column
    @JsonIgnore
    private String assignedDate;
    @Column
    private String severity; //SEVERITY LEVEL
    @Column
    private Severity enumSeverity;

    @Column
    private Boolean isApprovedForReassignment=false;

    @Column
    private final String ticketId = String.format("ZBN%s%s", new Date() , bugId);

    @Column
    private String bugTreatmentStage; //ALL BUGS, OPEN BUGS, TREATED, PENDING
    @Column
    private String progressStatus;//INITIATED, APPROVED, ASSIGNED TO, REASSIGNED TO, CORRECTION COMPLETED

    //    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
//            CascadeType.REFRESH, CascadeType.PERSIST},
//            fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "platforms_bugs",
//            joinColumns = @JoinColumn(name = "bug_id"),
//            inverseJoinColumns = @JoinColumn(name = "platform_id")
//    )
//    private List<Platforms> platformses;
//
//    @ManyToOne
//    @JoinColumn(name = "bug_platform_id")
//    private Platforms platformses;
    @Column
    private String bugReview; //USERS REVIEW ON BUG TREATMENT

    @OneToOne
    @JoinColumn(name = "bug_platform_id")
    private Platforms platformses;


//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
//            CascadeType.REFRESH, CascadeType.PERSIST},
//            fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "bug_activity",
//            joinColumns = @JoinColumn(name = "bug_activity_id"),
//            inverseJoinColumns = @JoinColumn(name = "activity_bug_id")
//    )
//    private List<Activity> activities;


//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
//            CascadeType.REFRESH, CascadeType.PERSIST},
//            fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "user_bug",
//            joinColumns = @JoinColumn(name = "bug_user_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_bug_id")
//    )
//    private List<User> userAssignedToBug;


//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "bug_user_id", referencedColumnName = "user_id")
//    private User userAssignedToBug;


//    @ManyToOne
//    @JoinColumn(name = "bug_user_id")
//    private User userAssignedToBug;

    public Bug() {
    }

    public Bug(String label, String createdBy, String approvedBy, Date approvedDate, String assignedTo, String assignedDate, LocalDate lastUpdate, String severity, String bugTreatmentStage, String progressStatus, String bugReview, List<Activity> activities) {


        this.label = label;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
        this.approvedDate = approvedDate;
        this.assignedTo = assignedTo;
        this.assignedDate = assignedDate;
        this.lastUpdate = lastUpdate;
        this.severity = severity;
        this.bugTreatmentStage = bugTreatmentStage;
        this.progressStatus = progressStatus;
        this.bugReview = bugReview;
    }

    public Bug(String label, String createdBy, String approvedBy, Date approvedDate, String assignedTo, String assignedDate, LocalDate lastUpdate, String severity, String bugTreatmentStage, String progressStatus, String bugReview, Platforms platformses, Severity enumSeverity) {
        this.label = label;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
        this.approvedDate = approvedDate;
        this.assignedTo = assignedTo;
        this.assignedDate = assignedDate;
        this.lastUpdate = lastUpdate;
        this.severity = severity;
        this.bugTreatmentStage = bugTreatmentStage;
        this.progressStatus = progressStatus;
        this.bugReview = bugReview;
        this.platformses = platformses;
    }

    //

    public Integer getBugId() {
        return bugId;
    }

    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String bugName) {
        this.label = bugName;
    }

    public String getCreatedBy(String bugName) {
        return createdBy;
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

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getBugTreatmentStage() {
        return bugTreatmentStage;
    }

    public void setBugTreatmentStage(String bugTreatmentStage) {
        this.bugTreatmentStage = bugTreatmentStage;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getBugReview() {
        return bugReview;
    }

    public void setBugReview(String bugReview) {
        this.bugReview = bugReview;
    }

    public Platforms getPlatformses() {
        return platformses;
    }

    public void setPlatformses(Platforms platformses) {
        this.platformses = platformses;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

//    public User getUserAssignedToBug() {
//        return userAssignedToBug;
//    }
//
//    public void setUserAssignedToBug(User userAssignedToBug) {
//        this.userAssignedToBug = userAssignedToBug;
//    }

//
//    public List<User> getUserAssignedToBug() {
//        return userAssignedToBug;
//    }
//
//    public void setUserAssignedToBug(List<User> userAssignedToBug) {
//        this.userAssignedToBug = userAssignedToBug;
//    }

    public User getUserAssignedToBug() {
        return userAssignedToBug;
    }

    public void setUserAssignedToBug(User userAssignedToBug) {
        this.userAssignedToBug = userAssignedToBug;
    }


    public Severity getEnumSeverity() {
        return enumSeverity;
    }

    public void setEnumSeverity(Severity enumSeverity) {
        this.enumSeverity = enumSeverity;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Boolean getApprovedForReassignment() {
        return isApprovedForReassignment;
    }

    public void setApprovedForReassignment(Boolean approvedForReassignment) {
        isApprovedForReassignment = approvedForReassignment;
    }
}