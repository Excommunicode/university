package com.example.university.services;

import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.Product;
import com.example.university.repositories.ProductRepository;
import com.example.university.services.contract.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(Product product) {
        log.info("createProduct called: name='{}', price={}", product.getName(), product.getPrice());
        return productRepository.save(product);
    }

    @Override
    public Product findProduct(Long id) {
        log.info("findProduct called: id={}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        log.info("getAllProducts called: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product product) {
        log.info("updateProduct called: id={}, name='{}', price={}", id, product.getName(), product.getPrice());
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        return productRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        log.info("deleteProduct called: id={}", id);
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> getProductsByPriceGreaterThan(Double price, Pageable pageable) {
        log.info("getProductsByPriceGreaterThan called: price={}, page={}, size={}", price, pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findByPriceGreaterThan(price, pageable);
    }

    @Override
    public Page<Product> searchByName(String name, Pageable pageable) {
        log.info("searchByName called: name='{}', page={}, size={}", name, pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<Product> filterByPriceRange(Double minPrice, Double maxPrice, Pageable pageable) {
        log.info("filterByPriceRange called: minPrice={}, maxPrice={}, page={}, size={}", minPrice, maxPrice, pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    }
}
