package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
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

    /**
     * The creation of the message will be successful if and only if the messageText
     * is not blank, is not over 255 characters, and postedBy refers to a real,
     * existing user
     * 
     * @param message
     * @return Message
     */
    @Transactional
    public Message createMessage(Message message) {
        String msgTxt = message.getMessageText();
        if (msgTxt == null || msgTxt.isBlank() || msgTxt.length() > 255) {
            throw new IllegalArgumentException("msg must exist and be less than 255 characters.");
        }
        if (!accountRepository.findById(message.getPostedBy()).isPresent()) {
            throw new IllegalStateException("Account does not exist.");
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id) {
        return messageRepository.findById(id).orElse(null);
    }

    public void deleteMessageById(Integer id) {
        if (id == null) {
            return;
        }
        messageRepository.deleteById(id);
    }

    public Integer updateMessageById(String newMessageText, Integer id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(newMessageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    public List<Message> getAllMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
