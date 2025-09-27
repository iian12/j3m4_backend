package com.example.j3m4_backend.domain.schedule.repository;

import com.example.j3m4_backend.domain.schedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
