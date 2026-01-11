package com.org.biblio.notification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "emprunt-created", groupId = "notification-group")
    public void consumeMessage(String message) {
        System.out.println("=================================================");
        System.out.println("KAFKA NOTIFICATION REÇUE");
        System.out.println("Contenu : " + message);

        envoyerEmailSimule(message);

        System.out.println("=================================================");
    }

    private void envoyerEmailSimule(String message) {
        System.out.println("[SIMULATION] Envoi d'un email à l'utilisateur...");
        System.out.println("Email envoyé avec succès !");
    }
}
