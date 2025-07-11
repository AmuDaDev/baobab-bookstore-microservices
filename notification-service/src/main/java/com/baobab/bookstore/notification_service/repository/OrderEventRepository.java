package com.baobab.bookstore.notification_service.repository;

import com.baobab.bookstore.notification_service.model.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author AmuDaDev
 * @created 10/07/2025
 */
public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {
    boolean existsByEventId(String eventId);
}
