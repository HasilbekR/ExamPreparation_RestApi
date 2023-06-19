package com.example.exampreparation_restapi.controller;

import com.example.exampreparation_restapi.dto.request.UserRequestDto;
import com.example.exampreparation_restapi.entity.UserEntity;
import com.example.exampreparation_restapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PutMapping("/update")
    public ResponseEntity<UserEntity> update(
            @RequestBody UserRequestDto requestDto,
            Principal principal
            ) {
        UserEntity updatedUser = userService.update(requestDto, principal);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            Principal principal
    ){
        userService.delete(principal);
        return ResponseEntity.ok("deleted");
    }
    @GetMapping("/get-archived-users")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getArchivedUsers(
            @RequestParam int page,
            @RequestParam int size
    ){
        return ResponseEntity.status(200).body(userService.getArchivedUsers(page, size));
    }
    @GetMapping("/retrieve-user")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<String> retrieveUser(
            @RequestParam UUID userId
            ){
        userService.retrieveUser(userId);
        return ResponseEntity.ok("retrieved user");
    }

}
