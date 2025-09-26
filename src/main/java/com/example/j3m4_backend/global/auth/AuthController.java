package com.example.j3m4_backend.global.auth;

import com.example.j3m4_backend.domain.user.UserService;
import com.example.j3m4_backend.global.dto.MessageResponse;
import com.example.j3m4_backend.global.security.JwtTokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            JwtTokenResponse tokenResponse = authService.login(loginRequestDto);

            // 액세스 토큰을 쿠키로 설정
            Cookie accessTokenCookie = new Cookie("access_token", tokenResponse.getAccessToken());
            accessTokenCookie.setHttpOnly(false);
            accessTokenCookie.setSecure(false); // True 설정 시 HTTPS 환경에서만 사용 가능
            accessTokenCookie.setPath("/"); // 전체 경로에서 사용 가능
            accessTokenCookie.setMaxAge(60 * 60 * 2); // 2h

            // 리프레시 토큰을 쿠키로 설정
            Cookie refreshTokenCookie = new Cookie("refresh_token", tokenResponse.getRefreshToken());
            refreshTokenCookie.setHttpOnly(false);
            refreshTokenCookie.setSecure(false); // True 설정 시 HTTPS 환경에서만 사용 가능
            refreshTokenCookie.setPath("/"); // 전체 경로에서 사용 가능
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60 * 2); // 7일

            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("로그인 성공"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("로그인 실패: " + e.getMessage()));
        }
    }
}
