package com.example.j3m4_backend.domain.attendance;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long scheduleId;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Builder
    public Attendance(Long userId, Long scheduleId, AttendanceStatus status) {
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.status = status;
    }

    public void updateAttendanceStatus(AttendanceStatus updateStatus) {
        this.status = updateStatus;
    }
}
