package com.baobab.bookstore.order_service.service;

import com.baobab.bookstore.order_service.dto.OrderEventDTO;
import com.baobab.bookstore.order_service.dto.OrderEventType;

/**
 * @author AmuDaDev
 * @created 09/07/2025
 */
public interface OrderEventService {
    void save(OrderEventDTO event, OrderEventType eventType);

    void publishOrderEvents();
}
