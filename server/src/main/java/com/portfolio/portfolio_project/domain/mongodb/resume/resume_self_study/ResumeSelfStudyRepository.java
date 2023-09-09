package com.portfolio.portfolio_project.domain.mongodb.resume.resume_self_study;

import org.springframework.data.mongodb.repository.Query;

import com.portfolio.portfolio_project.service.module.row_order_module.OrderableRepository;

public interface ResumeSelfStudyRepository extends OrderableRepository<ResumeSelfStudy, String> {
    @Query(sort = "{'order' : -1}")
    ResumeSelfStudy findTopByOrderByOrderDesc();
}