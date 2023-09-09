package com.portfolio.portfolio_project.core.exception;

import org.springframework.http.HttpStatus;

import com.portfolio.portfolio_project.core.dto.ResponseDTO;

import lombok.Getter;

@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ResponseDTO<?> body() {
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.UNAUTHORIZED, "unAuthorized", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
