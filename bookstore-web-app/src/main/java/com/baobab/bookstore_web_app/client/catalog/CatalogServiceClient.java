package com.baobab.bookstore_web_app.client.catalog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface CatalogServiceClient {

    @GetExchange("/catalog/api/products")
    PagedResult<Product> getProducts(@RequestParam int pageNo);

    @GetExchange("/catalog/api/products/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code);
}
