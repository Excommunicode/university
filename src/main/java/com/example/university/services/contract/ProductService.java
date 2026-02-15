package com.example.university.services.contract;

import com.example.university.models.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    Product findProduct(Long id);

    List<Product> getAllProducts();

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    List<Product> getProductsByPriceGreaterThan(Double price);

    List<Product> searchByName(String name);

    List<Product> filterByPriceRange(Double minPrice, Double maxPrice);
}
