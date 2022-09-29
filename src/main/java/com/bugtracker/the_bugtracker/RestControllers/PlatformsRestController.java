package com.bugtracker.the_bugtracker.RestControllers;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Models.Platforms;
import com.bugtracker.the_bugtracker.Services.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/platforms")
public class PlatformsRestController {

    @Autowired
    PlatformService platformService;

    @GetMapping("")
    public List<Platforms> displayPlatforms(Model model){
        return platformService.platformsList();
    }

    @GetMapping("new")
    public void newPlatformForm(Model model){
        Platforms platform=new Platforms();
        List<Platforms>platformsList = platformService.platformsList();
        model.addAttribute("platform", platform);
    }

    @PostMapping("save")
    public Platforms createPlatform(Platforms platform, Model model){
        return platformService.create(platform);
    }

    @PostMapping("{id}")
    public Platforms updatePlatform(@PathVariable Integer id,
                                 @ModelAttribute("platform") Platforms platform,
                                 Model model) throws UserNotFoundException {
        Platforms existingPlatform = platformService.get(id);
        existingPlatform.setPlatformId(platform.getPlatformId());
        existingPlatform.setPlatformName(platform.getPlatformName());
        return platformService.updatePlatform(existingPlatform);
    }

    //Delete Platform
    @PostMapping("delete")
    public void deletePlatforms(@RequestBody Platforms platforms) {
        Integer id = platforms.getPlatformId();
        platformService.deletePlatformRestController(id);
    }



}

