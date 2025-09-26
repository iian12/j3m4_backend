package com.example.j3m4_backend.global.exception;

/**
 * 회원가입 시 중복된 사용자(아이디/이메일)가 존재할 경우 발생하는 예외
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("이미 존재하는 사용자입니다.");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
