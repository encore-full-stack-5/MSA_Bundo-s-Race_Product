package com.example.bundosRace.core;

import org.springframework.http.HttpStatus;

public abstract class ExpectedError extends RuntimeException {

    protected String message;
    protected HttpStatus httpStatus;

    protected ExpectedError(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static class MethodArgumentNotValidException extends ExpectedError {
        public MethodArgumentNotValidException(String message) {
            super(message, HttpStatus.NOT_FOUND);
        }

        public MethodArgumentNotValidException() {
            super("요청한 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public static class MissingServletRequestParameterException extends ExpectedError {
        public MissingServletRequestParameterException(String message) {
            super(message, HttpStatus.NOT_FOUND);
        }

        public MissingServletRequestParameterException() {
            super("필수 요청 매개변수가 누락되었습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public static class BusinessException extends ExpectedError {
        public BusinessException(String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }

        public BusinessException() {
            super("비즈니스 규칙 위반입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public static class AuthenticationException extends ExpectedError {
        public AuthenticationException(String message) {
            super(message, HttpStatus.UNAUTHORIZED);
        }

        public AuthenticationException() {
            super("유효하지 않은 인증 요청입니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    public static class ResourceNotFoundException extends ExpectedError {
        public ResourceNotFoundException(String message) {
            super(message, HttpStatus.NOT_FOUND);
        }

        public ResourceNotFoundException() {
            super("찾는 데이터가 없습니다.", HttpStatus.NOT_FOUND);
        }
    }
}