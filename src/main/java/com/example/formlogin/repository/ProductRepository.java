package com.example.formlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.formlogin.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
