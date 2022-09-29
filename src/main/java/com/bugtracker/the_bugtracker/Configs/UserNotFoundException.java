package com.bugtracker.the_bugtracker.Configs;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String message) {
        super(message);
    }
}
