package com.example.formlogin.service;

import com.example.formlogin.domain.Role;
import com.example.formlogin.domain.User;
import com.example.formlogin.repository.RoleRepository;
import com.example.formlogin.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        System.out.println("LOG: Adding user with username: " + user.getUsername());

        // Encrypt the password
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        Role roleUser = roleRepo.findByName("USER");
        user.setRole(roleUser);

        User savedUser = userRepo.save(user);

        return savedUser;
    }
}
