package com.example.demo.controllers;

import com.example.demo.model.CartItem;

import com.example.demo.repo.CartItemRepo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/cartitem")
public class CartItemController {
    private final CartItemRepo cartItemRepo;

    public CartItemController(CartItemRepo cartItemRepo) {
        this.cartItemRepo = cartItemRepo;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        List<CartItem> cartItems = cartItemRepo.findAll();
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    
}
