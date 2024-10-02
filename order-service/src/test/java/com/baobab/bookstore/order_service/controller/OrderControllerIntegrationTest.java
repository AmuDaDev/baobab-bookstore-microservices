package com.baobab.bookstore.order_service.controller;

import com.baobab.bookstore.order_service.AbstractIT;
import com.baobab.bookstore.order_service.testdata.TestDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @author AmuDaDev
 * @created 02/10/2024
 */
@Sql("/test-orders.sql")
class OrderControllerIntegrationTest extends AbstractIT {

    @Nested
    class CreateOrderTests {
        @Test
        void shouldCreateOrderSuccessfully() {
            var payload =
                    """
                        {
                            "customer":{
                               "email":"amu@amu.com",
                               "name":"Amu",
                               "phone":"0792669018"
                            },
                            "deliveryAddress":{
                               "addressLine1":"121 Funnel",
                               "addressLine2":"funnel",
                               "city":"Boksburg",
                               "country":"South Africa",
                               "state":"Gauteng",
                               "zipCode":"1459"
                            },
                            "items":[
                               {
                                  "code":"P100",
                                  "name":"The Hunger Games",
                                  "price":34.0,
                                  "quantity":1
                               }
                            ]
                         }
                    """;
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
            System.out.println(payload);
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}