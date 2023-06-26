package com.example.exampreparation_restapi.service;

import com.example.exampreparation_restapi.entity.UserEntity;
import com.example.exampreparation_restapi.exception.DataNotFoundException;
import com.example.exampreparation_restapi.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    public void authenticate(Claims claims, HttpServletRequest request) throws JsonProcessingException {
        String username = claims.getSubject();
        List<String> roles = null;
        if (claims.get("roles") != null) {
           roles = (List<String>) claims.get("roles");
        }else {
            UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow(() -> new DataNotFoundException("User not found"));
            roles = List.of(String.valueOf(userEntity.getRole()));
        }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            getRoles(roles)
                    );
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    private List<SimpleGrantedAuthority> getRoles(List<String> roles){
        return roles.stream()
                .map(SimpleGrantedAuthority ::new)
                .toList();
    }
}
