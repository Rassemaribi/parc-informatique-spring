package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.ChatMessage;
import com.freedomofdev.parcinformatique.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(ChatMessage message) {
        message.setTimestamp(new Date());
        return chatMessageRepository.save(message);
    }
    public List<ChatMessage> fetchMessages(Long senderId, Long recipientId) {
        return chatMessageRepository.findBySenderIdAndReceiverId(senderId, recipientId);
    }
}