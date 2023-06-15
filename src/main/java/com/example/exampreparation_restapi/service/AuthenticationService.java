package com.example.exampreparation_restapi.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.util.List;

public class AuthenticationService {

    public void authenticate(Claims claims, HttpServletRequest request){
        List<String> role = (List<String>) claims.get("role");
        String username = claims.getSubject();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        getRoles(role)
                );
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private List<SimpleGrantedAuthority> getRoles(List<String> role){
        return role.stream()
                .map(SimpleGrantedAuthority :: new)
                .toList();
    }
}
