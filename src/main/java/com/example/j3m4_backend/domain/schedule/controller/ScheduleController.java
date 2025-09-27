package com.example.j3m4_backend.domain.schedule.controller;

import com.example.j3m4_backend.domain.schedule.dto.CreateScheduleRequestDto;
import com.example.j3m4_backend.domain.schedule.service.ScheduleService;
import com.example.j3m4_backend.global.auth.CustomUserDetail;
import com.example.j3m4_backend.global.dto.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<MessageResponse> createSchedule(@AuthenticationPrincipal CustomUserDetail user, @RequestBody CreateScheduleRequestDto createScheduleRequestDto) {
        log.info("Authorities in controller: {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        scheduleService.createSchedule(user, createScheduleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("일정이 생성되었습니다."));
    }
}
