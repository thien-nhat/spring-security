package com.example.formlogin.filter;

import com.example.formlogin.jwtutils.TokenManager;
import com.example.formlogin.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Profile("jwt")
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("CALL FILTER INTERNAL");
        String tokenHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);
            try {
                username = tokenManager.getUsernameFromToken(token);
                System.out.println("LOG: Extracted username from token: " + username);
            } catch (IllegalArgumentException e) {
                System.out.println("LOG: Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("LOG: JWT Token has expired");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (tokenManager.validateJwtToken(token, userDetails)) {
                    System.out.println("LOG: Valid JWT Token for user: " + username);
                    System.out.println("LOG: Valid JWT Token for user author: " + userDetails.getAuthorities());

                    Authentication authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    System.out.println("LOG: Invalid JWT Token for user: " + username);
                }
            }

        }


        filterChain.doFilter(request, response);
    }
}
