package com.example.bundosRace.core.error;

import org.springframework.http.HttpStatus;

public abstract class UnexpectedError extends RuntimeException {
    protected String message;
    protected HttpStatus httpStatus;

    protected UnexpectedError(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
    public static class AuthenticationException extends UnexpectedError {
        public AuthenticationException(String message) {super(message, HttpStatus.UNAUTHORIZED);}
        public AuthenticationException() {
            super("허용되지 않은 인증 접근입니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    public static class AccessDeniedException extends UnexpectedError {
        public AccessDeniedException(String message) {super(message, HttpStatus.FORBIDDEN);}
        public AccessDeniedException() {
            super("접근 거부되었습니다.", HttpStatus.FORBIDDEN);
        }
    }

    public static class DataIntegrityViolationException extends UnexpectedError {
        public DataIntegrityViolationException(String message) {super(message, HttpStatus.CONFLICT);}
        public DataIntegrityViolationException() {
            super("데이터 무결성 위반입니다.", HttpStatus.CONFLICT);
        }
    }

    public static class NullPointerException extends UnexpectedError {
        public NullPointerException(String message) {super(message, HttpStatus.INTERNAL_SERVER_ERROR);}
        public NullPointerException() {
            super("널 참조 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class IllegalArgumentException extends UnexpectedError {
        public IllegalArgumentException(String message) {super(message, HttpStatus.INTERNAL_SERVER_ERROR);}
        public IllegalArgumentException() {super("부적절한 인자가 전달되었습니다.", HttpStatus.INTERNAL_SERVER_ERROR);}
    }

    public static class ServerError extends UnexpectedError {
        public ServerError(String message) {super(message, HttpStatus.INTERNAL_SERVER_ERROR);}
        public ServerError() {super("서버 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR);}
    }
}