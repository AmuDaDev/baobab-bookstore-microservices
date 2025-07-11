package com.baobab.bookstore.notification_service.service;

import com.baobab.bookstore.notification_service.config.ApplicationProperties;
import com.baobab.bookstore.notification_service.dto.OrderEventDTO;
import com.baobab.bookstore.notification_service.dto.OrderEventType;
import com.baobab.bookstore.notification_service.model.OrderEvent;
import com.baobab.bookstore.notification_service.repository.OrderEventRepository;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AmuDaDev
 * @created 10/07/2025
 */

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final JavaMailSender emailSender;
    private final ApplicationProperties properties;
    private final OrderEventRepository orderEventRepository;

    public NotificationServiceImpl(JavaMailSender emailSender, ApplicationProperties properties, OrderEventRepository orderEventRepository) {
        this.emailSender = emailSender;
        this.properties = properties;
        this.orderEventRepository = orderEventRepository;
    }

    @Override
    public void sendNotification(OrderEventDTO orderEventDTO) {
        if (orderEventRepository.existsByEventId(orderEventDTO.eventId())) {
            log.warn("Received duplicate OrderCreatedEvent with eventId: {}", orderEventDTO.eventId());
            return;
        }

        log.info("Received new OrderCreatedEvent with orderNumber:{}: ", orderEventDTO.orderNumber());
        //Send an email notification
        sendEmail(orderEventDTO);

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventId(orderEventDTO.eventId());
        orderEventRepository.save(orderEvent);
    }

    private void sendEmail(OrderEventDTO event){
        OrderEventType eventType = event.eventType();
        String message = null;
        switch (eventType) {
            case ORDER_CREATED:
                message =
                        """
                        ===================================================
                        Order Created Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been created successfully.
        
                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                                .formatted(event.customer().getName(), event.orderNumber());
                log.info("\n{}", message);
                break;
            case ORDER_DELIVERED:
                message =
                        """
                        ===================================================
                        Order Delivered Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been delivered successfully.
        
                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                                .formatted(event.customer().getName(), event.orderNumber());
                log.info("\n{}", message);
                break;
            case ORDER_CANCELLED:
                message =
                        """
                        ===================================================
                        Order Cancelled Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been cancelled.
                        Reason: %s
        
                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                                .formatted(event.customer().getName(), event.orderNumber(), event.reason());
                log.info("\n{}", message);
                break;
            case ORDER_PROCESSING_FAILED:
                message =
                        """
                        ===================================================
                        Order Processing Failure Notification
                        ----------------------------------------------------
                        Hi Team,
                        The order processing failed for orderNumber: %s.
                        Reason: %s
        
                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                                .formatted(event.orderNumber(), event.reason());
                log.info("\n{}", message);
                break;
            default:
                log.warn("Unsupported OrderEventType: {}", eventType);
        }

        String subject = "Order " + eventType.name() + " Notification";
        sendEmail(event.customer().getEmail(), subject, message);
    }

    private void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(properties.supportEmail());
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content);
            emailSender.send(mimeMessage);
            log.info("Email sent to: {}", recipient);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
