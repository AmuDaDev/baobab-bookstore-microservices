package com.baobab.bookstore.order_service.service;

import com.baobab.bookstore.order_service.dto.*;
import com.baobab.bookstore.order_service.exception.ResourceNotFoundException;
import com.baobab.bookstore.order_service.model.Order;
import com.baobab.bookstore.order_service.model.OrderItem;
import com.baobab.bookstore.order_service.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.*;
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
    private final OrderValidator orderValidator;
    private final ModelMapper mapper;
    private final OrderEventService orderEventService;
    private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("SOUTH AFRICA", "USA", "GERMANY", "UK");

    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderValidator orderValidator,
            ModelMapper mapper,
            OrderEventService orderEventService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.mapper = mapper;
        this.orderEventService = orderEventService;
    }

    @Override
    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request) {
        // Validate with catalog service
        orderValidator.validate(request);
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
        // Create OrderEvent
        OrderEventDTO orderCreatedEvent = OrderEventMapper.buildOrderEvent(savedOrder, null);
        orderEventService.save(orderCreatedEvent, OrderEventType.ORDER_CREATED);

        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }

    @Override
    public List<OrderSummary> findOrders(String userName) {
        return orderRepository.findByUserName(userName);
    }

    @Override
    public OrderDTO findUserOrder(String userName, String orderNumber) {
        Order order = orderRepository
                .findByUserNameAndOrderNumber(userName, orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderNumber", orderNumber));
        return convertToOrderDTO(order);
    }

    @Override
    public void processNewOrders() {
        List<Order> orders = orderRepository.findByStatus(OrderStatus.NEW);
        log.info("Found {} new orders to process", orders.size());
        for (Order order : orders) {
            this.process(order);
        }
    }

    private void process(Order order) {
        try {
            if (canBeDelivered(order)) {
                log.info("OrderNumber: {} can be delivered", order.getOrderNumber());
                order.setStatus(OrderStatus.DELIVERED);
                orderRepository.save(order);
                orderEventService.save(OrderEventMapper.buildOrderEvent(order, null), OrderEventType.ORDER_DELIVERED);

            } else {
                log.info("OrderNumber: {} can not be delivered", order.getOrderNumber());
                order.setStatus(OrderStatus.CANCELLED);
                orderRepository.save(order);
                orderEventService.save(
                        OrderEventMapper.buildOrderEvent(order, "Can't deliver to the location"),
                        OrderEventType.ORDER_CANCELLED);
            }
        } catch (RuntimeException e) {
            log.error("Failed to process Order with orderNumber: {}", order.getOrderNumber(), e);
            order.setStatus(OrderStatus.ERROR);
            orderRepository.save(order);
            orderEventService.save(
                    OrderEventMapper.buildOrderEvent(order, e.getMessage()), OrderEventType.ORDER_PROCESSING_FAILED);
        }
    }

    private boolean canBeDelivered(Order order) {
        return DELIVERY_ALLOWED_COUNTRIES.contains(
                order.getDeliveryAddress().getCountry().toUpperCase());
    }

    private OrderDTO convertToOrderDTO(Order order) {
        Set<OrderItemDto> orderItems = order.getItems().stream()
                .map(item -> new OrderItemDto(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderDTO(
                order.getOrderNumber(),
                order.getUserName(),
                orderItems,
                order.getCustomer(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getComments(),
                order.getCreatedAt(),
                getTotalAmount(orderItems));
    }

    private BigDecimal getTotalAmount(Set<OrderItemDto> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
