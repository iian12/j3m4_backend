package com.example.j3m4_backend.domain.schedule.service;

import com.example.j3m4_backend.domain.schedule.dto.CreateScheduleRequestDto;
import com.example.j3m4_backend.domain.schedule.model.Schedule;
import com.example.j3m4_backend.domain.schedule.repository.ScheduleRepository;
import com.example.j3m4_backend.global.auth.CustomUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void createSchedule(CustomUserDetail author, CreateScheduleRequestDto requestDto) {
        Long authorId = author.getId();
        log.info("authorId: {}", authorId);
        log.info("role: {}", author.getRole());
        Schedule newSchedule = Schedule.builder()
                .title(requestDto.getTitle())
                .authorId(authorId)
                .place(requestDto.getPlace())
                .description(requestDto.getDescription())
                .time(requestDto.getTime())
                .build();

        scheduleRepository.save(newSchedule);
    }
}
