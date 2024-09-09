package com.example.service;

import org.springframework.stereotype.Service;

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
}
