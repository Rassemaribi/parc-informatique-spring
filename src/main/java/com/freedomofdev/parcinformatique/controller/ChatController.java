package com.freedomofdev.parcinformatique.controller;


import com.freedomofdev.parcinformatique.entity.ChatMessage;
import com.freedomofdev.parcinformatique.repository.ChatMessageRepository;
import com.freedomofdev.parcinformatique.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin(origins = "https://parcinformatiquefodservices.azurewebsites.net", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/messages")
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;



    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) throws Exception {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setTimestamp(new Date());
        chatMessageRepository.save(message);
        return message;
    }


    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessage>> fetchMessages(@PathVariable Long senderId, @PathVariable Long receiverId) {
        List<ChatMessage> messages = chatMessageService.fetchMessages(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }
}