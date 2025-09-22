package com.example.j3m4_backend.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String token; // JWT 토큰
    private String studentId;
    private String name;

    @Builder
     public LoginResponseDto(String token, String studentId, String name) {
         this.token = token;
         this.studentId = studentId;
         this.name = name;
     }
}
