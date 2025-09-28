package com.example.j3m4_backend.domain.user.service;

import com.example.j3m4_backend.domain.attendance.model.Attendance;
import com.example.j3m4_backend.domain.attendance.model.AttendanceStatus;
import com.example.j3m4_backend.domain.attendance.repository.AttendanceRepository;
import com.example.j3m4_backend.domain.user.dto.UserInfoResponse;
import com.example.j3m4_backend.domain.user.dto.UserJoinRequest;
import com.example.j3m4_backend.domain.user.model.UserStatus;
import com.example.j3m4_backend.domain.user.model.Users;
import com.example.j3m4_backend.domain.user.repository.UserRepository;
import com.example.j3m4_backend.domain.user.repository.UserStatusRepository;
import com.example.j3m4_backend.global.auth.CustomUserDetail;
import com.example.j3m4_backend.global.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserStatusRepository userStatusRepository;
    private final AttendanceRepository attendanceRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserStatusRepository userStatusRepository, AttendanceRepository attendanceRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userStatusRepository = userStatusRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public void joinNewUser(UserJoinRequest request) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("이미 존재하는 이메일입니다.");
        }

        if (userRepository.existsByUsername(request.getSchoolNum())) {
            throw new UserAlreadyExistsException("이미 존재하는 학번입니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        Users newUser = Users.builder()
                .username(request.getSchoolNum())
                .password(encodedPassword)
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Long userId = userRepository.save(newUser).getId();

        UserStatus newUserStatus = UserStatus.builder()
                .userId(userId)
                .build();
        userStatusRepository.save(newUserStatus);
    }

    private double calculateAttendanceRate(Long userId) {
        List<Attendance> attendances = attendanceRepository.findByUserId(userId);

        List<Attendance> validAttendances = attendances.stream()
                .filter(a -> a.getStatus() != AttendanceStatus.EXCUSED)
                .toList();

        if (validAttendances.isEmpty()) return 0.0;

        long presentCount = validAttendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT || a.getStatus() == AttendanceStatus.LATE)
                .count();

        return (presentCount * 100.0) / validAttendances.size();
    }

    public UserInfoResponse getUserInfo(CustomUserDetail userDetail) {
        Long userId = userDetail.getId();

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserInfoResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .unit(user.getUnit())
                .attendanceRate(calculateAttendanceRate(userId))
                .build();
    }
}
