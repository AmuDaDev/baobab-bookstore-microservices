package com.baobab.bookstore.catalog_service.service;

import com.baobab.bookstore.catalog_service.config.ApplicationProperties;
import com.baobab.bookstore.catalog_service.dto.PagedResult;
import com.baobab.bookstore.catalog_service.dto.ProductDto;
import com.baobab.bookstore.catalog_service.exception.ResourceNotFoundException;
import com.baobab.bookstore.catalog_service.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AmuDaDev
 * @created 26/08/2024
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ApplicationProperties properties;
    private final ModelMapper mapper;

    ProductServiceImpl(ProductRepository productRepository, ApplicationProperties properties, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.properties = properties;
        this.mapper = mapper;
    }

    @Override
    public PagedResult<ProductDto> getProducts(int pageNo, String sortBy, String sortDir) {
        // Sort
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        // Page
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, properties.pageSize(), sort);
        Page<ProductDto> productsPage =
                productRepository.findAll(pageable).map((element) -> mapper.map(element, ProductDto.class));

        return new PagedResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1,
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious());
    }

    @Override
    public ProductDto findByCode(String code) {
        return productRepository
                .findByCode(code)
                .map((element) -> mapper.map(element, ProductDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "code", code));
    }
}
