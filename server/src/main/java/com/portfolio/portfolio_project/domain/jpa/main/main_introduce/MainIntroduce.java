package com.portfolio.portfolio_project.domain.jpa.main.main_introduce;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "main_introduce_tb")
public class MainIntroduce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Comment("메인 페이지 - 저장된 사진 이름")
    @Column(name = "introduce_img_name")
    private String introduceImgName;
    @Comment("메인 페이지 - S3 또는 저장된 장소")
    @Column(name = "introduce_img_url")
    private String introduceImgUrl;

    @Comment("메인 페이지 - 해당 글 제목")
    @Column(name = "title")
    private String title;
    @Comment("메인 페이지 - 해당 글 내용")
    @Column(name = "content")
    private String content;

    @Comment("메인 페이지 - 등록 시간")
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Comment("메인 페이지 - 수정 시간")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
