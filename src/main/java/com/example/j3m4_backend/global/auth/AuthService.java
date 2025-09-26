package com.example.j3m4_backend.global.auth;

import com.example.j3m4_backend.domain.user.model.Users;
import com.example.j3m4_backend.domain.user.repository.UserRepository;
import com.example.j3m4_backend.global.security.JwtTokenProvider;
import com.example.j3m4_backend.global.security.JwtTokenResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public JwtTokenResponse login(LoginRequestDto requestDto) {
        Users user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            String accessToken = jwtTokenProvider.createAccessToken(user.getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

            return JwtTokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
        } else {
            throw new BadCredentialsException( "비밀번호가 일치하지 않습니다.");
        }
    }
}
