package com.portfolio.portfolio_project.domain.mongodb.resume.resume_academy_edu;

import org.springframework.data.mongodb.repository.Query;

import com.portfolio.portfolio_project.service.module.row_order_module.OrderableRepository;

public interface ResumeAcademyEduRepository extends OrderableRepository<ResumeAcademyEdu, String> {
    @Query(sort = "{'order' : -1}")
    ResumeAcademyEdu findTopByOrderByOrderDesc();
}