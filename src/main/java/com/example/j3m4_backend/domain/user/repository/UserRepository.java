package com.example.j3m4_backend.domain.user.repository;

import com.example.j3m4_backend.domain.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
