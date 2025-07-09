package com.baobab.bookstore.order_service.service;

import com.baobab.bookstore.order_service.dto.CreateOrderRequest;
import com.baobab.bookstore.order_service.dto.OrderItemDto;
import com.baobab.bookstore.order_service.dto.Product;
import com.baobab.bookstore.order_service.exception.InvalidOrderException;
import com.baobab.bookstore.order_service.service.client.ProductServiceClient;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author AmuDaDev
 * @created 03/10/2024
 */
@Component
public class OrderValidator {
    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);

    private final ProductServiceClient client;

    OrderValidator(ProductServiceClient client) {
        this.client = client;
    }

    void validate(CreateOrderRequest request) {
        Set<OrderItemDto> items = request.getItems();
        for (OrderItemDto item : items) {
            Product product = client.getProductByCode(item.getCode())
                    .orElseThrow(() -> new InvalidOrderException("Invalid Product code:" + item.getCode()));
            if (item.getPrice().compareTo(product.price()) != 0) {
                log.error(
                        "Product price not matching. Actual price:{}, received price:{}",
                        product.price(),
                        item.getPrice());
                throw new InvalidOrderException("Product price not matching");
            }
        }
    }
}
