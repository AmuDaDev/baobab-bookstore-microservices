package com.baobab.bookstore.order_service.service;

import com.baobab.bookstore.order_service.dto.CreateOrderRequest;
import com.baobab.bookstore.order_service.dto.CreateOrderResponse;

/**
 * @author AmuDaDev
 * @created 12/09/2024
 */
public interface OrderService {
    CreateOrderResponse createOrder(String userName, CreateOrderRequest request);
}
