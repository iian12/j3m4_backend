package com.example.j3m4_backend.domain.attendance.dto;

import com.example.j3m4_backend.domain.attendance.model.AttendanceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AttendanceRequest {

    private Long userId;
    private AttendanceStatus status;

    public AttendanceRequest(Long userId, AttendanceStatus status) {
        this.userId = userId;
        this.status = status;
    }
}
