package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isEmpty() ||
            account.getPassword() == null || account.getPassword().length() < 4 ||
            accountRepository.findByUsername(account.getUsername()) != null) {
            return Optional.empty();
        }
        return Optional.of(accountRepository.save(account));
    }
    
    public Optional<Account> loginAccount(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return Optional.of(existingAccount);
        }
        return Optional.empty();
    }

    public Optional<Account> getAccountById(Integer accountId) {
        return accountRepository.findById(accountId);
    }
}
