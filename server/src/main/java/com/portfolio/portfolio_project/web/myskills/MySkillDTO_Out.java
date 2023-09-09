package com.portfolio.portfolio_project.web.myskills;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.portfolio.portfolio_project.domain.jpa.skills.enums.SkillType;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill.MySkill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MySkillDTO_Out {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindAllDTO {
        private Map<SkillType, List<String>> skillsMap;

        public static FindAllDTO fromEntity(List<MySkill> mySkillsPS) {
            Map<SkillType, List<String>> groupedSkills = mySkillsPS.stream()
                    .collect(Collectors.groupingBy(
                            skill -> skill.getMySkillTypeCode().getSkillType(),
                            Collectors.mapping(MySkill::getSkill, Collectors.toList())
                    ));

            return new FindAllDTO(groupedSkills);
        }
    }
}