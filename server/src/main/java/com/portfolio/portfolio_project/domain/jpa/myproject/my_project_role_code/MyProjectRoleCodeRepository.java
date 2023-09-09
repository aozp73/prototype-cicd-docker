package com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role_code;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.portfolio_project.domain.jpa.myproject.enums.ProjectRole;

public interface MyProjectRoleCodeRepository extends JpaRepository<MyProjectRoleCode, Long> {
    Optional<MyProjectRoleCode> findByProjectRole(ProjectRole projectRole);
}