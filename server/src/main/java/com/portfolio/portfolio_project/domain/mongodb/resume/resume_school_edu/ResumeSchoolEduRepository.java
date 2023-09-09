package com.portfolio.portfolio_project.domain.mongodb.resume.resume_school_edu;

import org.springframework.data.mongodb.repository.Query;

import com.portfolio.portfolio_project.service.module.row_order_module.OrderableRepository;

public interface ResumeSchoolEduRepository extends OrderableRepository<ResumeSchoolEdu, String> {
    @Query(sort = "{'order' : -1}")
    ResumeSchoolEdu findTopByOrderByOrderDesc();
}