package com.example.demo.repo;

public record ProductRequest(
        String name,
        String description,
        float price,
        String imageUrl,
        boolean inStock
) {}
