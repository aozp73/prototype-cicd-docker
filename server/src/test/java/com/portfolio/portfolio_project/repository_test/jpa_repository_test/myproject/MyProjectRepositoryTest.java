package com.portfolio.portfolio_project.repository_test.jpa_repository_test.myproject;

import java.time.LocalDate;
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

import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProject;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProjectRepository;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MyProjectRepositoryTest {

    @Autowired
    private MyProjectRepository myProjectRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE my_project_tb AUTO_INCREMENT = 1").executeUpdate();
        setUp();
    }

    @Test
    void selectAll() {
        List<MyProject> myProjects = myProjectRepository.findAll();
        Assertions.assertEquals(2, myProjects.size());
    }

    @Test
    void selectAndUpdate() {
        Optional<MyProject> optionalMyProject = myProjectRepository.findById(1L);
        if (optionalMyProject.isPresent()) {
            MyProject myProject = optionalMyProject.get();
            String updatedTitle = "update 테스트";
            myProject.setProjectName(updatedTitle);

            MyProject updatedMyProject = entityManager.merge(myProject);
            Assertions.assertEquals(updatedTitle, updatedMyProject.getProjectName());
        } else {
            Assertions.fail("Select 테스트 에러");
        }
    }

    @Test
    void insertAndDelete() {
        MyProject newMyProject = MyProject.builder()
            .projectName("New Project Title")
            .member(3)
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(30))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        MyProject persistedMyProject = entityManager.persist(newMyProject);
        Optional<MyProject> optionalMyProject = myProjectRepository.findById(persistedMyProject.getId());

        if (optionalMyProject.isPresent()) {
            entityManager.remove(persistedMyProject);
            Optional<MyProject> deletedMyProject = myProjectRepository.findById(persistedMyProject.getId());
            Assertions.assertTrue(deletedMyProject.isEmpty());
        } else {
            Assertions.fail("Insert 테스트 에러");
        }
    }

    private void setUp(){
        MyProject myProject1 = MyProject.builder()
            .projectName("Project Title 1")
            .member(3)
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(10))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        MyProject myProject2 = MyProject.builder()
            .projectName("Project Title 2")
            .member(4)
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(20))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        entityManager.persist(myProject1);
        entityManager.persist(myProject2);
    }
}
