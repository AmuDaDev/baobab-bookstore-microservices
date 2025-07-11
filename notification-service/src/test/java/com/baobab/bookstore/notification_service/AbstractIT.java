package com.baobab.bookstore.notification_service;

import com.baobab.bookstore.notification_service.service.NotificationService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author AmuDaDev
 * @created 10/07/2025
 */

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public abstract class AbstractIT {
    @MockitoBean
    protected NotificationService notificationService;
}
