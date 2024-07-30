package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.ChatMessage;
import com.freedomofdev.parcinformatique.entity.Notification;
import com.freedomofdev.parcinformatique.exception.ResourceNotFoundException;
import com.freedomofdev.parcinformatique.repository.ChatMessageRepository;
import com.freedomofdev.parcinformatique.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    public ChatMessage saveMessage(ChatMessage message) {
        message.setTimestamp(new Date());
        return chatMessageRepository.save(message);
    }
    public List<ChatMessage> fetchMessages(Long senderId, Long recipientId) {
        return chatMessageRepository.findBySenderIdAndReceiverId(senderId, recipientId);
    }
    public Notification createNotification(Long receiverId, String message, Long senderId, String senderName) {
        Notification notification = new Notification(message);
        notification.setTimestamp(new Date());
        notification.setReceiverId(receiverId);
        notification.setSenderId(senderId);
        notification.setSenderName(senderName);
        notification.setMessage(message);
        return notificationRepository.save(notification);
    }
    public List<Notification> getNotificationsByReceiverId(Long receiverId) {
        List<Notification> notifications = notificationRepository.findByReceiverId(receiverId);
        System.out.println(notifications);
        return notifications;
    }
    public Notification markNotificationAsRead(Long notificationId) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);
        System.out.println(notificationOptional);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notification.setIsRead(true);
            System.out.println(notification);
            return notificationRepository.save(notification);
        } else {
            throw new ResourceNotFoundException("Notification", "id", notificationId);
        }
    }
}