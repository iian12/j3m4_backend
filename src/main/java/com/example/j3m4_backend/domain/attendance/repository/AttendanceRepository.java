package com.example.j3m4_backend.domain.attendance.repository;

import com.example.j3m4_backend.domain.attendance.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByUserIdAndScheduleId(Long userId, Long scheduleId);

    List<Attendance> findByScheduleId(Long scheduleId);
}
