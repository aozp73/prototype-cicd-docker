package com.portfolio.portfolio_project.domain.jpa.skills.my_skill;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCode;

public interface MySkillRepository extends JpaRepository<MySkill, Long> {
    Optional<MySkill> findBySkillAndMySkillTypeCode(String skill, MySkillTypeCode mySkillTypeCode);
}