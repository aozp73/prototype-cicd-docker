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

import com.portfolio.portfolio_project.domain.mongodb.resume.resume_self_study.ResumeSelfStudy;
import com.portfolio.portfolio_project.domain.mongodb.resume.resume_self_study.ResumeSelfStudyRepository;

@DataMongoTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
public class ResumeSelfStudyRepositoryTest {

    @Autowired
    private ResumeSelfStudyRepository resumeSelfStudyRepository;

    @BeforeEach
    public void init() {
        resumeSelfStudyRepository.deleteAll();
        setUp();
    }

    @AfterEach
    public void clean() {
        resumeSelfStudyRepository.deleteAll();
    }

    @Test
    void findAllTest() {
        List<ResumeSelfStudy> allSelfStudy = resumeSelfStudyRepository.findAll();
        Assertions.assertEquals(2, allSelfStudy.size());
    }

    @Test
    void findByIdAndUpdateTest() {
        List<ResumeSelfStudy> allSelfStudy = resumeSelfStudyRepository.findAll();
        ResumeSelfStudy firstStudy = allSelfStudy.get(0);
        
        firstStudy.setSelfStudyTheme("update 테스트");
        resumeSelfStudyRepository.save(firstStudy);

        Optional<ResumeSelfStudy> updatedStudyOpt = resumeSelfStudyRepository.findById(firstStudy.getId());
        
        if (updatedStudyOpt.isPresent()) {
            ResumeSelfStudy updatedStudy = updatedStudyOpt.get();
            Assertions.assertEquals("update 테스트", updatedStudy.getSelfStudyTheme());
        } else {
            Assertions.fail("Select 테스트 에러");
        }
    }

    @Test
    void insertAndDeleteTest() {
        ResumeSelfStudy study3 = ResumeSelfStudy.builder()
            .selfStudyDate("2023-07-16")
            .selfStudytype("OS")
            .selfStudyTheme("Linux")
            .selfStudyPlatform("Inflearn")
            .selfStudyBloggingLink("https:/blog.com/study3")
            .createdAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .build();
        
        ResumeSelfStudy savedStudy = resumeSelfStudyRepository.save(study3);
        
        Optional<ResumeSelfStudy> retrievedStudy = resumeSelfStudyRepository.findById(savedStudy.getId());
        
        if (retrievedStudy.isPresent()) {
            resumeSelfStudyRepository.deleteById(savedStudy.getId());
            Optional<ResumeSelfStudy> deletedStudy = resumeSelfStudyRepository.findById(savedStudy.getId());
            Assertions.assertTrue(deletedStudy.isEmpty());
        } else {
            Assertions.fail("Insert 테스트 에러");
        }
    }

    public void setUp() {
        ResumeSelfStudy study1 = ResumeSelfStudy.builder()
            .selfStudyDate("2023-06-01")
            .selfStudytype("Backend")
            .selfStudyTheme("Java")
            .selfStudyPlatform("Inflearn")
            .selfStudyBloggingLink("https:/blog.com/study1")
            .createdAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .build();

        ResumeSelfStudy study2 = ResumeSelfStudy.builder()
            .selfStudyDate("2023-06-15")
            .selfStudytype("DevOps")
            .selfStudyTheme("Docker")
            .selfStudyPlatform("YouTube")
            .selfStudyBloggingLink("https:/blog.com/study2")
            .createdAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .build();

        resumeSelfStudyRepository.save(study1);
        resumeSelfStudyRepository.save(study2);
    }
}

