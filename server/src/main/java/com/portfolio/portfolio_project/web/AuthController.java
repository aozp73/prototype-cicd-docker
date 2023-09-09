package com.portfolio.portfolio_project.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.portfolio_project.core.jwt.MyJwtProvider;

@RestController
public class AuthController {

    @Autowired
    private MyJwtProvider myJwtProvider;

    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        String jwt = token.replace(MyJwtProvider.TOKEN_PREFIX, "");
        boolean isValid = myJwtProvider.validateToken(jwt);

        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
}
