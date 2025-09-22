package com.example.j3m4_backend.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String studentId;
    private String name;
    private String email;
    private boolean approved; //관리자 승인 여부
}
