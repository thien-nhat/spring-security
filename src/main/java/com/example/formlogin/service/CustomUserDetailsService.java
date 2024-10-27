package com.example.formlogin.service;

import com.example.formlogin.domain.User;
import com.example.formlogin.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("LOG: User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        System.out.println("User found: " + user.getUsername() + " with roles: " + user.getRole() + " with pass: " + user.getPassword() + " with auth: " + user.getAuthorities());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

}
