package com.bugtracker.the_bugtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TheBugTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheBugTrackerApplication.class, args);
    }

}
