package com.bugtracker.the_bugtracker.Repositories;

import com.bugtracker.the_bugtracker.Models.Platforms;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "http://localhost:5000")
public interface PlatformsRepository extends CrudRepository<Platforms, Integer> {

    @Override
    public List<Platforms> findAll();

    public Integer countByPlatformId(Integer id);
}
