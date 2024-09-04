package com.baobab.bookstore.catalog_service;

import org.springframework.boot.SpringApplication;

/**
 * @author AmuDaDev
 * @created 15/08/2024
 */
public class TestCatalogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.from(CatalogServiceApplication::main)
                .with(ContainersConfig.class)
                .run(args);
    }
}
