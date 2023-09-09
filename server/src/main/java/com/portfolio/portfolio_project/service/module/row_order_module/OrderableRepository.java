package com.portfolio.portfolio_project.service.module.row_order_module;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderableRepository<T extends Orderable, ID> extends MongoRepository<T, ID> {
    T findTopByOrderByOrderDesc();
}