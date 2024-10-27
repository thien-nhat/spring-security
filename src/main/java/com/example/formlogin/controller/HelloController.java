package com.example.formlogin.controller;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/admin")
    public String helloAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "hello admin";
    }
    @GetMapping("/user")
    public String helloUser() {
        return "hello user";
    }
//    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    public String hello() {
        System.out.println("Hello");
        return "hello world";
    }
}