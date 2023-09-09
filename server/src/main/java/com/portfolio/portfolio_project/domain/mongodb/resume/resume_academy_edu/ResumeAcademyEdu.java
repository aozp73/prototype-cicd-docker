package com.portfolio.portfolio_project.domain.mongodb.resume.resume_academy_edu;

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
@Document(collection = "resume_academy_edu_tb")
public class ResumeAcademyEdu implements Orderable{
    @Id
    private String id;

    @Comment("이력 페이지 - 교육 시작일")
    @Field("academy_enroll_date")
    private String academyEnrollDate;
    @Comment("이력 페이지 - 교육 수료일")
    @Field("academy_completion_date")
    private String academyCompletionDate;
    
    @Comment("이력 페이지 - 교육 수료상태")
    @Field("academy_completion_status")
    private String academyCompletionStatus;
    @Comment("이력 페이지 - 교육장소")
    @Field("academy_name")
    private String academyName;
    @Comment("이력 페이지 - 교육과정명")
    @Field("academy_course")
    private String academyCourse;
    @Comment("이력 페이지 - 기타내용")
    @Field("academy_etc")
    private String academyEtc;

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
