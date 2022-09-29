package com.bugtracker.the_bugtracker.RestControllers;

import com.bugtracker.the_bugtracker.Models.Role;
import com.bugtracker.the_bugtracker.Repositories.RoleRepository;
import com.bugtracker.the_bugtracker.Repositories.UserRepository;
import com.bugtracker.the_bugtracker.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
public class RoleRestController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRepository userRepository;


    @GetMapping("")
    public List<Role> displayRoles(Model model){
        return roleService.listAllRoles();
    }

    @GetMapping("new")
    public void displayRoleForm(Model model) {

        Role aRole = new Role();
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("allRoles", roleList);
        model.addAttribute("role", aRole);
    }

    @PostMapping("save")
    public Role createRoleForm(Role role) {
        return roleRepository.save(role);
    }
}
