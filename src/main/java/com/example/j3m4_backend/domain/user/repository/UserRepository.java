package com.example.j3m4_backend.domain.user.repository;

import com.example.j3m4_backend.domain.user.model.Role;
import com.example.j3m4_backend.domain.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<Users> findByUsername(String username);

    @Query("SELECT u FROM Users u JOIN UserStatus us ON u.id = us.userId " +
            "WHERE u.role <> :adminRole AND us.userRequestStatus = 'APPROVED'")
    List<Users> findApprovedUsersExcludingAdmin(@Param("adminRole") Role adminRole);

}
