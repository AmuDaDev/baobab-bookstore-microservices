package com.baobab.bookstore.catalog_service.dto;

import java.util.List;

/**
 * @author AmuDaDev
 * @created 26/08/2024
 */
public record PagedResult<T>(
        List<T> data,
        long totalElements,
        int pageNumber,
        int totalPages,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious) {}
