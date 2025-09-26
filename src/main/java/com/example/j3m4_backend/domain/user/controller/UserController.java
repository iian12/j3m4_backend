package com.example.j3m4_backend.domain.user.controller;

import com.example.j3m4_backend.domain.user.UserService;
import com.example.j3m4_backend.domain.user.dto.UserJoinRequest;
import com.example.j3m4_backend.global.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api//users")
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
}
