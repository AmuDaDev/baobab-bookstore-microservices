package com.baobab.bookstore.catalog_service.service;

import com.baobab.bookstore.catalog_service.dto.PagedResult;
import com.baobab.bookstore.catalog_service.dto.ProductDto;

/**
 * @author AmuDaDev
 * @created 26/08/2024
 */
public interface ProductService {
    PagedResult<ProductDto> getProducts(int pageNo, String sortBy, String sortDir);

    ProductDto findByCode(String code);
}
