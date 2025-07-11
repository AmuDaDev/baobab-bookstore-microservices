package com.baobab.bookstore.notification_service.dto;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author AmuDaDev
 * @created 09/07/2025
 */
public record OrderEventDTO(
        OrderEventType eventType,
        String eventId,
        String orderNumber,
        Set<OrderItemDto> items,
        Customer customer,
        Address deliveryAddress,
        String reason,
        LocalDateTime createdAt) {}
