package com.baobab.bookstore.order_service.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

import com.baobab.bookstore.order_service.AbstractIT;
import com.baobab.bookstore.order_service.dto.OrderSummary;
import com.baobab.bookstore.order_service.testdata.TestDataFactory;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author AmuDaDev
 * @created 02/10/2024
 */
@Sql("/test-orders.sql")
@Disabled("To be fixed")
class OrderControllerIntegrationTest extends AbstractIT {

    @Nested
    class CreateOrderTests {
        @Test
        void shouldCreateOrderSuccessfully() {
            mockGetProductByCode("P100", "The Hunger Games", new BigDecimal("34.0"));
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
                    .header("Authorization", "Bearer " + getToken())
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
            given().contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + getToken())
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        protected static void mockGetProductByCode(String code, String name, BigDecimal price) {
            WireMock.stubFor(WireMock.get(WireMock.urlMatching("/api/products/" + code))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                            .withStatus(200)
                            .withBody(
                                    """
                        {
                            "code": "%s",
                            "name": "%s",
                            "price": %f
                        }
                    """
                                            .formatted(code, name, price.doubleValue()))));
        }
    }

    @Nested
    class GetOrdersTests {
        @Test
        void shouldGetOrdersSummarySuccessfully() {
            List<OrderSummary> orderSummaries = given().when()
                    .header("Authorization", "Bearer " + getToken())
                    .get("/api/orders")
                    .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .as(new TypeRef<>() {});

            assertThat(orderSummaries).hasSize(2);
        }
    }

    @Nested
    class GetOrderByOrderNumberTests {
        String orderNumber = "order-123";

        @Test
        void shouldGetOrderSuccessfully() {
            given().when()
                    .header("Authorization", "Bearer " + getToken())
                    .get("/api/orders/{orderNumber}", orderNumber)
                    .then()
                    .statusCode(200)
                    .body("orderNumber", is(orderNumber))
                    .body("items.size()", is(2));
        }
    }
}
