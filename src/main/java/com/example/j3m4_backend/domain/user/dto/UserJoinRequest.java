package com.example.j3m4_backend.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequest {

    private String schoolNum;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;

    public UserJoinRequest(String schoolNum, String password, String name, String email, String phoneNumber) {
        this.schoolNum = schoolNum;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
