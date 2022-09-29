package com.bugtracker.the_bugtracker.Repositories;



import com.bugtracker.the_bugtracker.DTO.RoleUser;
import com.bugtracker.the_bugtracker.Models.Role;
import com.bugtracker.the_bugtracker.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "http://localhost:5000")
public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Override
    public List<Role> findAll();


//    @Query(nativeQuery = true, value =
//            "SELECT r.name as name, COUNT(ur.role_id) " +
//                    "as userCount FROM roles r left join users_roles ur ON ur.role_id = r.role_id " +
//                    "GROUP BY r.name")
//   public List<RoleUser> roleUsers();

    @Query(nativeQuery = true, value = "SELECT u.user_role_id as name, COUNT(*) as userCount " +
            "FROM tracktesty.users u " +
            "GROUP BY u.user_role_id " +
            "ORDER by u.user_role_id")
    public List<RoleUser> roleUsers();


}
//
//    SELECT b.bug_id, b.bug_name, b.bug_review, b.bug_treatment_stage, b.created_by, b.approved_date, b.assigned_date, b.report_date, b.severity, u.user_id, u.first_name
//        FROM bug_information b
//        JOIN users u
//        ON b.bug_id = u.user_id