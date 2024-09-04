package com.baobab.bookstore.catalog_service.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.baobab.bookstore.catalog_service.model.Product;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author AmuDaDev
 * @created 04/09/2024
 */
@DataJpaTest(
        properties = {
            "spring.test.database.replace=none",
            "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db",
        })
@Sql("/test-data.sql")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    // You don't need to test the methods provided by Spring Data JPA.
    // This test is to demonstrate how to write tests for the repository layer.
    @Test
    void getProducts() {
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(15);
    }

    @Test
    void findByCode() {
        Product product = productRepository.findByCode("P100").orElseThrow();
        assertThat(product.getCode()).isEqualTo("P100");
        assertThat(product.getName()).isEqualTo("The Hunger Games");
        assertThat(product.getDescription()).isEqualTo("Winning will make you famous. Losing means certain death...");
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("34.0"));
    }

    @Test
    void findByCode_NotExists() {
        assertThat(productRepository.findByCode("invalid_product_code")).isEmpty();
    }
}
