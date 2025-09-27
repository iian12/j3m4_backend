package com.example.j3m4_backend.domain.user.repository;

import com.example.j3m4_backend.domain.user.model.UserRequestStatus;
import com.example.j3m4_backend.domain.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {
    Optional<UserStatus> findByUserRequestStatus(UserRequestStatus userRequestStatus);
}
