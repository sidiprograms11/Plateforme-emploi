package com.example.demo.registration.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.identity.UserStatus;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegisteredUserRepository
        extends JpaRepository<RegisteredUserEntity, String> {

    Optional<RegisteredUserEntity> findByEmail(String email);

    List<RegisteredUserEntity> findByStatus(UserStatus status);

    List<RegisteredUserEntity> findByEmailContainingIgnoreCase(String email);
}