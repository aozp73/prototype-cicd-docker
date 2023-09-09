package com.portfolio.portfolio_project.core.jwt;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.portfolio.portfolio_project.domain.jpa.user.User;

@Service
public class MyJwtProvider {

    @Value("${name}")
    private String name;

    private final String SUBJECT = "jwt";
    private final int EXP = 1000 * 60 * 60;
    public static final String TOKEN_PREFIX = "Bearer "; // 스페이스 
    public static final String HEADER = "Authorization";
    private String SECRET;

    @PostConstruct
    public void init() {
        this.SECRET = this.name;
    }

    public String create(User user) {
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("role", "ROLE_" + user.getRole())
                .sign(Algorithm.HMAC512(SECRET));

        return TOKEN_PREFIX + jwt;
    }

    public DecodedJWT verify(String jwt) throws TokenExpiredException, Exception {
        
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build().verify(jwt);
        return decodedJWT;
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(SECRET);
            JWT.require(algorithm).build().verify(token);
            return true; 
        } catch (Exception e) {
            return false;
        }
    }
}

