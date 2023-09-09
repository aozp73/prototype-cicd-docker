package com.portfolio.portfolio_project.domain.mongodb.resume.resume_certificate;

import org.springframework.data.mongodb.repository.Query;

import com.portfolio.portfolio_project.service.module.row_order_module.OrderableRepository;

public interface ResumeCertificateRepository extends OrderableRepository<ResumeCertificate, String> {
    @Query(sort = "{'order' : -1}")
    ResumeCertificate findTopByOrderByOrderDesc();
}