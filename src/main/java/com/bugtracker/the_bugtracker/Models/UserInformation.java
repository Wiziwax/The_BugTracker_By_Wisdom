package com.bugtracker.the_bugtracker.Models;

import java.security.Principal;

public class UserInformation {



    public String currentUserName(Principal principal) {
        return principal.getName();
    }

}
