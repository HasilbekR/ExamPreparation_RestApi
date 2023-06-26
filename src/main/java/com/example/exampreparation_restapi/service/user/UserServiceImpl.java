package com.example.exampreparation_restapi.service.user;

import com.example.exampreparation_restapi.dto.request.LoginDto;
import com.example.exampreparation_restapi.dto.request.UserRequestDto;
import com.example.exampreparation_restapi.dto.response.JwtResponse;
import com.example.exampreparation_restapi.entity.UserEntity;
import com.example.exampreparation_restapi.entity.enums.UserRole;
import com.example.exampreparation_restapi.exception.AuthenticationFailedException;
import com.example.exampreparation_restapi.repository.UserRepository;
import com.example.exampreparation_restapi.exception.DataNotFoundException;
import com.example.exampreparation_restapi.exception.UniqueObjectException;
import com.example.exampreparation_restapi.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserEntity save(UserRequestDto userRequestDto) {
        UserEntity userEntity = modelMapper.map(userRequestDto, UserEntity.class);
        if (userRepository.findUserEntityByUsername(userEntity.getUsername()).isEmpty() &&
                userRepository.findUserEntityByPhoneNumber(userEntity.getPhoneNumber()).isEmpty()) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRole(UserRole.USER);
            return userRepository.save(userEntity);
        }
        throw new UniqueObjectException("username or phone number already exists");
    }

    @Override
    public List<UserEntity> getArchivedUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.getArchivedUsers(pageable).getContent();
    }

    @Override
    public void retrieveUser(UUID userId) {
        UserEntity userEntity = userRepository.findUserEntityById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        userEntity.setArchived(false);
        userRepository.save(userEntity);
    }

    @Override
    public JwtResponse getNewAccessToken(Principal principal) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(() -> new DataNotFoundException("user not found"));
        String accessToken = jwtService.generateAccessToken(userEntity);
        return JwtResponse.builder().accessToken(accessToken).build();

    }

    @Override
    public void delete(Principal principal) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(
                () -> new DataNotFoundException("User not found"));
        userEntity.setArchived(true);
        userEntity.setUpdatedDate(LocalDateTime.now());
        userRepository.save(userEntity);
    }

    @Override
    public JwtResponse signIn(LoginDto loginDto) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(loginDto.getUsername())
                .orElseThrow(() -> new AuthenticationFailedException("incorrect username or password"));
        if (!userEntity.isArchived()) {
            if (passwordEncoder.matches(loginDto.getPassword(), userEntity.getPassword())) {
                String accessToken = jwtService.generateAccessToken(userEntity);
                String refreshToken = jwtService.generateRefreshToken(userEntity);
                return JwtResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken).build();
            }
            throw new AuthenticationFailedException("incorrect username or password");
        }
        throw new AuthenticationFailedException("incorrect username or password");
    }

    @Override
    public UserEntity update(UserRequestDto userRequestDto, Principal principal) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(() -> new AuthenticationFailedException("Your access expired"));
        if(!userEntity.getUsername().equals(userRequestDto.getUsername())) {
            Optional<UserEntity> userEntityByUsername = userRepository.findUserEntityByUsername(userRequestDto.getUsername());
            if (userEntityByUsername.isPresent()) throw new UniqueObjectException("Username already exists");
        }
        if(!userEntity.getPhoneNumber().equals(userRequestDto.getPhoneNumber())) {
            Optional<UserEntity> userEntityByPhoneNumber = userRepository.findUserEntityByPhoneNumber(userRequestDto.getPhoneNumber());
            if (userEntityByPhoneNumber.isPresent()) throw new UniqueObjectException("Phone number already exists");
        }
        if (userRequestDto.getUsername() != null) {
            userEntity.setUsername(userRequestDto.getUsername());
        }
        if (userRequestDto.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        }
        if(userRequestDto.getPhoneNumber() != null) {
            userEntity.setPhoneNumber(userRequestDto.getPhoneNumber());
        }
        userEntity.setUpdatedDate(LocalDateTime.now());
        return userRepository.save(userEntity);
    }
}
