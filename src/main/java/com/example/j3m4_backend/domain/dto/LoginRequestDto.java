package com.example.j3m4_backend.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String studentId;
    private String password;
}
