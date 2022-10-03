package com.bugtracker.the_bugtracker.Services;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Enums.Action;
import com.bugtracker.the_bugtracker.Models.Activity;
import com.bugtracker.the_bugtracker.Models.Bug;
import com.bugtracker.the_bugtracker.Repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    public List<Activity> findAllActivities(){
        return activityRepository.findAll();
    }

    public void createActivity(Activity activity){
        activityRepository.save(activity);
    }

    public Activity get(Integer id) throws UserNotFoundException {

        try {
            return activityRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            throw new UserNotFoundException("Could not find any bug with ID " + id);
        }
    }

    public List<Activity> getActivityByBugId(int bugId){
        return activityRepository.activityOwningBug(bugId);
    }


    public List<Activity> activityFilterByAction(int bugActionId){
        return activityRepository.activityFilterByAction(bugActionId);
    }

}
