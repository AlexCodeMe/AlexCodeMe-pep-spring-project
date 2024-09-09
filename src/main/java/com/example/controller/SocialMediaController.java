package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            Account registeredAccount = accountService.register(account);
            return ResponseEntity.ok(registeredAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            Account loggingInAccount = accountService.login(account);
            return ResponseEntity.ok(loggingInAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.ok(createdMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
        Message msg = messageService.getMessageById(messageId);
        if (msg == null) {
            return ResponseEntity.status(200).body(null);
        } else {
            messageService.deleteMessageById(messageId);
            return ResponseEntity.status(200).body(1);
        }
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> patchMessageById(@RequestBody Message message, @PathVariable Integer messageId) {
        String newMessageText = message.getMessageText();
        if (newMessageText == null || newMessageText.isBlank() || newMessageText.length() > 255) {
            return ResponseEntity.badRequest().body(null);
        }

        Integer updatedRows = messageService.updateMessageById(newMessageText, messageId);
        if (updatedRows > 0) {
            return ResponseEntity.ok(updatedRows);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessageByAccountId(@PathVariable Integer accountId) {
        return ResponseEntity.ok().body(messageService.getAllMessagesByAccountId(accountId));
    }
}
