package com.bugtracker.the_bugtracker.Controllers;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Models.Platforms;
import com.bugtracker.the_bugtracker.Models.Role;
import com.bugtracker.the_bugtracker.Models.User;
import com.bugtracker.the_bugtracker.Repositories.PlatformsRepository;
import com.bugtracker.the_bugtracker.Services.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("platforms")
public class PlatformsController {

        @Autowired
        PlatformService platformService;


        @Autowired
        PlatformsRepository platformsRepository;


        @GetMapping("")
        public String displayPlatforms(Model model){
            List<Platforms> platforms = platformService.platformsList();
            model.addAttribute("platforms", platforms);
            return "platforms/list-platforms";
        }



        @GetMapping("/new")
        public String newPlatformForm(Model model){
            Platforms platform=new Platforms();
            List<Platforms>platformsList = platformService.platformsList();
            model.addAttribute("platform", platform);
            return "platforms/new-platform";
        }

        @PostMapping("save")
        public String createPlatform(Platforms platform, Model model){
            platformService.create(platform);
            return "redirect:/platforms/";
        }

    @PostMapping("{id}")
    public String updatePlatform(@PathVariable Integer id,
                             @ModelAttribute("platform") Platforms platform,
                             Model model) throws UserNotFoundException {
        Platforms existingPlatform = platformService.get(id);
//        existingPlatform.setPlatformId(platform.getPlatformId());
        existingPlatform.setPlatformName(platform.getPlatformName());
        platformsRepository.save(existingPlatform);
        return "redirect:/platforms/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUsers(@PathVariable Integer id) throws UserNotFoundException {
        platformService.deletePlatform(id);
        return "redirect:/platforms";
    }


    @GetMapping("edit/{id}")
    public String editPlatform(@PathVariable Integer id, Model model) throws UserNotFoundException {
        Platforms existingPlatforms = platformService.get(id);
        model.addAttribute("platforms", existingPlatforms);

        return "platforms/edit_platform";
    }

}
