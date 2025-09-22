package com.example.j3m4_backend.domain.user.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private UserRequestStatus userRequestStatus;

    private LocalDateTime createdAt;

    @Builder
    public UserStatus(Long userId) {
        this.userId = userId;
        this.userRequestStatus = UserRequestStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void updateMemberStatus(UserRequestStatus newStatus) {
        this.userRequestStatus = newStatus;
    }
}
