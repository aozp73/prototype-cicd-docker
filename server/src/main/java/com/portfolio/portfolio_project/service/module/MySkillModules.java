package com.portfolio.portfolio_project.service.module;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.portfolio.portfolio_project.core.exception.Exception400;
import com.portfolio.portfolio_project.core.exception.Exception500;
import com.portfolio.portfolio_project.domain.jpa.skills.enums.SkillType;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill.MySkill;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill.MySkillRepository;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCode;
import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCodeRepository;
import com.portfolio.portfolio_project.web.myskills.MySkillDTO_In;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MySkillModules {
    
    private final MySkillTypeCodeRepository mySkillTypeCodeRepository;
    private final MySkillRepository mySkillRepository;

    public void processSkills(List<MySkillDTO_In.PostDTO.SkillDTO> skillDTOs, String section, List<MySkill> toAdd, List<MySkill> toRemove) {
    if (skillDTOs == null) return;

    for (MySkillDTO_In.PostDTO.SkillDTO skillDTO : skillDTOs) {
        MySkillTypeCode mySkillTypeCodePS = mySkillTypeCodeRepository.findBySkillType(SkillType.valueOf(section)).orElseThrow(() -> {
            throw new Exception400("존재하지 않는 스킬타입입니다.");
        });

        // 새로 추가하는 스킬이라면 list에 모아서 한번에 saveAll()
        if ("added".equals(skillDTO.getStatus())) {
            Optional<MySkill> existingSkill = mySkillRepository.findBySkillAndMySkillTypeCode(skillDTO.getName(), mySkillTypeCodePS);    

            if (existingSkill.isPresent()) {
                throw new Exception400("동일한 내용의 스킬은 저장할 수 없습니다.");
            } else {
                MySkill mySkill = MySkill.builder()
                        .skill(skillDTO.getName())
                        .mySkillTypeCode(mySkillTypeCodePS)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
                toAdd.add(mySkill);
            }

        // 제거하는 스킬이라면 list에 모아서 한번에 deleteAll()
        } else if ("removed".equals(skillDTO.getStatus())) {
            Optional<MySkill> existingSkill;
            try {
                existingSkill = mySkillRepository.findBySkillAndMySkillTypeCode(skillDTO.getName(), mySkillTypeCodePS);
            } catch (Exception e) {
                throw new Exception500("삭제 과정에서 스킬 조회에 실패했습니다.");
            }
            
            toRemove.add(existingSkill.get());
        }
    }
}
}
