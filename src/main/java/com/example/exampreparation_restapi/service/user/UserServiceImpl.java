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
import org.modelmapper.PropertyMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        if(userRepository.findUserEntityByUsername(userEntity.getUsername()).isEmpty() &&
                userRepository.findUserEntityByPhoneNumber(userEntity.getPhoneNumber()).isEmpty()){
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRole(UserRole.USER);
            return userRepository.save(userEntity);
        }
        throw new UniqueObjectException("username or phone number already exists");
    }

    @Override
    public UserEntity update(UserRequestDto userRequestDto, UUID id) {
        UserEntity currentUser = userRepository.findUserEntityById(id)
                .orElseThrow(() -> new DataNotFoundException("Question not found"));

        PropertyMap<UserRequestDto, UserEntity> questionMap = new PropertyMap<UserRequestDto, UserEntity>() {
            @Override
            protected void configure() {
                skip().setId(null);
                skip().setCreatedDate(null);
                skip().setUpdatedDate(null);
            }
        };

        modelMapper.addMappings(questionMap);
        modelMapper.map(userRequestDto, currentUser);

        currentUser.setUpdatedDate(LocalDateTime.now());
        return userRepository.save(currentUser);
    }
    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
    @Override
    public UserEntity getById(UUID id) {
        return userRepository.findUserEntityById(id).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );
    }
    @Override
    public JwtResponse signIn(LoginDto loginDto) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(loginDto.getUsername())
                .orElseThrow(() -> new AuthenticationFailedException("incorrect username or password"));
        if(passwordEncoder.matches(loginDto.getPassword(), userEntity.getPassword())) {
            String accessToken = jwtService.generateAccessToken(userEntity);
            return JwtResponse.builder().accessToken(accessToken).build();
        }
        throw new AuthenticationFailedException("incorrect username or password");
    }
}
