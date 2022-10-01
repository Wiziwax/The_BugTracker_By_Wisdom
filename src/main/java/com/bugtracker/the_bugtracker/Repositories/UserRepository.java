package com.bugtracker.the_bugtracker.Repositories;


import com.bugtracker.the_bugtracker.Models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Repository
@CrossOrigin(origins = "http://localhost:5000")
public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    public List<User> findAll();

    public Long countById(Integer id);

    @Modifying
    @Query("UPDATE User u SET u.enabled=?2 WHERE u.id=?1")
    public void updateEnabledStatus(Integer id, boolean enabled);

    @Query(value = "SELECT * FROM USERS u WHERE u.user_role_id=:roleId", nativeQuery = true)
    public List<User> userRoleList(int roleId);

    Optional<User> findUserByEmail(String email);

    List<User> findByEmail(String email);

    User getByEmail(String userEmail);
}



//    @Query(nativeQuery = true, value= "SELECT * FROM bugtrackermark2.users WHERE USER_ID IN  " +
//            "(SELECT USER_ID FROM users_roles WHERE role_id = ?)")
//    public List<User> userRoleList(int roleId);
//
