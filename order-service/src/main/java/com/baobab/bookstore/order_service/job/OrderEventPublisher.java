package com.baobab.bookstore.order_service.job;

import com.baobab.bookstore.order_service.config.ApplicationProperties;
import com.baobab.bookstore.order_service.dto.OrderEventDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author AmuDaDev
 * @created 09/07/2025
 */
@Component
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    OrderEventPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void publishOrders(OrderEventDTO event) {
        this.send(properties.ordersQueue(), event);
    }

    private void send(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), routingKey, payload);
    }
}
