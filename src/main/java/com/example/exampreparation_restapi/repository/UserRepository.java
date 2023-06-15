package com.example.exampreparation_restapi.repository;

import com.example.exampreparation_restapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findUserEntityByUsername(String username);
    Optional<UserEntity> findUserEntityByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findUserEntityById(UUID id);

}
