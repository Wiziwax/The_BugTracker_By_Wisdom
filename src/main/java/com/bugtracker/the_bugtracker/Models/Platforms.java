package com.bugtracker.the_bugtracker.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "platforms")
public class Platforms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer platformId;

    private String platformName;

    public Platforms() {
    }

    public Platforms(String platformName) {
        this.platformName = platformName;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
//            CascadeType.REFRESH, CascadeType.PERSIST},
//            fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "platforms_bugs",
//            joinColumns = @JoinColumn(name = "platform_id"),
//            inverseJoinColumns = @JoinColumn(name = "bug_id")
//    )    private List<Bug> bugses;


//    @OneToMany(mappedBy = "platformses")
//    private List<Bug> bugs;

    @Override
    public String toString(){
        return this.platformName;
    }
}
