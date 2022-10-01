package com.bugtracker.the_bugtracker.Repositories;

import com.bugtracker.the_bugtracker.Models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    }
