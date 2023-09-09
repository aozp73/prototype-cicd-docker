package com.portfolio.portfolio_project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.portfolio_project.core.exception.Exception400;
import com.portfolio.portfolio_project.core.exception.Exception500;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_academy_edu.ResumeAcademyEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_academy_edu.ResumeAcademyEduRepository;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_certificate.ResumeCertificate;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_certificate.ResumeCertificateRepository;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_school_edu.ResumeSchoolEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_school_edu.ResumeSchoolEduRepository;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_self_study.ResumeSelfStudy;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_self_study.ResumeSelfStudyRepository;
import com.portfolio.portfolio_project.service.module.row_order_module.OrderUtils;
import com.portfolio.portfolio_project.web.resume.ResumeDTO_In;
import com.portfolio.portfolio_project.web.resume.ResumeDTO_In.OrderUpdateDto;
import com.portfolio.portfolio_project.web.resume.ResumeDTO_Out;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ResumeService {
    
    private final ResumeSchoolEduRepository resumeSchoolEduRepository;
    private final ResumeAcademyEduRepository resumeAcademyEduRepository;
    private final ResumeCertificateRepository resumeCertificateRepository;
    private final ResumeSelfStudyRepository resumeSelfStudyRepository;

    
    // FindAll
    @Transactional
    public ResumeDTO_Out.FindAllDTO resume_findAll() {
        List<ResumeSchoolEdu> schoolEdus = resumeSchoolEduRepository.findAll(); 
        List<ResumeAcademyEdu> academyEdus = resumeAcademyEduRepository.findAll();
        List<ResumeCertificate> certificates = resumeCertificateRepository.findAll();
        List<ResumeSelfStudy> selfStudies = resumeSelfStudyRepository.findAll();

        return ResumeDTO_Out.FindAllDTO.fromEntities(academyEdus, certificates, selfStudies, schoolEdus);
    }



    // POST
    @Transactional
    public ResumeDTO_Out.SchoolEdu_postDTO resume_schooledu_post(ResumeDTO_In.Schooledu_postDTO postDTO_In) {
        ResumeSchoolEdu resumeSchoolEdu = postDTO_In.toEntity();
        OrderUtils.setOrder(resumeSchoolEdu, resumeSchoolEduRepository);

        resumeSchoolEduRepository.save(resumeSchoolEdu);

        return ResumeDTO_Out.SchoolEdu_postDTO.fromEntity(resumeSchoolEdu);
    }
    @Transactional
    public ResumeDTO_Out.AcademyEdu_postDTO resume_academyedu_post(ResumeDTO_In.Academyedu_postDTO postDTO_In) {
        ResumeAcademyEdu resumeAcademyEdu = postDTO_In.toEntity();
        OrderUtils.setOrder(resumeAcademyEdu, resumeAcademyEduRepository);

        resumeAcademyEduRepository.save(resumeAcademyEdu);

        return ResumeDTO_Out.AcademyEdu_postDTO.fromEntity(resumeAcademyEdu);
    }
    @Transactional
    public ResumeDTO_Out.Certificate_postDTO resume_certificate_post(ResumeDTO_In.Certificate_postDTO postDTO_In) {
        ResumeCertificate resumeCertificate = postDTO_In.toEntity();
        OrderUtils.setOrder(resumeCertificate, resumeCertificateRepository);

        resumeCertificateRepository.save(resumeCertificate);

        return ResumeDTO_Out.Certificate_postDTO.fromEntity(resumeCertificate);
    }
    @Transactional
    public ResumeDTO_Out.SelfStudy_postDTO resume_selfstudy_post(ResumeDTO_In.Selfstudy_postDTO postDTO_In) {
        ResumeSelfStudy resumeSelfStudy = postDTO_In.toEntity();
        OrderUtils.setOrder(resumeSelfStudy, resumeSelfStudyRepository);

        resumeSelfStudyRepository.save(resumeSelfStudy);

        return ResumeDTO_Out.SelfStudy_postDTO.fromEntity(resumeSelfStudy);
    }



    // DELETE
    @Transactional
    public void resume_schooledu_delete(String resumeID){
        ResumeSchoolEdu resumeSchoolEdu = resumeSchoolEduRepository.findById(resumeID).orElseThrow(() -> {
            throw new Exception400("삭제하려는 게시물이 존재하지 않습니다.");
        });

        try {
            resumeSchoolEduRepository.delete(resumeSchoolEdu);
        } catch (Exception e) {
            throw new Exception500("데이터 삭제에 실패하였습니다.");
        }
    }
    @Transactional
    public void resume_academyedu_delete(String resumeID){
        ResumeAcademyEdu resumeAcademyEdu = resumeAcademyEduRepository.findById(resumeID).orElseThrow(() -> {
            throw new Exception400("삭제하려는 데이터가 존재하지 않습니다.");
        });

        try {
            resumeAcademyEduRepository.delete(resumeAcademyEdu);
        } catch (Exception e) {
            throw new Exception500("데이터 삭제에 실패하였습니다.");
        }
    }
    @Transactional
    public void resume_certificate_delete(String resumeID){
        ResumeCertificate resumeCertificate = resumeCertificateRepository.findById(resumeID).orElseThrow(() -> {
            throw new Exception400("삭제하려는 데이터가 존재하지 않습니다.");
        });

        try {
            resumeCertificateRepository.delete(resumeCertificate);
        } catch (Exception e) {
            throw new Exception500("데이터 삭제에 실패하였습니다.");
        }
    }
    @Transactional
    public void resume_selfstudy_delete(String resumeID){
        ResumeSelfStudy resumeSelfStudy = resumeSelfStudyRepository.findById(resumeID).orElseThrow(() -> {
            throw new Exception400("삭제하려는 데이터가 존재하지 않습니다.");
        });

        try {
            resumeSelfStudyRepository.delete(resumeSelfStudy);
        } catch (Exception e) {
            throw new Exception500("데이터 삭제에 실패하였습니다.");
        }
    }



    // Row Move
    @Transactional
    public void resume_schoolEdu_updateOrder(List<ResumeDTO_In.OrderUpdateDto> updates){
        try {
            for (OrderUpdateDto update : updates) {
                ResumeSchoolEdu record = resumeSchoolEduRepository.findById(update.getId()).orElseThrow(() -> new Exception400("데이터가 존재하지 않습니다."));
                record.setOrder(update.getOrder());
                resumeSchoolEduRepository.save(record);
            }
        } catch (Exception e) {
            throw new Exception500("문서를 찾고, 순서를 갱신하는데 실패하였습니다.");
        }
    }
    @Transactional
    public void resume_academyedu_updateOrder(List<ResumeDTO_In.OrderUpdateDto> updates){
        try {
            for (OrderUpdateDto update : updates) {
                ResumeAcademyEdu record = resumeAcademyEduRepository.findById(update.getId()).orElseThrow(() -> new Exception400("데이터가 존재하지 않습니다."));
                record.setOrder(update.getOrder());
                resumeAcademyEduRepository.save(record);
            }
        } catch (Exception e) {
            throw new Exception500("문서를 찾고, 순서를 갱신하는데 실패하였습니다.");
        }
    }
    @Transactional
    public void resume_certificate_updateOrder(List<ResumeDTO_In.OrderUpdateDto> updates){
        try {
            for (OrderUpdateDto update : updates) {
                ResumeCertificate record = resumeCertificateRepository.findById(update.getId()).orElseThrow(() -> new Exception400("데이터가 존재하지 않습니다."));
                record.setOrder(update.getOrder());
                resumeCertificateRepository.save(record);
            }
        } catch (Exception e) {
            throw new Exception500("문서를 찾고, 순서를 갱신하는데 실패하였습니다.");
        }
    }
    @Transactional
    public void resume_selfstudy_updateOrder(List<ResumeDTO_In.OrderUpdateDto> updates){
        try {
            for (OrderUpdateDto update : updates) {
                ResumeSelfStudy record = resumeSelfStudyRepository.findById(update.getId()).orElseThrow(() -> new Exception400("데이터가 존재하지 않습니다."));
                record.setOrder(update.getOrder());
                resumeSelfStudyRepository.save(record);
            }
        } catch (Exception e) {
            throw new Exception500("문서를 찾고, 순서를 갱신하는데 실패하였습니다.");
        }
    }

}
