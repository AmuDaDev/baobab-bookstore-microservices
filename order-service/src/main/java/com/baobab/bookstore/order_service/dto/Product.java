package com.baobab.bookstore.order_service.dto;

import java.math.BigDecimal;

/**
 * @author AmuDaDev
 * @created 03/10/2024
 */
public record Product(String code, String name, String description, String imageUrl, BigDecimal price) {}
