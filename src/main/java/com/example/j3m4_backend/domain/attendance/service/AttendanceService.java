package com.example.j3m4_backend.domain.attendance.service;

import com.example.j3m4_backend.domain.attendance.dto.AttendanceRequest;
import com.example.j3m4_backend.domain.attendance.dto.UserListResponseForAttendance;
import com.example.j3m4_backend.domain.attendance.model.Attendance;
import com.example.j3m4_backend.domain.attendance.repository.AttendanceRepository;
import com.example.j3m4_backend.domain.user.model.Role;
import com.example.j3m4_backend.domain.user.model.UserRequestStatus;
import com.example.j3m4_backend.domain.user.model.UserStatus;
import com.example.j3m4_backend.domain.user.repository.UserRepository;
import com.example.j3m4_backend.domain.user.repository.UserStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttendanceService {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final UserStatusRepository userStatusRepository;

    public AttendanceService(UserRepository userRepository, AttendanceRepository attendanceRepository, UserStatusRepository userStatusRepository) {
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
        this.userStatusRepository = userStatusRepository;
    }

    public List<UserListResponseForAttendance> getUsersForAttendance() {
        return userRepository.findApprovedUsersExcludingAdmin(Role.ADMIN).stream()
                .map(user -> new UserListResponseForAttendance(user.getId(), user.getUsername(), user.getName()))
                .toList();
    }

    public void saveAttendance(Long scheduleId, List<AttendanceRequest> requests) {
        Set<Long> approvedUserIds = userStatusRepository.findByUserRequestStatus(UserRequestStatus.APPROVED)
                .stream()
                .map(UserStatus::getUserId)
                .collect(Collectors.toSet());

        for (AttendanceRequest req : requests) {
            if (!approvedUserIds.contains(req.getUserId())) {
                throw new IllegalArgumentException("승인되지 않은 사용자는 출석을 저장할 수 없습니다.");
            }

            Optional<Attendance> existing = attendanceRepository.findByUserIdAndScheduleId(req.getUserId(), scheduleId);

            if (existing.isPresent()) {
                existing.get().updateAttendanceStatus(req.getStatus());
            } else {
                Attendance newAttendance = Attendance.builder()
                        .userId(req.getUserId())
                        .scheduleId(scheduleId)
                        .status(req.getStatus())
                        .build();
                attendanceRepository.save(newAttendance);
            }
        }
    }
}
