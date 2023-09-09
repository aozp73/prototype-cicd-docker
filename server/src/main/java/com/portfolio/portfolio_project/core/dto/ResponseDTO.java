package com.portfolio.portfolio_project.core.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ResponseDTO<T> {
    private Integer status;
    private String msg; 
    private T data; 

    public ResponseDTO() {
        this.status = HttpStatus.OK.value();
        this.msg = "성공";
        this.data = null;
    }

    public ResponseDTO<?> data(T data) {
        this.data = data;
        return this;
    }

    public ResponseDTO<?> fail(HttpStatus httpStatus, String errorTitle, T errorDetail) {
        this.status = httpStatus.value();
        this.msg = errorTitle;
        this.data = errorDetail;
        return this;
    }
}
