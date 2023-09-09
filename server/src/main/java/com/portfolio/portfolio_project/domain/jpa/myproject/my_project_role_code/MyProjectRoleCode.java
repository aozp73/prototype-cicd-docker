package com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role_code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import com.portfolio.portfolio_project.domain.jpa.myproject.enums.ProjectRole;

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
@Table(name = "my_project_role_code_tb")
public class MyProjectRoleCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Comment("프로젝트 페이지 - BackEnd, FrontEnd, DevOps")
    @Enumerated(EnumType.STRING)
    @Column(name = "project_role")
    private ProjectRole projectRole;
}
