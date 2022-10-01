package com.bugtracker.the_bugtracker.Configs;

import com.bugtracker.the_bugtracker.Models.User;
import com.bugtracker.the_bugtracker.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> user = userRepository.findByEmail(username);

        if(user.size()==0){
            throw new UsernameNotFoundException("User details not found for the user");
        }
        return new SecurityUser(user.get(0));
    }
}
