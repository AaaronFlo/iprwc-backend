package com.example.demo.controllers;

import com.example.demo.model.CartItem;
import com.example.demo.repo.CartItemRepo;

import java.util.List;
import java.util.UUID;

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

    @GetMapping(path = "find/{id}")
    public CartItem getProductById(@PathVariable("id") UUID id) {
        return cartItemRepo.findById(id).orElse(null);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addProduct(@RequestBody CartItem cartItem) {
        return new ResponseEntity<>(cartItemRepo.save(cartItem), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItem> updateProduct(@RequestBody CartItem cartItem) {
        CartItem newCartItem = cartItemRepo.save(cartItem);
        return new ResponseEntity<>(newCartItem, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CartItem> deleteProduct(@PathVariable("id") UUID id) {
        cartItemRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
