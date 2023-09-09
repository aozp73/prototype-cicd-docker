package com.portfolio.portfolio_project.web.myblog;

import java.time.LocalDateTime;

import com.portfolio.portfolio_project.domain.jpa.myblog.my_blog.MyBlog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MyBlogDTO_In {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostDTO {
        private String mainTitle;
        private String content;
        private String subTitle;

        private String imageName;
        private String contentType;
        private String imageData;

        public MyBlog toEntity(){
            return MyBlog.builder()
                .mainTitle(this.mainTitle)
                .content(this.content)
                .subTitle(this.subTitle)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PutDTO {
        private Long id;
        private String mainTitle;
        private String content;
        private String subTitle;

        private String imageName;
        private String contentType;
        private String imageData;
        private Boolean imgChangeCheck;

        public void toEntity(MyBlog myBlogPS){
            myBlogPS.setMainTitle(this.mainTitle);
            myBlogPS.setSubTitle(this.subTitle);
            myBlogPS.setContent(this.content);
            myBlogPS.setUpdatedAt(LocalDateTime.now());
        }
    }
}
