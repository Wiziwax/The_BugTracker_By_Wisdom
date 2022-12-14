package com.bugtracker.the_bugtracker.Services;

import com.bugtracker.the_bugtracker.Configs.SecurityUser;
import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Enums.Action;
import com.bugtracker.the_bugtracker.Enums.Severity;
import com.bugtracker.the_bugtracker.Models.*;
import com.bugtracker.the_bugtracker.Repositories.ActivityRepository;
import com.bugtracker.the_bugtracker.Repositories.BugRepository;
import com.bugtracker.the_bugtracker.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BugService {

    @Autowired
    BugRepository bugRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserRepository userRepository;


    //LIST ALL BUGS
    public List<Bug> bugList(){
        return bugRepository.findAll();
    }

//
    public String[] findBugNames(){
        return getStrings();
    }


    private String[] getStrings() {
        List<Bug> bugNames = bugRepository.findAll();
        String theNames[]=new String[bugNames.size()];
        for(int i=0; i<bugNames.size();i++){
            theNames[i] = bugNames.get(i).getLabel();
        }
        return theNames;
    }


    public String[] findBugReviews(){
        return getStrings();
    }

    //PAGEABLE LIST
    public Page<Bug> listAll(Pageable pageable) {
        return bugRepository.findAll(pageable);
    }



    public String getSignedInUsername(@AuthenticationPrincipal SecurityUser userDetails){
        String userEmail = userDetails.getUsername();
        User user = userRepository.getByEmail(userEmail);
        return user.getFirstName() + " "+ user.getLastName();
    }


    //CREATE BUG
    public void create(Bug bug){

        Bug buggy = bugRepository.save(bug);

        //todo insert a new activity
        Activity activity = new Activity(
                String.format("Bug created by %s with description %s",buggy.getCreatedBy(), buggy.getBugReview()),
                buggy.getCreatedBy(),
                buggy.getReportDate(),
                buggy.getApprovedBy(),
                buggy.getApprovedDate(),
                buggy.getAssignedTo(),
                buggy.getTicketId(),
                buggy.getBugTreatmentStage(),
                buggy.getProgressStatus());

        activity.setBugActivity(buggy);
        activity.setAction(Action.BUG_CREATION);
        buggy.setEnumSeverity(Severity.LOW);
        activityRepository.save(activity);

    }

    //GET BY ID FUNCTION
    public Bug get(Integer id) throws UserNotFoundException {

        try {
            return bugRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            throw new UserNotFoundException("Could not find any bug with ID " + id);
        }
    }

    //SEARCH RESULTS
    public Page<Bug> listAllSearches(String keyword, Pageable pageable) {

        if (keyword != null) {
            return bugRepository.search(keyword, pageable);
        }
        return bugRepository.findAll(pageable);
    }

    //DELETE BUG BY ID
    public void deleteBug(Integer id) throws UserNotFoundException {
        Integer countById = bugRepository.countByBugId(id);
                if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }

        Bug bug= bugRepository.getById(id);
        bugRepository.deleteById(id);

        System.out.println(bug.getLabel());
        Activity deleteActivity = new Activity(String.format
                ("Bug with Id %d with description %s initiated by %s, deleted", id ,bug.getBugReview(), bug.getCreatedBy()),
                "DELETED",
                new Date());
        deleteActivity.setBugActivity(bug);
        deleteActivity.setAction(Action.BUG_DELETION);
        activityRepository.save(deleteActivity);
    }


    //UPDATE BUG INFORMATION
    public ResponseEntity<Bug> updateBug(Bug bug) {
        return new ResponseEntity<>(bugRepository.save(bug),HttpStatus.ACCEPTED);
    }


    //UPDATE BUG REST CONTROLLER
    @Transactional
    public void updateBugRestController(
            Integer bugId,
            String bugName,
            String severity,
            String bugReview,
            String treatmentStage,
            Platforms platforms,
            String progressStatus,
            User userAssignedToBug,
            Severity enumSeverity){

        Bug existingBug =
                bugRepository.findById(bugId).orElseThrow(()->new IllegalStateException(
                        "student with id " + bugId + " does not exist"));

        existingBug.setLabel(bugName);
        existingBug.setSeverity(severity);
        existingBug.setBugReview(bugReview);
        existingBug.setBugTreatmentStage(treatmentStage);
        existingBug.setLastUpdate(LocalDate.now());
        existingBug.setPlatformses(platforms);
        existingBug.setProgressStatus(progressStatus);
        existingBug.setUserAssignedToBug(userAssignedToBug);
        existingBug.setAssignedDate(String.valueOf(LocalDate.now()));
        existingBug.setEnumSeverity(enumSeverity);

    }


    //GET BUGS ASSIGNED T0 USER
    public List<Bug> getBugByUserId(int userId){
        return bugRepository.theAssignedBugs(userId);
    }
}
