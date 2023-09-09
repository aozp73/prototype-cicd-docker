package com.portfolio.portfolio_project.integration_test.dummy;

import java.time.LocalDateTime;

import com.portfolio.portfolio_project.domain.jpa.main.main_introduce.MainIntroduce;

public class MainIntroduceDummy {

    public static MainIntroduce newMainIntroduce1() {

        return MainIntroduce.builder()
                .id(1L)
                .introduceImgName("더미 이름 1")
                .introduceImgUrl("더미 url 1")
                .title("더미 타이틀 1")
                .content("더미 내용 1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    public static MainIntroduce newMainIntroduce2() {

        return MainIntroduce.builder()
                .id(2L)
                .introduceImgName("더미 이름 2")
                .introduceImgUrl("더미 url 2")
                .title("더미 타이틀 2")
                .content("더미 내용 2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
