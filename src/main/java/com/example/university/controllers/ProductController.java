package com.example.university.controllers;

import com.example.university.dto.ProductCreateDto;
import com.example.university.models.Product;
import com.example.university.services.contract.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductCreateDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProduct(id));
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/filter/price-greater-than")
    public ResponseEntity<Page<Product>> getProductsByPriceGreaterThan(
            @RequestParam Double price,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(productService.getProductsByPriceGreaterThan(price, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchByName(
            @RequestParam String name,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(productService.searchByName(name, pageable));
    }

    @GetMapping("/filter/price-range")
    public ResponseEntity<Page<Product>> filterByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(productService.filterByPriceRange(minPrice, maxPrice, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @Valid @RequestBody ProductCreateDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
