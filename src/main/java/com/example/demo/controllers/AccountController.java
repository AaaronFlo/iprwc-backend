package com.example.demo.controllers;


import com.example.demo.model.Account;
import com.example.demo.repo.AccountRepo;
import com.example.demo.repo.LoginRequest;
import com.example.demo.repo.LoginResponse;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable UUID id) {
        Account account = accountService.getAccountById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    // @PostMapping("/register")
    // public Account addAccount(@RequestBody Account account) {
    //     account.setPassword(passwordEncoder.encode(account.getPassword()));
    //     return accountRepo.save(account);
    // }

    @PostMapping("/register")
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
    // Check if email already exists
    if (accountRepo.findAccountByEmail(account.getEmail()).isPresent()) {
        return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
    }

    
    
    try {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account savedAccount = accountRepo.save(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
     } catch (Exception e) {
        return new ResponseEntity<>("Error creating account: " + e.getMessage(), 
            HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );
        
        if (authentication.isAuthenticated()) {
            Account account = accountRepo.findAccountByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                
            return ResponseEntity.ok(new LoginResponse(
                account.getId(),
                account.getEmail(),
                account.getRole()
            ));
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    } catch (AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
    }

    @PutMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        Account updatedAccount = accountService.updateAccount(account);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
