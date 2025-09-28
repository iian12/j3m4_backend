package com.example.j3m4_backend.domain.user.dto;

import com.example.j3m4_backend.domain.user.model.Unit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoResponse {

    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private Unit unit;
    private double attendanceRate;

    @Builder
    public UserInfoResponse(String username, String name, String email, String phoneNumber, Unit unit, double attendanceRate) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.unit = unit;
        this.attendanceRate = attendanceRate;
    }
}
