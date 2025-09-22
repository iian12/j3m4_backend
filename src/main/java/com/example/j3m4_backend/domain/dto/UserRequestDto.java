package com.example.j3m4_backend.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    //회원가입 빌드
    private String studentId; // 학번
    private String password;
    private String name;
    private String email;

    @Builder
    public UserRequestDto(String token, String studentId, String password, String name, String email) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
