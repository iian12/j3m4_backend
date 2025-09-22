package com.example.j3m4_backend.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String studentId;
    private String name;
    private String email;
    private boolean approved; //관리자 승인 여부

    @Builder
    public UserResponseDto(String token, String studentId, String name, String email, boolean approved) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.approved = approved;
    }
}
