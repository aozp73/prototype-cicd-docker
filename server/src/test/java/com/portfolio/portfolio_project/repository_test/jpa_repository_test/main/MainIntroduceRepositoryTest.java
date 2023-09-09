package com.portfolio.portfolio_project.repository_test.jpa_repository_test.main;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import com.portfolio.portfolio_project.domain.jpa.main.main_introduce.MainIntroduce;
import com.portfolio.portfolio_project.domain.jpa.main.main_introduce.MainIntroduceRepository;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 테이터베이스 사용 (테스트용 스키마 생성)
public class MainIntroduceRepositoryTest {

    @Autowired
    private MainIntroduceRepository mainIntroduceRepository;
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE main_introduce_tb AUTO_INCREMENT = 1").executeUpdate();
        setUp();
    }

    @Test
    void selectAll() {
        List<MainIntroduce> mainIntroduces = mainIntroduceRepository.findAll();
        // Assertions.assertNotEquals(0, mainIntroduces.size());
        Assertions.assertEquals(2, mainIntroduces.size());
    }

    @Test
    void selectAndUpdate() {
        Optional<MainIntroduce> optionalMainIntroduce = mainIntroduceRepository.findById(1L);

        if (optionalMainIntroduce.isPresent()) {
            MainIntroduce mainIntroduce = optionalMainIntroduce.get();
            String updatedTitle = "update 테스트";
            mainIntroduce.setTitle(updatedTitle);
            
            MainIntroduce updatedMainIntroduce = entityManager.merge(mainIntroduce);
            Assertions.assertEquals(updatedTitle, updatedMainIntroduce.getTitle());
        } else {
            Assertions.fail("Select 테스트 에러");
        }
    }

    @Test
    void insertAndDelete() {
        MainIntroduce newMainIntroduce = MainIntroduce.builder()
            .introduceImgName("new image_name")
            .introduceImgUrl("new image_url")
            .title("new Title")
            .content("new Content")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        MainIntroduce persistedMainIntroduce = entityManager.persist(newMainIntroduce);
        Optional<MainIntroduce> optionalMainIntroduce = mainIntroduceRepository.findById(persistedMainIntroduce.getId());

        if (optionalMainIntroduce.isPresent()) {
            entityManager.remove(persistedMainIntroduce);
            Optional<MainIntroduce> deletedMainIntroduce = mainIntroduceRepository.findById(persistedMainIntroduce.getId());
            Assertions.assertTrue(deletedMainIntroduce.isEmpty());
        } else {
            Assertions.fail("Insert 테스트 에러");
        }
    }


    private void setUp(){

        MainIntroduce mainIntroduce1 = MainIntroduce.builder()
            .introduceImgName("image_name 1")
            .introduceImgUrl("image_url 1")
            .title("Title 1")
            .content("Content 1")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        MainIntroduce mainIntroduce2 = MainIntroduce.builder()
            .introduceImgName("image_name 2")
            .introduceImgUrl("image_url 2")
            .title("Title 2")
            .content("Content 2")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        entityManager.persist(mainIntroduce1);
        entityManager.persist(mainIntroduce2);
    }
}