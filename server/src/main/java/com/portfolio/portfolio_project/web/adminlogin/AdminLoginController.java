package com.portfolio.portfolio_project.web.adminlogin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.portfolio.portfolio_project.core.dto.ResponseDTO;
import com.portfolio.portfolio_project.core.jwt.MyJwtProvider;
import com.portfolio.portfolio_project.service.AdminLoginService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminLoginController {
    
    private final AdminLoginService adminLoginService;

    @GetMapping("/login")
    public String logigForm(){
        return "adminlogin";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginDTO_In.LoginDTO loginDTO) {
        String jwt = adminLoginService.로그인(loginDTO);

        return ResponseEntity.ok().header(MyJwtProvider.HEADER, jwt)
                .body(new ResponseDTO<>().data(""));
    }
}
