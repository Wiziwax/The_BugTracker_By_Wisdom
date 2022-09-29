package com.bugtracker.the_bugtracker.Services;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Models.Platforms;
import com.bugtracker.the_bugtracker.Models.Role;
import com.bugtracker.the_bugtracker.Models.User;
import com.bugtracker.the_bugtracker.Repositories.PlatformsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlatformService {

    @Autowired
    PlatformsRepository platformsRepository;

    public List<Platforms> platformsList(){
        return platformsRepository.findAll();
    }

    public Platforms create(Platforms platforms) {
        platformsRepository.save(platforms);
        return platforms;
    }


    public Platforms get(Integer id) throws UserNotFoundException {

        try {
            return platformsRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            throw new UserNotFoundException("Could not find any platform with ID " + id);
        }
    }

    public void deletePlatform(Integer id) throws UserNotFoundException {
        Integer countById = platformsRepository.countByPlatformId(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }

        platformsRepository.deleteById(id);
    }

    public void deletePlatformRestController(Integer id){
        platformsRepository.deleteById(id);
    }

    public Platforms updatePlatform(Platforms platform) {
        return platformsRepository.save(platform);
    }


}