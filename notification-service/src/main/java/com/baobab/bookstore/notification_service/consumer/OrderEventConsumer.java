package com.baobab.bookstore.notification_service.consumer;

import com.baobab.bookstore.notification_service.dto.OrderEventDTO;
import com.baobab.bookstore.notification_service.service.NotificationService;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AmuDaDev
 * @created 10/07/2025
 */
@Component
@Transactional
public class OrderEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);
    private final NotificationService notificationService;

    public OrderEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${notification.orders-queue}")
    @SchedulerLock(name = "consumeOrderEvents")
    public void consumeOrderEvents(OrderEventDTO event) {
        LockAssert.assertLocked();
        log.info("Received a {} message with orderNumber:{}: ", event.eventType().name(), event.orderNumber());
        notificationService.sendNotification(event);
    }
}
