package com.example.j3m4_backend.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor//Setter 대신 쓰는 이유는 코드 커졌을때 데이터 불변위해
public class LoginRequestDto {
    private String studentId;
    private String password;

    @Builder
    public LoginRequestDto(String studentId, String password) {
        this.studentId = studentId;
        this.password = password;
    }
}
