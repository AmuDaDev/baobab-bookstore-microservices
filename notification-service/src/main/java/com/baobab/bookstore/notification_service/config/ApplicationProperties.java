package com.baobab.bookstore.notification_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author AmuDaDev
 * @created 05/09/2024
 */
@ConfigurationProperties(prefix = "notification")
public record ApplicationProperties(
        String orderEventsExchange,
        String ordersQueue,
        String supportEmail) {}
