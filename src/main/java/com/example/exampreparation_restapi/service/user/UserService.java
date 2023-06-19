package com.example.exampreparation_restapi.service.user;

import com.example.exampreparation_restapi.dto.request.LoginDto;
import com.example.exampreparation_restapi.dto.request.UserRequestDto;
import com.example.exampreparation_restapi.dto.response.JwtResponse;
import com.example.exampreparation_restapi.entity.UserEntity;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface UserService {
    JwtResponse signIn(LoginDto loginDto);
    UserEntity update(UserRequestDto userRequestDto, Principal principal);
    void delete(Principal principal);
    UserEntity getById(UUID id);
    UserEntity save(UserRequestDto userRequestDto);
    List<UserEntity> getArchivedUsers(int page, int size);
    void retrieveUser(UUID userId);
}
