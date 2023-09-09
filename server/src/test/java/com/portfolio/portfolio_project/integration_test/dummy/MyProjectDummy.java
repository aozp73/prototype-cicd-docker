package com.portfolio.portfolio_project.integration_test.dummy;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProject;

public class MyProjectDummy {
    
    public static MyProject newMyProject1() {

        return MyProject.builder()
                .id(1L)
                .projectImgName("더미 프로젝트 이미지 이름 1")
                .projectImgUrl("더미 프로젝트 이미지 url 1")
                .projectName("더미 프로젝트 이름 1")
                .member(1)
                .startDate(LocalDate.of(2011, 1, 1))
                .endDate(LocalDate.of(2011, 1, 11))
                .readmeUrl("더미 readme url 1")
                .githubUrl("더미 github url 1")
                .individualPerformanceImgName("더미 개인수행 이미지 이름 1")
                .individualPerformanceImgUrl("더미 개인수행 이미지 url 1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static MyProject newMyProject2() {

        return MyProject.builder()
                .id(2L)
                .projectImgName("더미 프로젝트 이미지 이름 2")
                .projectImgUrl("더미 프로젝트 이미지 url 2")
                .projectName("더미 프로젝트 이름 2")
                .member(2)
                .startDate(LocalDate.of(2022, 2, 12))
                .endDate(LocalDate.of(2022, 2, 22))
                .readmeUrl("더미 readme url 2")
                .githubUrl("더미 github url 2")
                .individualPerformanceImgName("더미 개인수행 이미지 이름 2")
                .individualPerformanceImgUrl("더미 개인수행 이미지 url 2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
