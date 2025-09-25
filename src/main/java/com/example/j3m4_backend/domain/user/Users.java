package com.example.j3m4_backend.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String schoolNum;
    private String name;
    private int age;
    private String phone;

    @Builder
    public Users(String username, String password, String schoolNum, String name, int age, String phone) {
        this.username = username;
        this.password = password;
        this.schoolNum = schoolNum;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
