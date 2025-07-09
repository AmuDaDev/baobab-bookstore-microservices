package com.baobab.bookstore.order_service.repository;

import com.baobab.bookstore.order_service.model.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author AmuDaDev
 * @created 09/07/2025
 */
public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {}
