package com.baobab.bookstore.order_service.service;

import com.baobab.bookstore.order_service.dto.OrderEventDTO;
import com.baobab.bookstore.order_service.dto.OrderEventType;
import com.baobab.bookstore.order_service.job.OrderEventPublisher;
import com.baobab.bookstore.order_service.model.OrderEvent;
import com.baobab.bookstore.order_service.repository.OrderEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AmuDaDev
 * @created 09/07/2025
 */
@Service
@Transactional
public class OrderEventServiceImpl implements OrderEventService {
    private static final Logger log = LoggerFactory.getLogger(OrderEventServiceImpl.class);
    private final OrderEventRepository orderEventRepository;
    private final ObjectMapper objectMapper;
    private final OrderEventPublisher orderEventPublisher;

    public OrderEventServiceImpl(
            OrderEventRepository orderEventRepository,
            ObjectMapper objectMapper,
            OrderEventPublisher orderEventPublisher) {
        this.orderEventRepository = orderEventRepository;
        this.objectMapper = objectMapper;
        this.orderEventPublisher = orderEventPublisher;
    }

    @Override
    public void save(OrderEventDTO event, OrderEventType eventType) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventId(event.eventId());
        orderEvent.setEventType(eventType);
        orderEvent.setOrderNumber(event.orderNumber());
        orderEvent.setCreatedAt(event.createdAt());
        orderEvent.setPayload(toJsonPayload(event));
        this.orderEventRepository.save(orderEvent);
    }

    public void publishOrderEvents() {
        Sort sort = Sort.by("createdAt").ascending();
        List<OrderEvent> events = orderEventRepository.findAll(sort);
        log.info("Found {} Order Events to be published", events.size());
        for (OrderEvent event : events) {
            this.publishEvent(event);
            orderEventRepository.delete(event);
        }
    }

    private void publishEvent(OrderEvent event) {
        OrderEventType eventType = event.getEventType();
        OrderEventDTO orderEvent = fromJsonPayload(event.getPayload(), OrderEventDTO.class);
        switch (eventType) {
            case ORDER_CREATED:
                orderEventPublisher.publishNewOrders(orderEvent);
                break;
            case ORDER_DELIVERED:
                orderEventPublisher.publishDeliveredOrders(orderEvent);
                break;
            case ORDER_CANCELLED:
                orderEventPublisher.publishCancelledOrders(orderEvent);
                break;
            case ORDER_PROCESSING_FAILED:
                orderEventPublisher.publishErrorOrders(orderEvent);
                break;
            default:
                log.warn("Unsupported OrderEventType: {}", eventType);
        }
    }

    private String toJsonPayload(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T fromJsonPayload(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
