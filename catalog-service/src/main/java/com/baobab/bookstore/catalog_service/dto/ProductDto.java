package com.baobab.bookstore.catalog_service.dto;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author AmuDaDev
 * @created 04/09/2024
 */
@Data
public class ProductDto {
    private String code;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
}
