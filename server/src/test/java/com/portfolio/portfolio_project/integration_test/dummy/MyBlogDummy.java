package com.portfolio.portfolio_project.integration_test.dummy;

import java.time.LocalDateTime;

import com.portfolio.portfolio_project.domain.jpa.myblog.my_blog.MyBlog;

public class MyBlogDummy {
    
    public static MyBlog newMyBlog1() {

        return MyBlog.builder()
                .id(1L)
                .mainTitle("더미 제목 1")
                .subTitle("더미 소제목 1")
                .content("더미 타이틀 1")
                .blogImgName("더미 내용 1")
                .blogImgUrl("더미 url 1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    public static MyBlog newMyBlog2() {

        return MyBlog.builder()
                .id(2L)
                .mainTitle("더미 제목 2")
                .subTitle("더미 소제목 2")
                .content("더미 타이틀 2")
                .blogImgName("더미 내용 2")
                .blogImgUrl("더미 url 2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
