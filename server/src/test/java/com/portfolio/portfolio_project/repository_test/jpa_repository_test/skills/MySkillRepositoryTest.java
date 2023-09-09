package com.portfolio.portfolio_project.repository_test.jpa_repository_test.skills;

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

import com.portfolio.portfolio_project.domain.jpa.skills.enums.SkillType;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill.MySkill;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill.MySkillRepository;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCode;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCodeRepository;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MySkillRepositoryTest {

    @Autowired
    private MySkillRepository mySkillRepository;
    @Autowired
    private MySkillTypeCodeRepository mySkillTypeCodeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE my_skill_tb AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE my_skill_type_code_tb AUTO_INCREMENT = 1").executeUpdate();
        
        setUp_skillcode();
        List<MySkillTypeCode> mySkillTypeCodes = mySkillTypeCodeRepository.findAll();
        
        setUp(mySkillTypeCodes.get(0), mySkillTypeCodes.get(2));
    }

    @Test
    void selectAll() {
        List<MySkill> skills = mySkillRepository.findAll();
        Assertions.assertEquals(2, skills.size());
    }

    @Test
    void selectAndUpdate() {
        Optional<MySkill> optionalSkill = mySkillRepository.findById(1L);
        if (optionalSkill.isPresent()) {
            MySkill skill = optionalSkill.get();
            LocalDateTime localDateTime = LocalDateTime.now();
            skill.setUpdatedAt(localDateTime);

            MySkill updatedSkill = entityManager.merge(skill);
            Assertions.assertEquals(localDateTime, updatedSkill.getUpdatedAt());
        } else {
            Assertions.fail("Select 테스트 에러");
        }
    }

    @Test
    void insertAndDelete() {
        Optional<MySkillTypeCode> mySkillTypeCode = mySkillTypeCodeRepository.findById(2L);

        MySkill newSkill = MySkill.builder()
            .skill("HTML")
            .mySkillTypeCode(mySkillTypeCode.get())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        MySkill persistedSkill = entityManager.persist(newSkill);
        Optional<MySkill> optionalSkill = mySkillRepository.findById(persistedSkill.getId());

        if (optionalSkill.isPresent()) {
            entityManager.remove(persistedSkill);
            Optional<MySkill> deletedSkill = mySkillRepository.findById(persistedSkill.getId());
            Assertions.assertTrue(deletedSkill.isEmpty());
        } else {
            Assertions.fail("Insert 테스트 에러");
        }
    }

    private void setUp(MySkillTypeCode mySkillTypeCode1, MySkillTypeCode mySkillTypeCode3) {
        MySkill mySkill1 = MySkill.builder()
            .skill("Java")
            .mySkillTypeCode(mySkillTypeCode1)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        MySkill mySkill2 = MySkill.builder()
            .skill("Docker")
            .mySkillTypeCode(mySkillTypeCode3)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        
        entityManager.persist(mySkill1);
        entityManager.persist(mySkill2);
    }
    private void setUp_skillcode() {
        MySkillTypeCode skill1 = MySkillTypeCode.builder()
            .skillType(SkillType.BackEnd)
            .build();
        MySkillTypeCode skill2 = MySkillTypeCode.builder()
            .skillType(SkillType.FrontEnd)
            .build();
        MySkillTypeCode skill3 = MySkillTypeCode.builder()
            .skillType(SkillType.DevOps)
            .build();

        entityManager.persist(skill1);
        entityManager.persist(skill2);
        entityManager.persist(skill3);
    }
}

