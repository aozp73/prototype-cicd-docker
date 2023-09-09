package com.portfolio.portfolio_project.web.resume;

import java.time.LocalDateTime;
import java.util.List;

import com.portfolio.portfolio_project.domain.mongodb.resume.resume_academy_edu.ResumeAcademyEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_certificate.ResumeCertificate;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_school_edu.ResumeSchoolEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_self_study.ResumeSelfStudy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ResumeDTO_In {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Schooledu_postDTO {
        private List<String> values;

        public ResumeSchoolEdu toEntity() {
            return ResumeSchoolEdu.builder()
                    .schoolAdmissionDate(values.get(0))
                    .schoolGraduateDate(values.get(1))
                    .schoolGraduateStatus(values.get(2))
                    .schoolName(values.get(3))
                    .schoolMajor(values.get(4))
                    .schoolCredit(values.get(5))
                    .createdAt(LocalDateTime.now().toString())
                    .updatedAt(LocalDateTime.now().toString())
                    .build();
        }
    }
    @Getter
    @Setter
    public static class Academyedu_postDTO {
        private List<String> values;

        public ResumeAcademyEdu toEntity() {
            return ResumeAcademyEdu.builder()
                    .academyEnrollDate(values.get(0))
                    .academyCompletionDate(values.get(1))
                    .academyCompletionStatus(values.get(2))
                    .academyName(values.get(3))
                    .academyCourse(values.get(4))
                    .academyEtc(values.get(5))
                    .createdAt(LocalDateTime.now().toString())
                    .updatedAt(LocalDateTime.now().toString())
                    .build();
        }
    }
    @Getter
    @Setter
    public static class Certificate_postDTO {
        private List<String> values;

        public ResumeCertificate toEntity() {
            return ResumeCertificate.builder()
                    .acquisitionDate(values.get(0))
                    .certificateType(values.get(1))
                    .certificateName(values.get(2))
                    .certificateIssuingAgency(values.get(3))
                    .certificateStatus(values.get(4))
                    .createdAt(LocalDateTime.now().toString())
                    .updatedAt(LocalDateTime.now().toString())
                    .build();
        }
    }
    @Getter
    @Setter
    public static class Selfstudy_postDTO {
        private List<String> values;

        public ResumeSelfStudy toEntity() {
            return ResumeSelfStudy.builder()
                    .selfStudyDate(values.get(0))
                    .selfStudytype(values.get(1))
                    .selfStudyTheme(values.get(2))
                    .selfStudyPlatform(values.get(3))
                    .selfStudyBloggingLink(values.get(4))
                    .createdAt(LocalDateTime.now().toString())
                    .updatedAt(LocalDateTime.now().toString())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderUpdateDto {
        private String id;
        private Integer order;
    }
}