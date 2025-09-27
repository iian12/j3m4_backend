package com.example.j3m4_backend.domain.attendance.controller;

import com.example.j3m4_backend.domain.attendance.dto.AttendanceRequest;
import com.example.j3m4_backend.domain.attendance.dto.UserListResponseForAttendance;
import com.example.j3m4_backend.domain.attendance.service.AttendanceService;
import com.example.j3m4_backend.global.dto.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/users")
    public List<UserListResponseForAttendance> getUsersForAttendance() {
        return attendanceService.getUsersForAttendance();
    }

    @PostMapping("/check/{scheduleId}")
    @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('MANAGER'))")
    public ResponseEntity<MessageResponse> saveAttendance(@PathVariable Long scheduleId, @RequestBody List<AttendanceRequest> requests) {
        attendanceService.saveAttendance(scheduleId, requests);
        return ResponseEntity.ok(new MessageResponse("출석이 저장되었습니다."));
    }
}
