package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.service.PushNotificationService;
import nl.martijndwars.webpush.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/push")
public class PushNotificationController {

    @Autowired
    private final PushNotificationService pushNotificationService;

    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody Subscription subscription, @RequestParam String userId) {
        // Enregistrer l'abonnement avec l'identifiant de l'utilisateur
        pushNotificationService.saveSubscription(userId, subscription);
    }

    @PostMapping("/send")
    public void sendNotification(@RequestBody NotificationRequest request, @RequestParam String userId) {
        // Récupérer l'abonnement enregistré
        Subscription subscription = pushNotificationService.getSubscription(userId);

        pushNotificationService.sendPushNotification(subscription, request.getMessage());
    }

    static class NotificationRequest {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}


