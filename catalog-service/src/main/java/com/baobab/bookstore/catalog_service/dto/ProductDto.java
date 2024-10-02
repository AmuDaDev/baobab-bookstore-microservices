package com.baobab.bookstore.catalog_service.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AmuDaDev
 * @created 04/09/2024
 */
@Getter
@Setter
public class ProductDto {
    private String code;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
}
