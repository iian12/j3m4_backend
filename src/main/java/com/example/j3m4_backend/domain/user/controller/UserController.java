package com.example.j3m4_backend.domain.user.controller;

import com.example.j3m4_backend.domain.user.dto.UserInfoResponse;
import com.example.j3m4_backend.domain.user.service.UserService;
import com.example.j3m4_backend.domain.user.dto.UserJoinRequest;
import com.example.j3m4_backend.global.auth.CustomUserDetail;
import com.example.j3m4_backend.global.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<MessageResponse> createJoinRequest(@RequestBody UserJoinRequest request) {
        userService.joinNewUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("회원가입이 완료되었습니다."));
    }

    @GetMapping("/info")
    @PreAuthorize("isAuthenticated() and (hasRole('USER') or hasRole('MANAGER'))")
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal CustomUserDetail userDetail) {
        UserInfoResponse userInfo = userService.getUserInfo(userDetail);
        return ResponseEntity.status(HttpStatus.OK).body(userInfo);
    }
}
