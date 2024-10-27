package com.example.formlogin.domain;


public class JwtResponseModel {
    private final String token;

    public JwtResponseModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

