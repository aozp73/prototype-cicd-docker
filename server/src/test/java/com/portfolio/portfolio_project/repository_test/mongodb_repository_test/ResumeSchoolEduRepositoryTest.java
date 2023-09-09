package com.portfolio.portfolio_project.repository_test.mongodb_repository_test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import com.portfolio.portfolio_project.domain.mongodb.resume.resume_school_edu.ResumeSchoolEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_school_edu.ResumeSchoolEduRepository;

@DataMongoTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
public class ResumeSchoolEduRepositoryTest {

    @Autowired
    private ResumeSchoolEduRepository resumeSchoolEduRepository;

    @BeforeEach
    public void init() {
        resumeSchoolEduRepository.deleteAll();
        setUp();
    }

    @AfterEach
    public void clean() {
        resumeSchoolEduRepository.deleteAll();
    }

    @Test
    void findAllTest() {
        List<ResumeSchoolEdu> allSchoolEdu = resumeSchoolEduRepository.findAll();
        Assertions.assertEquals(2, allSchoolEdu.size());
    }

    @Test
    void findByIdAndUpdateTest() {
        List<ResumeSchoolEdu> allSchoolEdu = resumeSchoolEduRepository.findAll();
        ResumeSchoolEdu firstEdu = allSchoolEdu.get(0);
        
        firstEdu.setSchoolName("update 테스트");
        resumeSchoolEduRepository.save(firstEdu);

        Optional<ResumeSchoolEdu> updatedEduOpt = resumeSchoolEduRepository.findById(firstEdu.getId());
        
        if (updatedEduOpt.isPresent()) {
            ResumeSchoolEdu updatedEdu = updatedEduOpt.get();
            Assertions.assertEquals("update 테스트", updatedEdu.getSchoolName());
        } else {
            Assertions.fail("Select 테스트 에러");
        }
    }

    @Test
    void insertAndDeleteTest() {
        ResumeSchoolEdu edu3 = ResumeSchoolEdu.builder()
            .schoolAdmissionDate("2021-07-01")
            .schoolGraduateDate("2022-02-01")
            .schoolName("JPA 대학교")
            .schoolMajor("데이터베이스 학과")
            .schoolCredit("3.7")
            .schoolGraduateStatus("졸업")
            .createdAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .build();
        
        ResumeSchoolEdu savedEdu = resumeSchoolEduRepository.save(edu3);
        
        Optional<ResumeSchoolEdu> retrievedEdu = resumeSchoolEduRepository.findById(savedEdu.getId());
        
        if (retrievedEdu.isPresent()) {
            resumeSchoolEduRepository.deleteById(savedEdu.getId());
            Optional<ResumeSchoolEdu> deletedEdu = resumeSchoolEduRepository.findById(savedEdu.getId());
            Assertions.assertTrue(deletedEdu.isEmpty());
        } else {
            Assertions.fail("Insert 테스트 에러");
        }
    }

    public void setUp() {
        ResumeSchoolEdu edu1 = ResumeSchoolEdu.builder()
            .schoolAdmissionDate("2018-03-01")
            .schoolGraduateDate("2022-02-25")
            .schoolName("자바 대학교")
            .schoolMajor("기계 공학과")
            .schoolCredit("3.7")
            .schoolGraduateStatus("졸업")
            .createdAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .build();

        ResumeSchoolEdu edu2 = ResumeSchoolEdu.builder()
            .schoolAdmissionDate("2016-03-01")
            .schoolGraduateDate("2020-05-03")
            .schoolName("파이썬 대학교")
            .schoolMajor("컴퓨터 공학과")
            .schoolCredit("3.7")
            .schoolGraduateStatus("졸업")
            .createdAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .build();

        resumeSchoolEduRepository.save(edu1);
        resumeSchoolEduRepository.save(edu2);
    }
}

