package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
@Controller
public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Account> registerHandler(@RequestBody Account newAccount) {
        Optional<Account> registeredAccount = accountService.registerAccount(newAccount);
        if (registeredAccount.isPresent()) {
            return ResponseEntity.ok(registeredAccount.get());
        } else if (accountService.loginAccount(newAccount).isPresent()) {
            return ResponseEntity.status(409).build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Account> loginHandler(@RequestBody Account account) {
        Optional<Account> loginAccount = accountService.loginAccount(account);
        if (loginAccount.isPresent()) {
            return ResponseEntity.ok(loginAccount.get());
        } else {
            return ResponseEntity.status(401).build();
        }

    }

    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<Message> createMessageHandler(@RequestBody Message message) {
        Optional<Message> newMsg = messageService.createMessage(message);
        if (newMsg.isPresent()) {
            return ResponseEntity.ok(newMsg.get());
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessagesHandler() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Message> getMessageByIdHandler(@PathVariable Integer messageId) {
        Optional<Message> message = messageService.getMessageById(messageId);
        if (message.isPresent()) {
            return ResponseEntity.ok(message.get());
        }
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Integer> deleteMessageByIdHandler(@PathVariable Integer messageId) {
        int rowsAffected = messageService.deleteMessageById(messageId);
        if (rowsAffected == 1) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.status(200).build();
        }
    }

    @PatchMapping("/messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Integer> updateMessageByIdHandler(@PathVariable Integer messageId, @RequestBody String newText){
        boolean updatedMessage = messageService.updateMessageById(messageId, newText);
        
        if(updatedMessage){
            return ResponseEntity.ok(1);
        }else{
            return ResponseEntity.status(400).build();
        }

    }

    @GetMapping("/accounts/{accountId}/messages")
    @ResponseBody
    public ResponseEntity<List<Message>> getMessagesByUserHandler(@PathVariable Integer accountId){
        List<Message> messages = messageService.getMessagesByUser(accountId);
        return ResponseEntity.ok(messages);    
    }

}
