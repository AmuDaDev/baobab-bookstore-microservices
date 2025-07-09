package com.baobab.bookstore.order_service.service;

import com.baobab.bookstore.order_service.dto.OrderEventDTO;
import com.baobab.bookstore.order_service.dto.OrderItemDto;
import com.baobab.bookstore.order_service.model.Order;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author AmuDaDev
 * @created 09/07/2025
 */
public class OrderEventMapper {
    static OrderEventDTO buildOrderEvent(Order order, String reason) {
        return new OrderEventDTO(
                UUID.randomUUID().toString(),
                order.getOrderNumber(),
                getOrderItems(order),
                order.getCustomer(),
                order.getDeliveryAddress(),
                reason,
                LocalDateTime.now());
    }

    private static Set<OrderItemDto> getOrderItems(Order order) {
        return order.getItems().stream()
                .map(item -> new OrderItemDto(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());
    }
}
