package com.baobab.bookstore.order_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AmuDaDev
 * @created 12/09/2024
 */
@Getter
@Setter
public class CreateOrderRequest {
    @Valid
    @NotEmpty(message = "Items cannot be empty")
    Set<OrderItemDto> items;

    @Valid
    CustomerDto customer;

    @Valid
    AddressDto deliveryAddress;
}
