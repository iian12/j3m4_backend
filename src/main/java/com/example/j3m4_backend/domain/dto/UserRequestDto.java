package com.example.j3m4_backend.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    //회원가입 빌드
    private String studentId; // 학번
    private String password;
    private String name;
    private String email;
}
