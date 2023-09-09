package com.portfolio.portfolio_project.repository_test.jpa_repository_test.myproject;

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

import com.portfolio.portfolio_project.domain.jpa.myproject.enums.ProjectRole;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role_code.MyProjectRoleCode;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role_code.MyProjectRoleCodeRepository;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MyProjectRoleCodeRepositoryTest {

    @Autowired
    private MyProjectRoleCodeRepository myProjectRoleCodeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE my_project_role_code_tb AUTO_INCREMENT = 1").executeUpdate();
        setUp();
    }

    @Test
    void selectAll() {
        List<MyProjectRoleCode> roles = myProjectRoleCodeRepository.findAll();
        Assertions.assertEquals(2, roles.size());
    }

    @Test
    void selectAndUpdate() {
        Optional<MyProjectRoleCode> optionalRole = myProjectRoleCodeRepository.findById(1L);
        if (optionalRole.isPresent()) {
            MyProjectRoleCode role = optionalRole.get();
            role.setProjectRole(ProjectRole.DevOps);

            MyProjectRoleCode updatedRole = entityManager.merge(role);
            Assertions.assertEquals(ProjectRole.DevOps, updatedRole.getProjectRole());
        } else {
            Assertions.fail("Select 테스트 에러");
        }
    }

    @Test
    void insertAndDelete() {
        MyProjectRoleCode newRole = MyProjectRoleCode.builder()
            .projectRole(ProjectRole.BackEnd)
            .build();

        MyProjectRoleCode persistedRole = entityManager.persist(newRole);
        Optional<MyProjectRoleCode> optionalRole = myProjectRoleCodeRepository.findById(persistedRole.getId());

        if (optionalRole.isPresent()) {
            entityManager.remove(persistedRole);
            Optional<MyProjectRoleCode> deletedRole = myProjectRoleCodeRepository.findById(persistedRole.getId());
            Assertions.assertTrue(deletedRole.isEmpty());
        } else {
            Assertions.fail("Insert 테스트 에러");
        }
    }

    private void setUp(){
        MyProjectRoleCode role1 = MyProjectRoleCode.builder()
            .projectRole(ProjectRole.BackEnd)
            .build();
        MyProjectRoleCode role2 = MyProjectRoleCode.builder()
            .projectRole(ProjectRole.FrontEnd)
            .build();

        entityManager.persist(role1);
        entityManager.persist(role2);
    }
}