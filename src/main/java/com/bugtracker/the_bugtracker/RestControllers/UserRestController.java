package com.bugtracker.the_bugtracker.RestControllers;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Models.Bug;
import com.bugtracker.the_bugtracker.Models.Role;
import com.bugtracker.the_bugtracker.Models.User;
import com.bugtracker.the_bugtracker.Repositories.BugRepository;
import com.bugtracker.the_bugtracker.Repositories.RoleRepository;
import com.bugtracker.the_bugtracker.Repositories.UserRepository;
import com.bugtracker.the_bugtracker.Services.BugService;
import com.bugtracker.the_bugtracker.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BugRepository bugRepository;

    @Autowired
    BugService bugService;

    //DISPLAY ALL USERS
    @GetMapping("")
    public List<User> displayUsers() {
        return userService.listAll();
    }

    //ROLE LIST
    @GetMapping("roleList")
    public List<Role> roleList() {
        return roleRepository.findAll();
    }


    //FIND USER BY ID
    @GetMapping("findById")
    public Optional<User> findUser(@RequestParam Integer id){
        return userService.getUserById(id);
    }


    //CREATE USER
    @PostMapping("addUser")
    public void createUserForm(@RequestBody User user) {
        System.out.println(user);
        userService.save(user);
    }


    //DELETE USER
    @PostMapping("delete")
    public void deleteUsers(@RequestBody User user){
        Integer id = user.getId();
        System.out.println(id);
        userService.deleteUser(id);
    }

    //UPDATE USER
    @PutMapping("update")
    public void updateUser(@RequestBody User user) throws UserNotFoundException {
        userService.updateUserRestController(user.getId(),
                user.getFirstName(), user.getLastName(), user.getEmail());
    }


    // ENABLE/DISABLE USER
    @GetMapping("/enabled/{id}")
    public void updateUserEnabledStatus(@PathVariable("id") Integer id,
                                        boolean enabled
                                        ) throws UserNotFoundException {
        User user=userService.get(id);
        if(user.isEnabled()){
        user.setEnabled(false);
        }else {user.setEnabled(true);}

        enabled= user.isEnabled()?true:false;
        userService.updateUserEnabledStatus(id, enabled);
    }


    @GetMapping("mydashboard/{id}")
    public List <Bug> myDashboard(@PathVariable Integer id) throws UserNotFoundException {
        return bugService.getBugByUserId(id);
    }

//    @PutMapping("update/{userId}")
//    public void updateUser(
//            @PathVariable("userId") Integer userId,
//            @RequestParam(required = false) String firstName,
//            @RequestParam(required = false) String lastName,
//            @RequestParam(required = false) String email
//            ) {
//            System.out.println();
//        userService.updateUserRestController(userId, firstName, lastName, email);
//    }

}
