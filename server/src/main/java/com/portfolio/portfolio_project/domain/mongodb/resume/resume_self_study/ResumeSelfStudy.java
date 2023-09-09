package com.portfolio.portfolio_project.domain.mongodb.resume.resume_self_study;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.portfolio.portfolio_project.service.module.row_order_module.Orderable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document(collection = "resume_self_study_tb")
public class ResumeSelfStudy implements Orderable {
    @Id
    private String id;

    @Comment("이력 페이지 - 개인학습 시작일")
    @Field("self_study_date")
    private String selfStudyDate;
    @Comment("이력 페이지 - 개인학습 종류 (ex. 백엔드, linux, 데브옵스 등)")
    @Field("self_study_type")
    private String selfStudytype;
    @Comment("이력 페이지 - 개인학습 주제 (ex. 강의명)")
    @Field("self_study_theme")
    private String selfStudyTheme;
    @Comment("이력 페이지 - 개인학습 수강장소 (ex. 인프런 인터넷 강의)")
    @Field("self_study_platform")
    private String selfStudyPlatform;
    @Comment("이력 페이지 - 개인학습 블로깅 링크")
    @Field("self_study_blogging_link")
    private String selfStudyBloggingLink;

    @Comment("이력 페이지 - 등록 시간")
    @Field("created_at")
    private String createdAt;
    @Comment("이력 페이지 - 수정 시간")
    @Field("updated_at")
    private String updatedAt;

    @Comment("이력 페이지 - View에서 Row이동 시 참고할 필드")
    @Field("order")
    private Integer order;

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }
}
