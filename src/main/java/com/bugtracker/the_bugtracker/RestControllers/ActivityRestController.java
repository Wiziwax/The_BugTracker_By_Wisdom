package com.bugtracker.the_bugtracker.RestControllers;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Models.Activity;
import com.bugtracker.the_bugtracker.Services.ActivityService;
import com.bugtracker.the_bugtracker.Services.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/activity")
public class ActivityRestController {

    @Autowired
    ActivityService activityService;

    @Autowired
    BugService bugService;

    @GetMapping("")
    public List<Activity> displayActivities(){
        return activityService.findAllActivities();
    }

    @GetMapping("activityfilter/{id}")
    public List<Activity> activitiesFilter(@PathVariable Integer id) {
        return activityService.activityFilterByAction(id);
    }
}
