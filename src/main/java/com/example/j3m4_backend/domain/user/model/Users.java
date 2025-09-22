package com.example.j3m4_backend.domain.user.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;    // School number
    private String password;
    private String name;
    private String email;

    private boolean isJoined;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Users(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.isJoined = false;  // Default is not joined
        this.role = Role.USER; // Default role is USER
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }
}
