package com.baobab.bookstore.catalog_service.repository;

import com.baobab.bookstore.catalog_service.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author AmuDaDev
 * @created 26/08/2024
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);
}
