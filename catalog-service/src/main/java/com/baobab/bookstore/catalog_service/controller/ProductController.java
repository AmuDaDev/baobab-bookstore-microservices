package com.baobab.bookstore.catalog_service.controller;

import com.baobab.bookstore.catalog_service.dto.PagedResult;
import com.baobab.bookstore.catalog_service.dto.ProductDto;
import com.baobab.bookstore.catalog_service.service.ProductService;
import com.baobab.bookstore.catalog_service.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author AmuDaDev
 * @created 26/08/2024
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<ProductDto> getProducts(
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir) {
        log.info("Fetching products for page: {}", pageNo);
        return productService.getProducts(pageNo, sortBy, sortDir);
    }

    @GetMapping("/{code}")
    ResponseEntity<ProductDto> findByCode(@PathVariable String code) {
        log.info("Fetching product for code: {}", code);
        return ResponseEntity.ok(productService.findByCode(code));
    }
}
