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
public class AddressDto {
    @NotBlank(message = "AddressLine1 is required")
    String addressLine1;

    String addressLine2;

    @NotBlank(message = "City is required")
    String city;

    @NotBlank(message = "State is required")
    String state;

    @NotBlank(message = "ZipCode is required")
    String zipCode;

    @NotBlank(message = "Country is required")
    String country;
}
