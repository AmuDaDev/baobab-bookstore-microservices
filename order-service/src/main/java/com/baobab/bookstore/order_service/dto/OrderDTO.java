package com.baobab.bookstore.order_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AmuDaDev
 * @created 26/06/2025
 */
@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
    String orderNumber;
    String userName;
    Set<OrderItemDto> items;
    Customer customer;
    Address address;
    OrderStatus status;
    String comments;
    LocalDateTime createdAt;
    BigDecimal totalAmount;
}
