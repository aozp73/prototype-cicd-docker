package com.portfolio.portfolio_project.web.resume;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.portfolio.portfolio_project.domain.mongodb.resume.resume_academy_edu.ResumeAcademyEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_certificate.ResumeCertificate;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_school_edu.ResumeSchoolEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_self_study.ResumeSelfStudy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ResumeDTO_Out {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindAllDTO {
        private List<AcademyEdu_postDTO> resumeAcademyEdus;
        private List<Certificate_postDTO> resumeCertificates;
        private List<SelfStudy_postDTO> resumeSelfStudies;
        private List<SchoolEdu_postDTO> resumeSchoolEdus; 

        public static FindAllDTO fromEntities(List<ResumeAcademyEdu> academyEdus, List<ResumeCertificate> certificates,
                                                List<ResumeSelfStudy> selfStudies, List<ResumeSchoolEdu> schoolEdus) {
           
            List<AcademyEdu_postDTO> academyEduDtos = academyEdus.stream()
                .sorted(Comparator.comparingInt(ResumeAcademyEdu::getOrder))
                .map(AcademyEdu_postDTO::fromEntity)
                .collect(Collectors.toList());

            List<Certificate_postDTO> certificateDtos = certificates.stream()
                .sorted(Comparator.comparingInt(ResumeCertificate::getOrder))
                .map(Certificate_postDTO::fromEntity)
                .collect(Collectors.toList());

            List<SelfStudy_postDTO> selfStudyDtos = selfStudies.stream()
                .sorted(Comparator.comparingInt(ResumeSelfStudy::getOrder))
                .map(SelfStudy_postDTO::fromEntity)
                .collect(Collectors.toList());

            List<SchoolEdu_postDTO> schoolEduDtos = schoolEdus.stream()
               .sorted(Comparator.comparingInt(ResumeSchoolEdu::getOrder))
               .map(SchoolEdu_postDTO::fromEntity)
               .collect(Collectors.toList()); 

            return FindAllDTO.builder()
                .resumeAcademyEdus(academyEduDtos)
                .resumeCertificates(certificateDtos)
                .resumeSelfStudies(selfStudyDtos)
                .resumeSchoolEdus(schoolEduDtos) 
                .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SchoolEdu_postDTO {
        private String id;
        private String schoolAdmissionDate;
        private String schoolGraduateDate;
        private String schoolGraduateStatus;

        private String schoolName;
        private String schoolMajor;
        private String schoolCredit;

        public static SchoolEdu_postDTO fromEntity(ResumeSchoolEdu resumeSchoolEdu) {
            return SchoolEdu_postDTO.builder()
                .id(resumeSchoolEdu.getId())
                .schoolAdmissionDate(resumeSchoolEdu.getSchoolAdmissionDate())
                .schoolGraduateDate(resumeSchoolEdu.getSchoolGraduateDate())
                .schoolGraduateStatus(resumeSchoolEdu.getSchoolGraduateStatus())
                .schoolName(resumeSchoolEdu.getSchoolName())
                .schoolMajor(resumeSchoolEdu.getSchoolMajor())
                .schoolCredit(resumeSchoolEdu.getSchoolCredit())
                .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AcademyEdu_postDTO {
        private String id;
        private String academyEnrollDate;
        private String academyCompletionDate;
        private String academyCompletionStatus;

        private String academyName;
        private String academyCourse;
        private String academyEtc;

        public static AcademyEdu_postDTO fromEntity(ResumeAcademyEdu resumeAcademyEdu) {
            return AcademyEdu_postDTO.builder()
                .id(resumeAcademyEdu.getId())
                .academyEnrollDate(resumeAcademyEdu.getAcademyEnrollDate())
                .academyCompletionDate(resumeAcademyEdu.getAcademyCompletionDate())
                .academyCompletionStatus(resumeAcademyEdu.getAcademyCompletionStatus())
                .academyName(resumeAcademyEdu.getAcademyName())
                .academyCourse(resumeAcademyEdu.getAcademyCourse())
                .academyEtc(resumeAcademyEdu.getAcademyEtc())
                .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Certificate_postDTO {
        private String id;
        private String acquisitionDate;
        private String certificateType;
        private String certificateName;
        private String certificateIssuingAgency;
        private String certificateStatus;

        public static Certificate_postDTO fromEntity(ResumeCertificate resumeCertificate) {
            return Certificate_postDTO.builder()
                .id(resumeCertificate.getId())
                .acquisitionDate(resumeCertificate.getAcquisitionDate())
                .certificateType(resumeCertificate.getCertificateType())
                .certificateName(resumeCertificate.getCertificateName())
                .certificateIssuingAgency(resumeCertificate.getCertificateIssuingAgency())
                .certificateStatus(resumeCertificate.getCertificateStatus())
                .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SelfStudy_postDTO {
        private String id;
        private String selfStudyDate;
        private String selfStudytype;
        private String selfStudyTheme;
        private String selfStudyPlatform;
        private String selfStudyBloggingLink;

        public static SelfStudy_postDTO fromEntity(ResumeSelfStudy resumeSelfStudy) {
            return SelfStudy_postDTO.builder()
                .id(resumeSelfStudy.getId())
                .selfStudyDate(resumeSelfStudy.getSelfStudyDate())
                .selfStudytype(resumeSelfStudy.getSelfStudytype())
                .selfStudyTheme(resumeSelfStudy.getSelfStudyTheme())
                .selfStudyPlatform(resumeSelfStudy.getSelfStudyPlatform())
                .selfStudyBloggingLink(resumeSelfStudy.getSelfStudyBloggingLink())
                .build();
        }
    }
}