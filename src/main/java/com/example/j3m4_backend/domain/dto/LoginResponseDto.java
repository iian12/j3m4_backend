package com.example.j3m4_backend.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token; // JWT 토큰
    private String studentId;
    private String name;
}
