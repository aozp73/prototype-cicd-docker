package com.portfolio.portfolio_project.integration_test.dummy;

import java.time.LocalDateTime;

import com.portfolio.portfolio_project.domain.mongodb.resume.resume_academy_edu.ResumeAcademyEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_certificate.ResumeCertificate;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_school_edu.ResumeSchoolEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_self_study.ResumeSelfStudy;

public class ResumeDummy {
    
    public static ResumeSchoolEdu newResumeSchoolEdu1() {
        return ResumeSchoolEdu.builder()
                .schoolAdmissionDate("2018-03-01")
                .schoolGraduateDate("2022-02-25")
                .schoolName("자바 대학교")
                .schoolMajor("기계 공학과")
                .schoolCredit("3.7")
                .schoolGraduateStatus("졸업")
                .order(1)
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }
    public static ResumeSchoolEdu newResumeSchoolEdu2() {
        return ResumeSchoolEdu.builder()
                .schoolAdmissionDate("2016-03-01")
                .schoolGraduateDate("2020-05-03")
                .schoolName("파이썬 대학교")
                .schoolMajor("컴퓨터 공학과")
                .schoolCredit("3.7")
                .schoolGraduateStatus("졸업")
                .order(2)
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }

        
    public static ResumeAcademyEdu newResumeAcademyEdu1() {
        return ResumeAcademyEdu.builder()
                .academyEnrollDate("2015-09-01")
                .academyCompletionDate("2016-09-01")
                .academyCompletionStatus("수료")
                .academyName("자바 교육원")
                .academyCourse("풀스택 과정")
                .academyEtc("-")
                .order(1)
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }
    public static ResumeAcademyEdu newResumeAcademyEdu2() {
        return ResumeAcademyEdu.builder()
                .academyEnrollDate("2017-09-01")
                .academyCompletionDate("2018-09-01")
                .academyCompletionStatus("수료")
                .academyName("파이썬 교육원")
                .academyCourse("풀스택 과정")
                .academyEtc("-")
                .order(2)
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }

    public static ResumeCertificate newResumeCertificate1() {
        return ResumeCertificate.builder()
                .acquisitionDate("2022-01-01")
                .certificateType("IT 자격증")
                .certificateName("SQLD")
                .certificateIssuingAgency("데이터베이스진흥원")
                .certificateStatus("최종합격")
                .order(1)
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }
    public static ResumeCertificate newResumeCertificate2() {
        return ResumeCertificate.builder()
                .acquisitionDate("2021-03-01")
                .certificateType("어학 자격증")
                .certificateName("TOEIC (830)")
                .certificateIssuingAgency("한국TOEIC위원회")
                .certificateStatus("최종합격")
                .order(2)
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }

    public static ResumeSelfStudy newResumeSelfStudy1() {
        return ResumeSelfStudy.builder()
                .selfStudyDate("2023-06-01")
                .selfStudytype("Backend")
                .selfStudyTheme("Java")
                .selfStudyPlatform("Inflearn")
                .selfStudyBloggingLink("https:/blog.com/study1")
                .order(1)
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }
    public static ResumeSelfStudy newResumeSelfStudy2() {
        return ResumeSelfStudy.builder()
                .selfStudyDate("2023-06-15")
                .selfStudytype("DevOps")
                .selfStudyTheme("Docker")
                .selfStudyPlatform("YouTube")
                .selfStudyBloggingLink("https:/blog.com/study2")
                .order(2)
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }
}
