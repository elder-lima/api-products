package com.api.products.controllers;

import com.api.products.dtos.ProductRecordDto;
import com.api.products.models.Product;
import com.api.products.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Product> saveProducts(@RequestBody @Valid ProductRecordDto productRecordDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productRecordDto, product);
        Product obj = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> obj = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        Optional<Product> obj = productRepository.findById(id);
        if (obj.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        BeanUtils.copyProperties(productRecordDto, product.get());
        Product obj = productRepository.save(product.get());
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(product.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }

}
