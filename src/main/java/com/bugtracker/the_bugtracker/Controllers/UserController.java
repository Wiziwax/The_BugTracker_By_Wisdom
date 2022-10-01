package com.bugtracker.the_bugtracker.Controllers;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("users")
public class UserController {

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

    @GetMapping("")
    public String displayUsers(Model model) {
        List<User> users = userService.listAll();
        model.addAttribute("users", users);
        return "users/list-users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {

        User aUser = new User();
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("allRoles", roleList);
        model.addAttribute("user", aUser);
        return "users/new-users";

    }

    @PostMapping("save")
    public String createUserForm(User user, Model model) {
        userService.save(user);
        //Use a redirect to prevent duplicate submissions
        return "redirect:/users/";
    }



    @GetMapping("edit/{id}")
    public String editUser(@PathVariable Integer id, Model model) throws UserNotFoundException {
        User existingUser = userService.get(id);
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("allRoles", roleList);
        model.addAttribute("user", existingUser);

        return "users/edit_user";
    }

    @PostMapping("{id}")
    public String updateUser(@PathVariable Integer id,
                             @ModelAttribute("user") User user,
                             Model model) throws UserNotFoundException {
        User existingUser = userService.get(id);
        existingUser.setId(user.getId());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPassword(user.getPassword());
        existingUser.setRoles(user.getRoles());
        existingUser.setEmail(user.getEmail());

        userService.updateUser(existingUser);

        return "redirect:/users/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUsers(@PathVariable Integer id) throws UserNotFoundException {
        userService.deleteUser(id);
        return "redirect:/users";
    }


    @GetMapping("/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled,
                                          RedirectAttributes redirectAttributes) throws UserNotFoundException {
        userService.updateUserEnabledStatus(id, enabled);
        String user1 = userService.get(id).getFirstName();
        String status = enabled ? "enabled" : "disabled";
        String message = "The user ID " + id + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);
//        String message = user1 + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }


    @GetMapping("mydashboard/{id}")
    public String myDashboard(@PathVariable Integer id,
                              @ModelAttribute("user") User user,
                              Model model) throws UserNotFoundException {

        List<Bug> listBugByDeveloperAssigned = bugService.getBugByUserId(id);
        System.out.println(listBugByDeveloperAssigned);
        model.addAttribute("buguserlist", listBugByDeveloperAssigned);
        return "/users/individualreporttable";
    }

}
