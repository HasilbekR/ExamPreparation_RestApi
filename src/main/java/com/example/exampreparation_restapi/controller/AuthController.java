package com.example.exampreparation_restapi.controller;

import com.example.exampreparation_restapi.dto.request.LoginDto;
import com.example.exampreparation_restapi.dto.request.UserRequestDto;
import com.example.exampreparation_restapi.dto.response.JwtResponse;
import com.example.exampreparation_restapi.entity.UserEntity;
import com.example.exampreparation_restapi.exception.RequestValidationException;
import com.example.exampreparation_restapi.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/sign-in")
    public ResponseEntity<JwtResponse> signIn(
            @Valid @RequestBody LoginDto loginDto,
            BindingResult bindingResult
            ) throws RequestValidationException {
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return ResponseEntity.ok(userService.signIn(loginDto));
    }
    @PostMapping("/sign-up")
    public ResponseEntity<UserEntity> signUp(
            @Valid @RequestBody UserRequestDto userRequestDto,
            BindingResult bindingResult
    ) throws RequestValidationException {
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return ResponseEntity.ok(userService.save(userRequestDto));
    }
    @GetMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshAccessToken(
            Principal principal
    ){
        return ResponseEntity.ok(userService.getNewAccessToken(principal));
    }
}
