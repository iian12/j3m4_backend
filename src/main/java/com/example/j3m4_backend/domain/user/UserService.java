package com.example.j3m4_backend.domain.user;

import com.example.j3m4_backend.domain.user.dto.UserJoinRequest;
import com.example.j3m4_backend.domain.user.model.UserStatus;
import com.example.j3m4_backend.domain.user.model.Users;
import com.example.j3m4_backend.domain.user.repository.UserRepository;
import com.example.j3m4_backend.domain.user.repository.UserStatusRepository;
import com.example.j3m4_backend.global.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserStatusRepository userStatusRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserStatusRepository userStatusRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userStatusRepository = userStatusRepository;
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
}
