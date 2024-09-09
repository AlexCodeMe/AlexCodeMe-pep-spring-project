package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class AccountService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    public AccountService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) throws IllegalArgumentException, IllegalStateException {
        if (account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new IllegalStateException("Account already exists.");
        }
        return accountRepository.save(account);
    }

    public Account login(Account account) throws RuntimeException {
        Account validatedAccount = accountRepository.findByUsernameAndPassword(account.getUsername(),
                account.getPassword());
        if (validatedAccount == null) {
            throw new RuntimeException("Invalid credentials.");
        } else {
            return validatedAccount;
        }
    }
}
