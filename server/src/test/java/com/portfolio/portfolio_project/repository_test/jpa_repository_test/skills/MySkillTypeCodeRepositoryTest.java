package com.portfolio.portfolio_project.repository_test.jpa_repository_test.skills;

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
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCode;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCodeRepository;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MySkillTypeCodeRepositoryTest {

    @Autowired
    private MySkillTypeCodeRepository mySkillTypeCodeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE my_skill_type_code_tb AUTO_INCREMENT = 1").executeUpdate();
        setUp();
    }

    @Test
    void selectAll() {
        List<MySkillTypeCode> skills = mySkillTypeCodeRepository.findAll();
        Assertions.assertEquals(3, skills.size());
    }

    @Test
    void selectAndUpdate() {
        Optional<MySkillTypeCode> optionalSkill = mySkillTypeCodeRepository.findById(1L);
        if (optionalSkill.isPresent()) {
            MySkillTypeCode skill = optionalSkill.get();
            skill.setSkillType(SkillType.DevOps);

            MySkillTypeCode updatedSkill = entityManager.merge(skill);
            Assertions.assertEquals(SkillType.DevOps, updatedSkill.getSkillType());
        } else {
            Assertions.fail("Select 테스트 에러");
        }
    }

    @Test
    void insertAndDelete() {
        MySkillTypeCode newSkill = MySkillTypeCode.builder()
            .skillType(SkillType.ETC)
            .build();

        MySkillTypeCode persistedSkill = entityManager.persist(newSkill);
        Optional<MySkillTypeCode> optionalSkill = mySkillTypeCodeRepository.findById(persistedSkill.getId());

        if (optionalSkill.isPresent()) {
            entityManager.remove(persistedSkill);
            Optional<MySkillTypeCode> deletedSkill = mySkillTypeCodeRepository.findById(persistedSkill.getId());
            Assertions.assertTrue(deletedSkill.isEmpty());
        } else {
            Assertions.fail("Insert 테스트 에러");
        }
    }

    private void setUp(){
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
