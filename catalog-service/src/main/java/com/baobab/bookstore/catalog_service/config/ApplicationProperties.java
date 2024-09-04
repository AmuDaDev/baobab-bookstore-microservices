package com.baobab.bookstore.catalog_service.config;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

/**
 * @author AmuDaDev
 * @created 26/08/2024
 */
@Validated
@ConfigurationProperties(prefix = "catalog")
public record ApplicationProperties(@DefaultValue("10") @Min(1) int pageSize) {}
