package com.portfolio.portfolio_project.web.main;

import java.util.ArrayList;
import java.util.List;

import com.portfolio.portfolio_project.domain.jpa.main.main_introduce.MainIntroduce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MainIntroduceDTO_Out {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindAllDTO {
        private Long id;
        private String postTitle;
        private String postContent;
        private String imgURL;

        public static FindAllDTO fromEntity(MainIntroduce entity) {
            return new FindAllDTO(entity.getId(), entity.getTitle(), entity.getContent(), entity.getIntroduceImgUrl());
        }

        public static List<FindAllDTO> fromEntityList(List<MainIntroduce> entityList) {
            List<FindAllDTO> dtoList = new ArrayList<>();
            for (MainIntroduce entity : entityList) {
                dtoList.add(FindAllDTO.fromEntity(entity));
            }
             return dtoList;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostDTO {
        private Long id;
        private String postTitle;
        private String postContent;
        private String imgURL;

        public static PostDTO fromEntity(MainIntroduce entity) {
            return new PostDTO(entity.getId(), entity.getTitle(), entity.getContent(), entity.getIntroduceImgUrl());
        }

        public static List<PostDTO> fromEntityList(List<MainIntroduce> entityList) {
            List<PostDTO> dtoList = new ArrayList<>();
            for (MainIntroduce entity : entityList) {
                dtoList.add(PostDTO.fromEntity(entity));
            }
             return dtoList;
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
        private String imgURL;

        public static PutDTO fromEntity(MainIntroduce entity) {
            return new PutDTO(entity.getId(), entity.getTitle(), entity.getContent(), entity.getIntroduceImgUrl());
        }

    }
}
