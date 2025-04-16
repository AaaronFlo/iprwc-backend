package com.example.demo.repo;

import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepo extends JpaRepository<Account, UUID> {

    Optional<Account> findAccountById(UUID id);

    Optional<Account> findAccountByEmail(String Email);
}
