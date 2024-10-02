package com.baobab.bookstore.catalog_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.*;

/**
 * @author AmuDaDev
 * @created 26/08/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "product",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Product code is required")
    private String code;

    @NotEmpty(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    private String description;
    private String imageUrl;

    @NotNull(message = "Product price is required") @DecimalMin("0.1")
    @Column(nullable = false)
    private BigDecimal price;
}
