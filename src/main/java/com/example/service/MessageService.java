package com.example.service;

import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service    
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
}
