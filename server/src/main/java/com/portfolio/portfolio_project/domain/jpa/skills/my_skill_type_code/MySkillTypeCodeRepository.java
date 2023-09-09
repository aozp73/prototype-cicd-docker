package com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.portfolio_project.domain.jpa.skills.enums.SkillType;

public interface MySkillTypeCodeRepository extends JpaRepository<MySkillTypeCode, Long> {
    Optional<MySkillTypeCode> findBySkillType(SkillType skillType);
}