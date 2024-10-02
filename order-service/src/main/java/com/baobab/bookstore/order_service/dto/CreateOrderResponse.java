package com.baobab.bookstore.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AmuDaDev
 * @created 12/09/2024
 */
@Getter
@Setter
@AllArgsConstructor
public class CreateOrderResponse {
    String orderNumber;
}
