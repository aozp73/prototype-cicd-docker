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

import com.portfolio.portfolio_project.domain.jpa.myproject.enums.ProjectRole;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProject;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProjectRepository;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role.MyProjectRole;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role.MyProjectRoleRepository;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role_code.MyProjectRoleCode;
import com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role_code.MyProjectRoleCodeRepository;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MyProjectRoleRepositoryTest {

    @Autowired
    private MyProjectRoleRepository myProjectRoleRepository;
    @Autowired
    private MyProjectRoleCodeRepository myProjectRoleCodeRepository;
    @Autowired
    private MyProjectRepository myProjectRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE my_project_tb AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE my_project_role_tb AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE my_project_role_code_tb AUTO_INCREMENT = 1").executeUpdate();

        setUp_project();
        setUp_rolecode();
        List<MyProject> myProjects = myProjectRepository.findAll();
        List<MyProjectRoleCode> myProjectRoleCodes = myProjectRoleCodeRepository.findAll();
  
        setUp(myProjects.get(0), myProjects.get(1), myProjectRoleCodes.get(0), myProjectRoleCodes.get(1));
    }

    @Test
    void selectAll() {
        List<MyProjectRole> roles = myProjectRoleRepository.findAll();
        Assertions.assertEquals(2, roles.size());
    }

    @Test
    void selectAndUpdate() {
        Optional<MyProjectRole> optionalRole = myProjectRoleRepository.findById(1L);
        if (optionalRole.isPresent()) {
            MyProjectRole role = optionalRole.get();
            LocalDateTime localDateTime = LocalDateTime.now();
            role.setUpdatedAt(localDateTime);

            MyProjectRole updatedRole = entityManager.merge(role);
            Assertions.assertEquals(localDateTime, updatedRole.getUpdatedAt());
        } else {
            Assertions.fail("Select 테스트 에러");
        }
    }

    @Test
    void insertAndDelete() {
        Optional<MyProject> project = myProjectRepository.findById(1L); 
        Optional<MyProjectRoleCode> roleCode = myProjectRoleCodeRepository.findById(3L); 

        MyProjectRole newRole = MyProjectRole.builder()
            .project(project.get())
            .roleCode(roleCode.get())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        MyProjectRole persistedRole = entityManager.persist(newRole);
        Optional<MyProjectRole> optionalRole = myProjectRoleRepository.findById(persistedRole.getId());

        if (optionalRole.isPresent()) {
            entityManager.remove(persistedRole);
            Optional<MyProjectRole> deletedRole = myProjectRoleRepository.findById(persistedRole.getId());
            Assertions.assertTrue(deletedRole.isEmpty());
        } else {
            Assertions.fail("Insert 테스트 에러");
        }
    }

    private void setUp(MyProject myProject1, MyProject myProject2, MyProjectRoleCode code1, MyProjectRoleCode code2) {
        MyProjectRole role1 = MyProjectRole.builder()
            .project(myProject1)
            .roleCode(code1)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        MyProjectRole role2 = MyProjectRole.builder()
            .project(myProject2)
            .roleCode(code2)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        entityManager.persist(role1);
        entityManager.persist(role2);
    }

    private void setUp_project() {
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
            .member(1)
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(20))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        
        entityManager.persist(myProject1);
        entityManager.persist(myProject2);
    }

    private void setUp_rolecode(){
        MyProjectRoleCode role1 = MyProjectRoleCode.builder()
            .projectRole(ProjectRole.BackEnd)
            .build();
        MyProjectRoleCode role2 = MyProjectRoleCode.builder()
            .projectRole(ProjectRole.FrontEnd)
            .build();
        MyProjectRoleCode role3 = MyProjectRoleCode.builder()
            .projectRole(ProjectRole.DevOps)
            .build();

        entityManager.persist(role1);
        entityManager.persist(role2);
        entityManager.persist(role3);
    }
}