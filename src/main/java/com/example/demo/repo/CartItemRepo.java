package com.example.demo.repo;

import com.example.demo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepo extends JpaRepository<CartItem, UUID> {
}
