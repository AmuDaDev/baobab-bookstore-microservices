package com.baobab.bookstore.order_service.controller;

import com.baobab.bookstore.order_service.dto.CreateOrderRequest;
import com.baobab.bookstore.order_service.dto.CreateOrderResponse;
import com.baobab.bookstore.order_service.service.OrderService;
import com.baobab.bookstore.order_service.service.SecurityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author AmuDaDev
 * @created 12/09/2024
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    public OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userName = securityService.getLoginUserName();
        log.info("Creating order for user: {}", userName);
        return orderService.createOrder(userName, request);
    }
}
