package com.freedomofdev.parcinformatique.service;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class PushNotificationService {

    @Value("${vapid.public.key}")
    private String publicKey;

    @Value("${vapid.private.key}")
    private String privateKey;

    private Map<String, Subscription> subscriptions = new HashMap<>();

    public void saveSubscription(String userId, Subscription subscription) {
        subscriptions.put(userId, subscription);
    }

    public Subscription getSubscription(String userId) {
        return subscriptions.get(userId);
    }

    public void sendPushNotification(Subscription subscription, String message) {
        try {
            PushService pushService = new PushService()
                    .setPublicKey(publicKey)
                    .setPrivateKey(privateKey);

            Notification notification = new Notification(
                    subscription,
                    new String(message.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)
            );

            pushService.send(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}