package com.example.exampreparation_restapi.service.user;

import com.example.exampreparation_restapi.dto.request.LoginDto;
import com.example.exampreparation_restapi.dto.request.UserRequestDto;
import com.example.exampreparation_restapi.dto.response.JwtResponse;
import com.example.exampreparation_restapi.entity.UserEntity;
import com.example.exampreparation_restapi.service.BaseService;

public interface UserService extends BaseService<UserEntity, UserRequestDto> {
    public JwtResponse signIn(LoginDto loginDto);

}
