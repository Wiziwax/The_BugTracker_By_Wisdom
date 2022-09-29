package com.bugtracker.the_bugtracker.Controllers;

import com.bugtracker.the_bugtracker.DTO.ChartData;
import com.bugtracker.the_bugtracker.DTO.RoleUser;
import com.bugtracker.the_bugtracker.Repositories.BugRepository;
import com.bugtracker.the_bugtracker.Repositories.RoleRepository;
import com.bugtracker.the_bugtracker.Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("home")
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BugRepository bugRepository;





    @GetMapping("/")
    public String displayHome(Model model) throws JsonProcessingException {


        Map<String, Object> map = new HashMap<>();

//        We are querying the database for projects
        List<ChartData> projectData = bugRepository.getProjectStatus();


//Converting projectData object into a json structure for use in javascript
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString= objectMapper.writeValueAsString(projectData);
        model.addAttribute("projectStatusCount", jsonString);


//We're querying the database for employees
        List <RoleUser> roleUsersCnt = roleRepository.roleUsers();
        model.addAttribute("statusReportCount", projectData);
        model.addAttribute("employeesListProjectsCnt", roleUsersCnt);

        return "main/home";
    }



}