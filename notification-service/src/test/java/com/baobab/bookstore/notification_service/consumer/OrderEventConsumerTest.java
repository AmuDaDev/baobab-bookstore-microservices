package com.baobab.bookstore.notification_service.consumer;

import com.baobab.bookstore.notification_service.AbstractIT;
import com.baobab.bookstore.notification_service.config.ApplicationProperties;
import com.baobab.bookstore.notification_service.dto.Address;
import com.baobab.bookstore.notification_service.dto.Customer;
import com.baobab.bookstore.notification_service.dto.OrderEventDTO;
import com.baobab.bookstore.notification_service.dto.OrderEventType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * @author AmuDaDev
 * @created 10/07/2025
 */
class OrderEventConsumerTest extends AbstractIT {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ApplicationProperties properties;

    @Test
    void shouldConsumeOrderEvents() {
        String orderNumber = UUID.randomUUID().toString();

        OrderEventDTO event = new OrderEventDTO(OrderEventType.ORDER_CREATED,UUID.randomUUID().toString(), orderNumber,
                Set.of(), getCustomer(), getAddress(), null, LocalDateTime.now());
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), properties.ordersQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
           verify(notificationService).sendNotification(any(OrderEventDTO.class));
        });
    }

    private Customer getCustomer(){
        Customer customer = new Customer();
        customer.setName("amu");
        customer.setEmail("amu@amu");
        customer.setPhone("123456789");
        return customer;
    }

    private Address getAddress(){
     Address address = new Address();
     address.setAddressLine1("280 funnel");
     address.setAddressLine2("sun park");
     address.setCity("Jozi");
     address.setState("GP");
     address.setCountry("SA");
     address.setZipCode("0123");
     return address;
    }
}