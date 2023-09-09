package com.portfolio.portfolio_project.integration_test.dummy;

import java.time.LocalDateTime;

import com.portfolio.portfolio_project.domain.jpa.skills.my_skill.MySkill;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCode;

public class MySkillDummy {
    
    public static MySkill newSkill1(MySkillTypeCode mySkillTypeCode) {

        return MySkill.builder()
                .id(1L)
                .skill("Java")
                .mySkillTypeCode(mySkillTypeCode)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static MySkill newSkill2(MySkillTypeCode mySkillTypeCode) {

        return MySkill.builder()
                .id(2L)
                .skill("CSS")
                .mySkillTypeCode(mySkillTypeCode)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
