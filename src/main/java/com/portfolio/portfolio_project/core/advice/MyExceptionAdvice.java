package com.portfolio.portfolio_project.core.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.portfolio.portfolio_project.core.exception.CustomException;
import com.portfolio.portfolio_project.core.exception.Exception400;
import com.portfolio.portfolio_project.core.exception.Exception401;
import com.portfolio.portfolio_project.core.exception.Exception403;
import com.portfolio.portfolio_project.core.exception.Exception404;
import com.portfolio.portfolio_project.core.exception.Exception500;
import com.portfolio.portfolio_project.core.util.Script;

import io.sentry.Sentry;

@RestControllerAdvice
public class MyExceptionAdvice {
 
    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(CustomException.class) 
    public ResponseEntity<?> customException(CustomException e) {
        Sentry.captureException(e);
        return new ResponseEntity<>(Script.back(e.getMessage()), e.getStatus());
    }
}
