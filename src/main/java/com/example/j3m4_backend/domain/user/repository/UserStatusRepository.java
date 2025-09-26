package com.example.j3m4_backend.domain.user.repository;

import com.example.j3m4_backend.domain.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {
}
