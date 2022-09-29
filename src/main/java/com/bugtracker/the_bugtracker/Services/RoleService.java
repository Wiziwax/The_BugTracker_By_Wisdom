package com.bugtracker.the_bugtracker.Services;

import com.bugtracker.the_bugtracker.Models.Role;
import com.bugtracker.the_bugtracker.Repositories.RoleRepository;
import com.bugtracker.the_bugtracker.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public List<Role> listAllRoles(){
        return roleRepository.findAll();
    }

}
