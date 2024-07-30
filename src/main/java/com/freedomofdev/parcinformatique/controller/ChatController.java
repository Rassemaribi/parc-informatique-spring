package com.freedomofdev.parcinformatique.controller;


import com.freedomofdev.parcinformatique.entity.ChatMessage;
import com.freedomofdev.parcinformatique.entity.Notification;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.repository.ChatMessageRepository;
import com.freedomofdev.parcinformatique.service.ChatMessageService;
import com.freedomofdev.parcinformatique.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin(origins = "https://parcinformatiquefodservicess.azurewebsites.net", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/messages")
public class ChatController {



    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private UserService userService;



    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) throws Exception {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setTimestamp(new Date());
        chatMessageRepository.save(message);
        User sender = userService.getUserById(message.getSenderId());


        // Publier une notification
        Notification notification = new Notification("Nouveau message de " + message.getSenderId());
        messagingTemplate.convertAndSend("/topic/notifications", notification);
        notification.setTimestamp(new Date());
        chatMessageService.createNotification(message.getReceiverId(),  message.getContent(), message.getSenderId(), sender.getNom()+' '+sender.getPrenom());
        return message;
    }


    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessage>> fetchMessages(@PathVariable Long senderId, @PathVariable Long receiverId) {
        List<ChatMessage> messages = chatMessageService.fetchMessages(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/notifications/{receiverId}")
    public ResponseEntity<List<Notification>> getNotificationsByReceiverId(@PathVariable Long receiverId) {
        List<Notification> notifications = chatMessageService.getNotificationsByReceiverId(receiverId);
        return ResponseEntity.ok(notifications);
    }




    @PutMapping("/notifications/{notificationId}/read")
    public ResponseEntity<Notification> markNotificationAsRead(@PathVariable Long notificationId) {
        Notification updatedNotification = chatMessageService.markNotificationAsRead(notificationId);
        System.out.println(updatedNotification);
        return ResponseEntity.ok(updatedNotification);
    }


}