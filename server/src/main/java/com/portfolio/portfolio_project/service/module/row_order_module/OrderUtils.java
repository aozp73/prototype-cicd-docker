package com.portfolio.portfolio_project.service.module.row_order_module;

public class OrderUtils {
    
    public static <T extends Orderable, ID> void setOrder(T entity, OrderableRepository<T, ID> repository) {
        T highestOrderEntity = repository.findTopByOrderByOrderDesc();

        int highestOrder = 0;
        if (highestOrderEntity != null) {
            highestOrder = highestOrderEntity.getOrder();
        }
        entity.setOrder(highestOrder + 1);
    }
}

