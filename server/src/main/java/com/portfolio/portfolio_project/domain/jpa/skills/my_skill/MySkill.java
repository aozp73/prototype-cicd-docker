package com.portfolio.portfolio_project.domain.jpa.skills.my_skill;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import com.portfolio.portfolio_project.domain.jpa.skills.my_skill_type_code.MySkillTypeCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "my_skill_tb")
public class MySkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("기술스택 페이지 - Java, HTML, Docker 등 스킬")
    @Column(name = "skill")
    private String skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("기술스택 페이지 - BackEnd, FrontEnd, DevOps, ETC 스킬코드")
    @JoinColumn(name = "my_skill_code_id")
    private MySkillTypeCode mySkillTypeCode;

    @Comment("기술스택 페이지 - 등록 시간")
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Comment("기술스택 페이지 - 수정 시간")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}