package com.example.formlogin.jwtutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("jwt")
public class TokenManager {
    public static final long TOKEN_VALIDITY = 10 * 60 * 60; // 10 hours

    @Value("${secret}")
    private String JWT_SECRET;

    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        System.out.println("LOG: Generated JWT token for user: " + userDetails.getUsername());
        return token;
    }

    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        final Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            System.out.println("LOG: Invalid JWT token: " + e.getMessage());
            return false;
        }

        boolean isTokenExpired = claims.getExpiration().before(new Date());
        boolean isValid = (username.equals(userDetails.getUsername())) && !isTokenExpired;

        System.out.println("LOG: Validating JWT token for user: " + username + ", is valid: " + isValid);
        return isValid;
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        System.out.println("LOG: Extracted username from token: " + username);
        return username;
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }
}
