package com.portfolio.portfolio_project.core.exception;

import org.springframework.http.HttpStatus;

import com.portfolio.portfolio_project.core.dto.ResponseDTO;

import lombok.Getter;

@Getter
public class Exception400 extends RuntimeException {
    public Exception400(String message) {
        super(message);
    }

    public ResponseDTO<?> body() {
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.BAD_REQUEST, "badRequest", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}

