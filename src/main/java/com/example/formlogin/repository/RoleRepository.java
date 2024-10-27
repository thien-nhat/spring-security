package com.example.formlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.formlogin.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByName(String name);
}
