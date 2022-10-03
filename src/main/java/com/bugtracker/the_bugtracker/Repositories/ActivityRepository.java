package com.bugtracker.the_bugtracker.Repositories;

import com.bugtracker.the_bugtracker.Models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {


    @Query(nativeQuery = true, value = "SELECT * FROM activity a WHERE a.activity_bug_id=:bugId")
    public List<Activity> activityOwningBug(int bugId);



    @Query(nativeQuery = true, value = "SELECT * FROM tracktesty.activity where bug_action =:bugActionId")
    public List<Activity> activityFilterByAction(int bugActionId);

    }
