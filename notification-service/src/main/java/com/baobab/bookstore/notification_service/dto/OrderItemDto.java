package com.baobab.bookstore.notification_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author AmuDaDev
 * @created 12/09/2024
 */
@Getter
@Setter
@AllArgsConstructor
public class OrderItemDto {
    @NotBlank(message = "Code is required")
    String code;

    @NotBlank(message = "Name is required")
    String name;

    @NotNull(message = "Price is required") BigDecimal price;

    @NotNull(message = "Quantity is required") @Min(value = 1, message = "Min quantity must be 1")
    Integer quantity;
}
