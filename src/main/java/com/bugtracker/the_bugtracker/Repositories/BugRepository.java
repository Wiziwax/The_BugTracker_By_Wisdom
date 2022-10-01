package com.bugtracker.the_bugtracker.Repositories;

import com.bugtracker.the_bugtracker.DTO.ChartData;
import com.bugtracker.the_bugtracker.Models.Bug;
import com.bugtracker.the_bugtracker.Models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "http://localhost:5000")
public interface BugRepository extends JpaRepository<Bug,Integer> {


    @Query(nativeQuery = true, value = "SELECT bug_treatment_stage as label, " +
            "COUNT(*) as value FROM bug_information GROUP BY bug_treatment_stage")
    public List<ChartData> getProjectStatus();


    @Override
    public List<Bug> findAll();

    public Integer countByBugId(Integer id);


//    @Query(nativeQuery = true,
//            value="SELECT * FROM bugtrackermark2.bug_information WHERE bug_id IN  " +
//                    "(SELECT bug_user_id FROM user_bug WHERE user_bug_id = ?)")
//    public List<Bug> theAssignedBugs(int userId);


    @Query(nativeQuery = true, value = "SELECT * FROM bug_information bu WHERE bu.user_bug_id=:userId")
    public List<Bug> theAssignedBugs(int userId);

    @Query(nativeQuery = true, value = "SELECT * FROM bug_information p WHERE p.severity LIKE %?1%")
    public Page<Bug> search(String keyword, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT bu.bug_name FROM bug_information bu")
    public List<Bug> justBugs();


}

//    @Query(nativeQuery = true, value = "SELECT * FROM bug_information p WHERE CONCAT(p.bug_name, ' ', p.bug_review, ' ', p.bug_treatment_stage, ' ', p.severity, ' ', p.bug_review) LIKE %?1%")