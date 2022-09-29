package com.bugtracker.the_bugtracker.RestControllers;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Models.Bug;
import com.bugtracker.the_bugtracker.Models.Platforms;
import com.bugtracker.the_bugtracker.Models.User;
import com.bugtracker.the_bugtracker.Repositories.BugRepository;
import com.bugtracker.the_bugtracker.Services.ActivityService;
import com.bugtracker.the_bugtracker.Services.BugService;
import com.bugtracker.the_bugtracker.Services.PlatformService;
import com.bugtracker.the_bugtracker.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/bug")
public class BugRestController {

    @Autowired
    BugService bugService;

    @Autowired
    UserService userService;

    @Autowired
    PlatformService platformService;

    @Autowired
    ActivityService activityService;

    @Autowired
    BugRepository bugRepository;


    @GetMapping("")
    public List<Bug> bugList(){
        return bugService.bugList();
    }


    //LIST BUG BY PAGE
    @GetMapping("pages")
    public ResponseEntity<Page<Bug>> viewBugPages(Pageable pageable){
        return new ResponseEntity<>(bugService.listAll(pageable), HttpStatus.ACCEPTED);
    }


//    //LIST BUG BY PAGE & CONTENT
//    @GetMapping("page/{pageNum}")
//    public List<Bug> viewPage(@PathVariable(name = "pageNum") int pageNum,
//                              Pageable pageable) {
//        Page<Bug> page = bugService.listAll(pageable);
//        return page.getContent();
//    }

    //SEARCH FILTER
    @GetMapping("search")
    public Page<Bug> viewHomePage( @RequestParam(name = "keyword", required = false) String keyword, Pageable pageable) {
        return bugService.listAllSearches(keyword, pageable);
    }


    //FIND BUG BY ID
    @GetMapping("{id}")
    public Optional<Bug> findBugById(@PathVariable(name = "id") Integer id){
        return bugRepository.findById(id);
    }


    //REQUEST FOR LIST OF USERS ACCORDING TO THEIR ROLES, VIA ID(1, 2, or 3)
    @GetMapping("findusersonrole")
    public List<User> getByRole(@RequestParam(name = "roleId")Integer roleId){
        return userService.getUserByRoleId(roleId);
    }

    //PLATFORM LIST
    @GetMapping("platformlist")
    public List<Platforms> getPlatforms(){
        return platformService.platformsList();
    }


    //CREATE BUG
    @PostMapping("addbug")
    public void createBug(@RequestBody Bug bug) {
        bugService.create(bug);
    }


    //UPDATE BUG
    @PostMapping("update")
    public void updateBug(@RequestBody Bug bug) throws UserNotFoundException {
        bugService.updateBugRestController(bug.getBugId(), bug.getLabel(),
                bug.getSeverity(), bug.getBugReview(), bug.getBugTreatmentStage(),
                bug.getPlatformses(), bug.getProgressStatus(), bug.getUserAssignedToBug()
                );


        //////REQUEST FOR NECESSARY FIELDS FROM EMEKA SO YOU CAN SET AN ADEQUATE
        ///// CONSTRUCTOR TO REGISTER ACTIVITY IN ACTIVITY CLASS.

    }

    @GetMapping("delete")
    public void deleteBug(@RequestBody Bug bug) throws UserNotFoundException {
        Integer id = bug.getBugId();
        bugService.deleteBug(id);
    }
}
