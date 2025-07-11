package com.baobab.bookstore.notification_service.service;

import com.baobab.bookstore.notification_service.dto.OrderEventDTO;

/**
 * @author AmuDaDev
 * @created 10/07/2025
 */
public interface NotificationService {
    /**
     * Sends a notification for the given order event.
     *
     * @param orderEventDTO the order event data transfer object containing event details
     */
    void sendNotification(OrderEventDTO orderEventDTO);
}
