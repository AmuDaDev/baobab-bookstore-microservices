package com.baobab.bookstore.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AmuDaDev
 * @created 12/09/2024
 */
@Getter
@Setter
public class CustomerDto {
    @NotBlank(message = "Customer Name is required")
    String name;

    @NotBlank(message = "Customer email is required")
    String email;

    @NotBlank(message = "Customer Phone number is required")
    String phone;
}
