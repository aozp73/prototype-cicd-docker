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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.portfolio.portfolio_project.domain.mongodb.resume.resume_academy_edu.ResumeAcademyEdu;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_academy_edu.ResumeAcademyEduRepository;

@DataMongoTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
public class ResumeAcademyEduRepositoryTest {

    @Autowired
    private ResumeAcademyEduRepository resumeAcademyEduRepository;

    @BeforeEach
    public void init() {
        resumeAcademyEduRepository.deleteAll();
        setUp();
    }
    @AfterEach
    public void clean() {
        resumeAcademyEduRepository.deleteAll();
    }
    
    @Test
    void findAllTest() {
        List<ResumeAcademyEdu> allEdu = resumeAcademyEduRepository.findAll();
        Assertions.assertEquals(2, allEdu.size());
    }

    @Test
    void findByIdAndUpdateTest() {
        List<ResumeAcademyEdu> allEdu = resumeAcademyEduRepository.findAll();
        ResumeAcademyEdu firstEdu = allEdu.get(0);
        
        firstEdu.setAcademyName("update 테스트");
        
        resumeAcademyEduRepository.save(firstEdu);

        Optional<ResumeAcademyEdu> updatedEduOpt = resumeAcademyEduRepository.findById(firstEdu.getId());
        
        if (updatedEduOpt.isPresent()) {
            ResumeAcademyEdu updatedEdu = updatedEduOpt.get();
            Assertions.assertEquals("update 테스트", updatedEdu.getAcademyName());
        } else {
            Assertions.fail("Select 테스트 에러");
        }
    }

    @Test
    void insertAndDeleteTest() {

        ResumeAcademyEdu edu3 = ResumeAcademyEdu.builder()
            .academyEnrollDate("2017-09-01")
            .academyCompletionDate("2018-09-01")
            .academyCompletionStatus("수료")
            .academyName("JPA 교육원")
            .academyCourse("풀스택 과정")
            .academyEtc("-")
            .createdAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .build();
        
        ResumeAcademyEdu savedEdu = resumeAcademyEduRepository.save(edu3);
        
        Optional<ResumeAcademyEdu> retrievedEdu = resumeAcademyEduRepository.findById(savedEdu.getId());
        
        if (retrievedEdu.isPresent()) {
            resumeAcademyEduRepository.deleteById(savedEdu.getId());
            Optional<ResumeAcademyEdu> deletedEdu = resumeAcademyEduRepository.findById(savedEdu.getId());
            Assertions.assertTrue(deletedEdu.isEmpty());
        } else {
            Assertions.fail("Insert 테스트 에러");
        }
    }

    public void setUp() {
        ResumeAcademyEdu edu1 = ResumeAcademyEdu.builder()
            .academyEnrollDate("2015-09-01")
            .academyCompletionDate("2016-09-01")
            .academyCompletionStatus("수료")
            .academyName("자바 교육원")
            .academyCourse("풀스택 과정")
            .academyEtc("-")
            .createdAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .build();
        ResumeAcademyEdu edu2 = ResumeAcademyEdu.builder()
            .academyEnrollDate("2017-09-01")
            .academyCompletionDate("2018-09-01")
            .academyCompletionStatus("수료")
            .academyName("파이썬 교육원")
            .academyCourse("풀스택 과정")
            .academyEtc("-")
            .createdAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .build();

        resumeAcademyEduRepository.save(edu1);
        resumeAcademyEduRepository.save(edu2);
    }
}