package com.example.formlogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.formlogin.domain.Role;
import com.example.formlogin.domain.User;
import com.example.formlogin.repository.RoleRepository;
import com.example.formlogin.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Xóa dữ liệu cũ
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Tạo và lưu role
        Role userRole = new Role();
        userRole.setName("USER");
        userRole.setDescription("Standard User");
        roleRepository.save(userRole);

        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminRole.setDescription("Administrator");
        roleRepository.save(adminRole);

        // Tạo và lưu user
        User user = new User();
        user.setUsername("thien");
        user.setPassword("12345");
        user.setRole(userRole);
        userRepository.save(user);
    }
}
