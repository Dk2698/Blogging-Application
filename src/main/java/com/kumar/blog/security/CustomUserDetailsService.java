package com.kumar.blog.security;

import com.kumar.blog.entities.User;
import com.kumar.blog.exceptions.ResourceNotFoundException;
import com.kumar.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database by username
        User user = userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","email:"+username,0));
        // this is User but also return UserDetails
        // this class implements UserDetails in  User Table
        return user;
    }
}
