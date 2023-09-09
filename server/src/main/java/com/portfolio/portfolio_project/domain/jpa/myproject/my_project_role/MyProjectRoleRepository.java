package com.portfolio.portfolio_project.domain.jpa.myproject.my_project_role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.portfolio_project.domain.jpa.myproject.my_project.MyProject;

public interface MyProjectRoleRepository extends JpaRepository<MyProjectRole, Long> {
    List<MyProjectRole> findAllByProject(MyProject project);
}