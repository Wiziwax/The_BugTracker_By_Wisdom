package com.bugtracker.the_bugtracker.Services;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Models.Activity;
import com.bugtracker.the_bugtracker.Models.Bug;
import com.bugtracker.the_bugtracker.Models.Platforms;
import com.bugtracker.the_bugtracker.Models.User;
import com.bugtracker.the_bugtracker.Repositories.ActivityRepository;
import com.bugtracker.the_bugtracker.Repositories.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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


    //LIST ALL BUGS
    public List<Bug> bugList(){
        return bugRepository.findAll();
    }

    //LIST JUST THE BUG NAMES
    public List<Bug> justBugs(){
        return bugRepository.justBugs();
    }

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

    //CREATE BUG
    public void create(Bug bug){
        Bug buggy = bugRepository.save(bug);
        //todo insert a new activity
        Activity activity = new Activity(
                String.format("Bug created by %s with description %s",buggy.getLabel(),
                        buggy.getBugReview()),
                buggy.getLabel(),
                buggy.getReportDate(),
//                String.format("Bug created with description %s on the %s platform(s)", buggy.getBugReview(), buggy.getPlatformses()),
                buggy.getApprovedBy(),
                buggy.getApprovedDate(),
                buggy.getAssignedTo(),
                buggy.getBugTreatmentStage(),
                buggy.getProgressStatus());
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
                ("Bug with Id %d with description %s initiated by %s, deleted", id ,bug.getBugReview(),
                        bug.getLabel()), "DELETED", new Date(), bug.getLabel());
        activityRepository.save(deleteActivity);

    }

    //UPDATE BUG INFORMATION
    public ResponseEntity<Bug> updateBug(Bug bug) {
        return new ResponseEntity<>( bugRepository.save(bug),HttpStatus.ACCEPTED);
    }

    //UPDATE BUG REST CONTROLLER
    @Transactional
    public void updateBugRestController(
            Integer bugId, String bugName, String severity, String bugReview,
            String treatmentStage, Platforms platforms, String progressStatus,
            User userAssignedToBug){

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

    }

    //GET BUGS ASSIGNED T0 USER
    public List<Bug> getBugByUserId(int userId){
        return bugRepository.theAssignedBugs(userId);
    }
}
