package com.portfolio.portfolio_project.domain.mongodb.resume.resume_school_edu;

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
@Document(collection = "resume_school_edu_tb")
public class ResumeSchoolEdu implements Orderable {
    @Id
    private String id;

    @Comment("이력 페이지 - 학교 입학일")
    @Field("school_admission_date")
    private String schoolAdmissionDate;
    @Comment("이력 페이지 - 학교 졸업일")
    @Field("school_graduate_date")
    private String schoolGraduateDate;
    @Comment("이력 페이지 - 졸업구분")
    @Field("school_graduate_status")
    private String schoolGraduateStatus;

    @Comment("이력 페이지 - 학교명")
    @Field("school_name")
    private String schoolName;
    @Comment("이력 페이지 - 전공명")
    @Field("school_major")
    private String schoolMajor;
    @Comment("이력 페이지 - 학점")
    @Field("school_credit")
    private String schoolCredit;

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
