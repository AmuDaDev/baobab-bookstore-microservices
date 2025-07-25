package com.baobab.bookstore_web_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BookstoreWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreWebAppApplication.class, args);
    }
}
