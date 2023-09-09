package com.portfolio.portfolio_project.web.adminlogin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AdminLoginDTO_In {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginDTO {
        private String email;
        private String password;
    }
}
