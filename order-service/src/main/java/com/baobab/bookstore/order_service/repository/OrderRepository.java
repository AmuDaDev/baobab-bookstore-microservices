package com.baobab.bookstore.order_service.repository;

import com.baobab.bookstore.order_service.dto.OrderStatus;
import com.baobab.bookstore.order_service.dto.OrderSummary;
import com.baobab.bookstore.order_service.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author AmuDaDev
 * @created 12/09/2024
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
            """
            select new com.baobab.bookstore.order_service.dto.OrderSummary(o.orderNumber, o.status)
            from Order o
            where o.userName = :userName
            """)
    List<OrderSummary> findByUserName(String userName);

    Optional<Order> findByUserNameAndOrderNumber(String userName, String orderNumber);

    List<Order> findByStatus(OrderStatus status);
}
