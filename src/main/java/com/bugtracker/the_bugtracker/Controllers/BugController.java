package com.bugtracker.the_bugtracker.Controllers;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Models.*;
import com.bugtracker.the_bugtracker.Repositories.ActivityRepository;
import com.bugtracker.the_bugtracker.Services.ActivityService;
import com.bugtracker.the_bugtracker.Services.BugService;
import com.bugtracker.the_bugtracker.Services.PlatformService;
import com.bugtracker.the_bugtracker.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    UserService userService;

    @Autowired
    PlatformService platformService;

    @Autowired
    ActivityService activityService;

    @Autowired
    ActivityRepository activityRepository;



    @GetMapping("")
    public String displayBugs(Model model){
        List<Bug> bugs = bugService.bugList();
        model.addAttribute("bugs", bugs);
        return "bugs/list-bugs";
    }

//    @GetMapping("")
//    public String viewHomePage(Model model) {
//        return viewPage(model, 1);
//    }
//
//    @GetMapping("page/{pageNum}")
//    public String viewPage(Model model,
//                           @PathVariable(name = "pageNum") int pageNum) {
//
//        Page<Bug> page = bugService.listAll(pageNum,5);
//
//        List<Bug> listBugs = page.getContent();
//
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("listBugs", listBugs);
//
//        return "bugs/list-bugs";
//    }


//    @GetMapping("assignmenttable")
//    public String displayAssignmentTable(Model model){
//        List<Bug> bugs = bugService.bugList();
//        model.addAttribute("bugs", bugs);
//        return "bugs/bugassignmenttable";
//    }


    @GetMapping("new")
    public String newBugForm(Model model){
        Bug aBug=new Bug();
        List<Platforms>allPlatforms = platformService.platformsList();//Create API
        model.addAttribute("allPlatforms", allPlatforms);
        model.addAttribute("bug", aBug);
        return "bugs/new-bugs";
    }

    @PostMapping("save")
    public String createBug(Bug bug, Activity activity, Model model){
        bugService.create(bug);

        return "redirect:/bug/";
    }

    @GetMapping("edit/{id}")
    public String editBug(@PathVariable Integer id, Model model) throws UserNotFoundException {
        Bug existingBug = bugService.get(id);
        List<Platforms> platformsList = platformService.platformsList();
        List<User> listUserByRole = userService.getUserByRoleId(2);


        System.out.println(listUserByRole);
        System.out.println(platformsList);
        model.addAttribute("developers", listUserByRole);
        model.addAttribute("allPlatforms", platformsList);
        model.addAttribute("bug", existingBug);
        return "bugs/edit_bug";
    }

    @PostMapping("{id}")
    public String updateBug(@PathVariable Integer id,
                                 @ModelAttribute("bug") Bug bug,
                                 Model model) throws UserNotFoundException {
        Bug existingBug = bugService.get(id);
        existingBug.setLabel(bug.getLabel());
        existingBug.setSeverity(bug.getSeverity());
        existingBug.setBugReview(bug.getBugReview());
        existingBug.setBugTreatmentStage(bug.getBugTreatmentStage());
        existingBug.setLastUpdate(LocalDate.now());
        existingBug.setPlatformses(bug.getPlatformses());
        existingBug.setProgressStatus(bug.getProgressStatus());
        existingBug.setUserAssignedToBug(bug.getUserAssignedToBug());
        existingBug.setAssignedDate(String.valueOf(LocalDate.now()));
        String assignedTo= String.valueOf(existingBug.getUserAssignedToBug());

        Activity editActivity= new Activity(
                existingBug.getLabel(),
                existingBug.getReportDate(),
                String.format("Bug %s created by %s was assigned to %s ", existingBug.getBugReview(), existingBug.getLabel(), existingBug.userAssignedToBug),
                new Date(),
                assignedTo,
                existingBug.getBugTreatmentStage(),
                new Date()
                );

        bugService.updateBug(existingBug);
        activityRepository.save(editActivity);

        return "redirect:/bug/";
    }

    @GetMapping("/delete/{id}")
    public String deleteBug(@PathVariable Integer id) throws UserNotFoundException {
        bugService.deleteBug(id);
        return "redirect:/bug";
    }
}
