package com.baobab.bookstore.order_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author AmuDaDev
 * @created 05/09/2024
 */
@ConfigurationProperties(prefix = "orders")
public record ApplicationProperties(
        String catalogServiceUrl,
        String orderEventsExchange,
        String newOrdersQueue,
        String deliveredOrdersQueue,
        String cancelledOrdersQueue,
        String errorOrdersQueue) {}
