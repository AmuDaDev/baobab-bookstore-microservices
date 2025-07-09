package com.baobab.bookstore.order_service.service;

import com.baobab.bookstore.order_service.dto.CreateOrderRequest;
import com.baobab.bookstore.order_service.dto.CreateOrderResponse;
import com.baobab.bookstore.order_service.dto.OrderDTO;
import com.baobab.bookstore.order_service.dto.OrderSummary;
import java.util.List;

/**
 * @author AmuDaDev
 * @created 12/09/2024
 */
public interface OrderService {
    CreateOrderResponse createOrder(String userName, CreateOrderRequest request);

    List<OrderSummary> findOrders(String userName);

    OrderDTO findUserOrder(String userName, String orderNumber);

    void processNewOrders();
}
