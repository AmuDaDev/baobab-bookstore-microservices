package com.baobab.bookstore.order_service.service;

import com.baobab.bookstore.order_service.dto.CreateOrderRequest;
import com.baobab.bookstore.order_service.dto.CreateOrderResponse;
import com.baobab.bookstore.order_service.dto.OrderStatus;
import com.baobab.bookstore.order_service.model.Order;
import com.baobab.bookstore.order_service.model.OrderItem;
import com.baobab.bookstore.order_service.repository.OrderRepository;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AmuDaDev
 * @created 12/09/2024
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @Override
    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) {
        // order
        Order newOrder = mapper.map(request, Order.class);
        newOrder.setUserName(userName);
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setStatus(OrderStatus.NEW);
        // items
        Set<OrderItem> items = request.getItems().stream()
                .map((element) -> mapper.map(element, OrderItem.class))
                .collect(Collectors.toSet());
        items.forEach(item -> item.setOrder(newOrder));
        newOrder.setItems(items);
        Order savedOrder = orderRepository.save(newOrder);
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }
}
