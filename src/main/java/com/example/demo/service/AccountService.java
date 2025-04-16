package com.example.demo.service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Account;
import com.example.demo.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService implements UserDetailsService {
    private final AccountRepo accountRepo;

    @Autowired
    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account addAccount(Account account) {
        account.setId(UUID.randomUUID());
        return accountRepo.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    public Account updateAccount(Account account) {
        return accountRepo.save(account);
    }

    public Account getAccountById(UUID id) {
        return accountRepo.findAccountById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteAccount(UUID id) {
        accountRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepo.findAccountByEmail(username);
        if(account.isPresent()) {
            var accountObj = account.get();
            return User.builder()
                    .username(accountObj.getEmail())
                    .password(accountObj.getPassword())
                    .roles(getRoles(accountObj))
                    .build();
        } else {
            throw new UsernameNotFoundException("User " + username + " was not found");
        }
    }

    private String[] getRoles(Account accountObj) {
        if(accountObj.getRole() == null){
            return new String[]{"ROLE_USER"};
        }
        return accountObj.getRole().split(",");
    }
}
