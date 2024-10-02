package com.baobab.bookstore.order_service.testdata;

import static org.instancio.Select.field;

import com.baobab.bookstore.order_service.dto.AddressDto;
import com.baobab.bookstore.order_service.dto.CreateOrderRequest;
import com.baobab.bookstore.order_service.dto.CustomerDto;
import com.baobab.bookstore.order_service.dto.OrderItemDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.instancio.Instancio;

/**
 * @author AmuDaDev
 * @created 02/10/2024
 */
public class TestDataFactory {
    static final List<String> VALID_COUNTIES = List.of("India", "Germany");
    static final Set<OrderItemDto> VALID_ORDER_ITEMS =
            Set.of(new OrderItemDto("P100", "Product 1", new BigDecimal("25.50"), 1));
    static final Set<OrderItemDto> INVALID_ORDER_ITEMS =
            Set.of(new OrderItemDto("ABCD", "Product 1", new BigDecimal("25.50"), 1));

    public static CreateOrderRequest createValidOrderRequest() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(CustomerDto::getEmail), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                .set(field(CreateOrderRequest::getItems), VALID_ORDER_ITEMS)
                .generate(field(AddressDto::getCountry), gen -> gen.oneOf(VALID_COUNTIES))
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithInvalidCustomer() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(CustomerDto::getEmail), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .set(field(CustomerDto::getPhone), "")
                .generate(field(AddressDto::getCountry), gen -> gen.oneOf(VALID_COUNTIES))
                .set(field(CreateOrderRequest::getItems), VALID_ORDER_ITEMS)
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithInvalidDeliveryAddress() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(CustomerDto::getEmail), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .set(field(AddressDto::getCountry), "")
                .set(field(CreateOrderRequest::getItems), VALID_ORDER_ITEMS)
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithNoItems() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(CustomerDto::getEmail), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .generate(field(AddressDto::getCountry), gen -> gen.oneOf(VALID_COUNTIES))
                .set(field(CreateOrderRequest::getItems), Set.of())
                .create();
    }
}
