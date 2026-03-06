package com.example.university.services.contract;

import com.example.university.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Product createProduct(Product product);

    Product findProduct(Long id);

    Page<Product> getAllProducts(Pageable pageable);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    Page<Product> getProductsByPriceGreaterThan(Double price, Pageable pageable);

    Page<Product> searchByName(String name, Pageable pageable);

    Page<Product> filterByPriceRange(Double minPrice, Double maxPrice, Pageable pageable);
}
