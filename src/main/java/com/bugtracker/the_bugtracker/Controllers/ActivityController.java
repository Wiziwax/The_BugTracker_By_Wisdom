package com.bugtracker.the_bugtracker.Controllers;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Models.Activity;
import com.bugtracker.the_bugtracker.Models.Bug;
import com.bugtracker.the_bugtracker.Models.User;
import com.bugtracker.the_bugtracker.Services.ActivityService;
import com.bugtracker.the_bugtracker.Services.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Controller
@RequestMapping("activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    BugService bugService;


    @GetMapping("")
    public String displayActivities( Model model) throws UserNotFoundException {
        List<Activity> activityList = activityService.findAllActivities();
        List<Bug> bugActivityList = bugService.bugList();
        model.addAttribute("activityList", activityList);


        model.addAttribute("bugActivityList", bugActivityList);
        return "activity/activity_list";
    }





}


