package com.bugtracker.the_bugtracker.Controllers;

import com.bugtracker.the_bugtracker.Configs.SecurityUser;
import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Enums.Action;
import com.bugtracker.the_bugtracker.Models.*;
import com.bugtracker.the_bugtracker.Repositories.ActivityRepository;
import com.bugtracker.the_bugtracker.Repositories.ApprovalQueueRepository;
import com.bugtracker.the_bugtracker.Repositories.UserRepository;
import com.bugtracker.the_bugtracker.Services.ActivityService;
import com.bugtracker.the_bugtracker.Services.BugService;
import com.bugtracker.the_bugtracker.Services.PlatformService;
import com.bugtracker.the_bugtracker.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("bug")
public class BugController {


    @Autowired
    BugService bugService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PlatformService platformService;

    @Autowired
    ActivityService activityService;

    @Autowired
    ApprovalQueueRepository approvalQueueRepository;

    @Autowired
    ActivityRepository activityRepository;

    Boolean isUserAssigned = false;

    @GetMapping("")
    public String displayBugs(Model model) {
        List<Bug> bugs = bugService.bugList();
        model.addAttribute("bugs", bugs);
        return "bugs/list-bugs";
    }


    @GetMapping("/username")
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }


    @GetMapping("/new")
    public String newBugForm(Model model) {
        Bug aBug = new Bug();
        List<Platforms> allPlatforms = platformService.platformsList();//Create API
        model.addAttribute("allPlatforms", allPlatforms);
        model.addAttribute("bug", aBug);
        return "bugs/new-bugs";
    }

    @PostMapping("/save")
    public String createBug(Bug bug, @AuthenticationPrincipal SecurityUser userDetails){


        String userEmail=userDetails.getUsername();
        User user = userRepository.getByEmail(userEmail);
        isUserAssigned=false;
        bug.setCreatedBy(user.getFirstName()+ " " + user.getLastName());
        bugService.create(bug);
        return "redirect:/bug/";
    }


    @GetMapping("/edit/{id}")
    public String editBug(@PathVariable Integer id, Model model) throws UserNotFoundException {
        Bug existingBug = bugService.get(id);
        List<Platforms> platformsList = platformService.platformsList();
        List<User> listUserByRole = userService.getUserByRoleId(2);
        model.addAttribute("developers", listUserByRole);
        model.addAttribute("allPlatforms", platformsList);
        model.addAttribute("bug", existingBug);
        return "bugs/edit_bug";
    }

    @PostMapping("{id}")
    public String updateBug(@PathVariable Integer id,
                            @ModelAttribute("bug") Bug bug,
                            @AuthenticationPrincipal SecurityUser userDetails) throws UserNotFoundException {


        Bug existingBug = bugService.get(id);
        existingBug.setLabel(bug.getLabel());
        existingBug.setSeverity(bug.getSeverity());
        existingBug.setBugReview(bug.getBugReview());
        existingBug.setBugTreatmentStage(bug.getBugTreatmentStage());
        existingBug.setLastUpdate(LocalDate.now());
        existingBug.setPlatformses(bug.getPlatformses());
        existingBug.setProgressStatus(bug.getProgressStatus());
        existingBug.setAssignedDate(String.valueOf(LocalDate.now()));
        System.out.println("isUserAssigned for normal edition " + isUserAssigned);
        existingBug.setUserAssignedToBug(assignmentResolution(bug, existingBug, userDetails));
        bugService.updateBug(existingBug);
        return "redirect:/bug/";
    }


    @GetMapping("/delete/{id}")
    public String deleteBug(@PathVariable Integer id) throws UserNotFoundException {
        bugService.deleteBug(id);
        return "redirect:/bug";
    }



    ///////////////////////////////////CONTROLLER UTILITY METHODS/////////////////////////////////////////

    public User assignmentResolution(Bug bug, Bug existingBug, SecurityUser userDetails) {
        User assignedUser = null;
        String userFullName = userDetails.getFullName();
        if (isUserAssigned==false ) {
            assignedUser = bugAssignment(bug, existingBug, userDetails);
            System.out.println("isUserAssigned for Assignment= "+ isUserAssigned);
            isUserAssigned=true;
        } else if(isUserAssigned==true && bug.getApprovedForReassignment()==true){
            System.out.println("isUserAssigned for reassignment = "+ true);
            assignedUser = bugReassignment(bug, existingBug, userDetails);
        }

        else if(isUserAssigned==true && bug.getApprovedForReassignment()==false){
            System.out.println(bug.getApprovedForReassignment());
            System.out.println("Sent to admin for approval");

            ApprovalQueue reassignmentApprovalQueue = new ApprovalQueue(new Date(), "REASSIGNMENT",
                    userFullName, false, String.format("%s", bug.userAssignedToBug));

            approvalQueueRepository.save(reassignmentApprovalQueue);
        }


        return assignedUser;
    }
    public User bugReassignment(Bug bug, Bug existingBug, SecurityUser userDetails) {

        String userFullName = userDetails.getFullName();


        bug.setUserAssignedToBug(bug.getUserAssignedToBug());
        Activity reassignmentActivity = new Activity(
                existingBug.getCreatedBy(),
                bug.getReportDate(),
                "Initially assigned to " + existingBug.userAssignedToBug,
                String.format("Bug %s created by %s was reassigned to %s ", bug.getLabel(), existingBug.getCreatedBy(), bug.userAssignedToBug),
                new Date(),
                userFullName,
                String.valueOf(bug.userAssignedToBug)
        );
        reassignmentActivity.setBugActivity(existingBug);
        reassignmentActivity.setAction(Action.BUG_REASSIGNMENT);
        activityRepository.save(reassignmentActivity);

        return bug.getUserAssignedToBug();
    }

    public User bugAssignment(Bug bug, Bug existingBug,  SecurityUser userDetails) {

        String userFullName=userDetails.getFullName();

        bug.setUserAssignedToBug(bug.getUserAssignedToBug());
        String assignedTo = String.valueOf(bug.getUserAssignedToBug());
        Activity assignmentActivity = new Activity(
                existingBug.getCreatedBy(),
                bug.getReportDate(),
                null,
                String.format("Bug %s created by %s was assigned to %s ",
                        bug.getLabel(), existingBug.getCreatedBy(), bug.userAssignedToBug),
                userFullName,
                assignedTo,
                bug.getBugTreatmentStage(),
                new Date()
        );
        assignmentActivity.setBugActivity(existingBug);
        assignmentActivity.setAction(Action.BUG_ASSIGNMENT);
        activityRepository.save(assignmentActivity);
        return bug.getUserAssignedToBug();
    }


    @GetMapping("activityonbug/{id}")
    public String myDashboard(@PathVariable Integer id,
                              @ModelAttribute("bug") Bug bug,
                              Model model)  {

        List<Activity> listOfActivitiesCarriedOutOnBug = activityService.getActivityByBugId(id);
        System.out.println(listOfActivitiesCarriedOutOnBug);
        model.addAttribute("bugactivitylist", listOfActivitiesCarriedOutOnBug);
        return "/bugs/bug_activity_table";
    }


}