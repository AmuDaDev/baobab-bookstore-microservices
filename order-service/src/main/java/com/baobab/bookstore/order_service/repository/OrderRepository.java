package com.baobab.bookstore.order_service.repository;

import com.baobab.bookstore.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author AmuDaDev
 * @created 12/09/2024
 */
public interface OrderRepository extends JpaRepository<Order, Long> {}
