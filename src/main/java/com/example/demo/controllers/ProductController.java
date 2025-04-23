package com.example.demo.controllers;

import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepo;
import com.example.demo.repo.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://http://iprwcfrontend.s3-website.eu-north-1.amazonaws.com/")
@RequestMapping(path = "api/product")
public class ProductController {
    private final ProductRepo productRepo;

    @Autowired
    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(path = "find/{id}")
    public Product getProductById(@PathVariable("id") UUID id) {
        return productRepo.findById(id).orElse(null);
    }

//    @PostMapping("/add")
//    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
//        return new ResponseEntity<>(productRepo.save(product), HttpStatus.CREATED);
//    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequest req) {
        Product p = new Product(
                req.name(), req.price(), req.description(),
                req.imageUrl(), req.inStock()
        );
        return new ResponseEntity<>(productRepo.save(p), HttpStatus.CREATED);
    }

//    @PutMapping("/update")
//    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
//        Product newProduct = productRepo.save(product);
//        return new ResponseEntity<>(newProduct, HttpStatus.OK);
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductRequest req) {

        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found"));

        existing.setName(req.name());
        existing.setDescription(req.description());
        existing.setPrice(req.price());
        existing.setImageUrl(req.imageUrl());
        existing.setInStock(req.inStock());

        // Hibernate auto-detects managed changes and UPDATEs with correct version
        return new ResponseEntity<>(productRepo.save(existing), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") UUID id) {
        productRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
