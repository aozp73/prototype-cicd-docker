package com.portfolio.portfolio_project.setting_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.portfolio.portfolio_project.core.exception.CustomException;
import com.portfolio.portfolio_project.core.exception.Exception400;

public class ExceptionTest {

    @Test
    @DisplayName("Exception400 테스트")
    void testExceptionMessage() {
        Exception400 exception = new Exception400("Test Message");
        Assertions.assertEquals("Test Message", exception.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.status());
    }
    
    @Test
    @DisplayName("CustomException 테스트")
    void testExceptionStatus() {
        CustomException exception = new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        Assertions.assertEquals("잘못된 요청입니다.", exception.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
