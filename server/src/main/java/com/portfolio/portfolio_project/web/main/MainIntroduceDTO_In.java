package com.portfolio.portfolio_project.web.main;

import java.time.LocalDateTime;

import com.portfolio.portfolio_project.domain.jpa.main.main_introduce.MainIntroduce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MainIntroduceDTO_In {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostDTO {
        private String postTitle;
        private String postContent;
        private String imageName;
        private String contentType;
        private String imageData;

        public MainIntroduce toEntity() {
            return MainIntroduce.builder()
                .title(this.postTitle)
                .content(this.postContent)
                .createdAt(LocalDateTime.now()) 
                .updatedAt(LocalDateTime.now()) 
                .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PutDTO {
        private Long id;
        private String postTitle;
        private String postContent;
        private String imageName;
        private String contentType;
        private String imageData;
        private Boolean imgChangeCheck;

        public void toEntity(MainIntroduce mainIntroducePS) {
            mainIntroducePS.setTitle(this.getPostTitle());
            mainIntroducePS.setContent(this.getPostContent());
            mainIntroducePS.setUpdatedAt(LocalDateTime.now());
        }   
    }
}