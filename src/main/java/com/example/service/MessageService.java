package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageRepository messageRepository;

    public Optional<Message> createMessage(Message message) {
        Optional<Account> account = accountService.getAccountById(message.getPostedBy());
        if (message.getMessageText() == null || message.getMessageText().isBlank()
                || message.getMessageText().length() > 255 || account.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(messageRepository.save(message));
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }

    public int deleteMessageById(Integer messageId) {
        boolean message = messageRepository.existsById(messageId);
        if (message) {
            messageRepository.deleteById(messageId);
            return 1;
        } else {
            return 0;
        }
    }

    public boolean updateMessageById(Integer messageId, String newText){
        System.out.println("___________________" + newText);
        if(newText == null || newText.isEmpty() || newText.isBlank() || newText.length() > 255) {
            return false;
        }
        Optional<Message> message= messageRepository.findById(messageId);
        
        if(message.isPresent()){
            message.get().setMessageText(newText);
            messageRepository.save(message.get());
            return true;
        } 
        return false;
    }

    public List<Message> getMessagesByUser(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
    }

}
