package com.baobab.bookstore.catalog_service.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.baobab.bookstore.catalog_service.AbstractIT;
import com.baobab.bookstore.catalog_service.dto.ProductDto;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author AmuDaDev
 * @created 04/09/2024
 */
@Sql("/test-data.sql")
class ProductControllerTest extends AbstractIT {

    @Test
    void getProducts() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("data", hasSize(10))
                .body("totalElements", is(15))
                .body("pageNumber", is(1))
                .body("totalPages", is(2))
                .body("isFirst", is(true))
                .body("isLast", is(false))
                .body("hasNext", is(true))
                .body("hasPrevious", is(false));
    }

    @Test
    void findByCode() {
        ProductDto productDto = given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", "P100")
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(ProductDto.class);

        assertThat(productDto.getCode()).isEqualTo("P100");
        assertThat(productDto.getName()).isEqualTo("The Hunger Games");
        assertThat(productDto.getDescription())
                .isEqualTo("Winning will make you famous. Losing means certain death...");
        assertThat(productDto.getPrice()).isEqualTo(new BigDecimal("34.0"));
    }

    @Test
    void findByCode_NotFound() {
        String code = "invalid_product_code";
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", code)
                .then()
                .statusCode(404)
                .body("status", is(404))
                .body("title", is("Product Not Found"))
                .body("detail", is("Product not found with code : '" + code + "'"));
    }
}
